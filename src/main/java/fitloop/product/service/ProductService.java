package fitloop.product.service;

import fitloop.member.entity.ProfileEntity;
import fitloop.member.entity.UserEntity;
import fitloop.member.jwt.JWTUtil;
import fitloop.member.repository.ProfileRepository;
import fitloop.member.repository.UserRepository;
import fitloop.product.dto.request.ProductRegisterRequest;
import fitloop.product.dto.response.ProductDetailResponse;
import fitloop.product.dto.response.ProductResponse;
import fitloop.product.entity.*;
import fitloop.product.entity.category.BottomCategory;
import fitloop.product.entity.category.MiddleCategory;
import fitloop.product.entity.category.TopCategory;
import fitloop.product.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import fitloop.member.auth.MemberIdentity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductConditionRepository productConditionRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRelationRepository productCategoryRelationRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductTagRepository productTagRepository;
    private final JWTUtil jwtUtil;
    private final ProfileRepository profileRepository;
    private final StringRedisTemplate redisTemplate;

    private Long getLikeCount(Long productId) {
        String key = "like_count:PRODUCT:" + productId;
        String value = redisTemplate.opsForValue().get(key);
        return value != null ? Long.parseLong(value) : 0L;
    }

    private boolean isLikedByUser(Long userId, Long productId) {
        if (userId == null) return false;
        String key = "user:" + userId + ":likes";
        String value = "PRODUCT_" + productId;
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    @Transactional
    public ResponseEntity<?> createProduct(
            ProductRegisterRequest productRegisterRequest,
            MemberIdentity member,
            String accessToken
    ) {
        UserEntity userEntity = userRepository.findById(member.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 카테고리 저장
        CategoryEntity category = CategoryEntity.builder()
                .topCategory(productRegisterRequest.getTopCategoryEnum())
                .middleCategory(productRegisterRequest.getMiddleCategoryEnum())
                .bottomCategory(productRegisterRequest.getBottomCategoryEnum())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        categoryRepository.save(category);

        // 상품 상태 저장
        ProductConditionEntity productCondition = ProductConditionEntity.builder()
                .productConditionCategory(productRegisterRequest.getProductConditionEnum())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        productConditionRepository.save(productCondition);

        // 상품 이미지 저장
        List<ProductImageEntity> productImageEntities = productRegisterRequest.getImages().stream()
                .map(imageUrl -> ProductImageEntity.builder().imageURL(imageUrl).build())
                .toList();

        // 상품 태그 저장
        List<ProductTagEntity> productTagEntities = productRegisterRequest.getTags().stream()
                .map(tag -> ProductTagEntity.builder().tagName(tag).build())
                .toList();

        // 상품 저장
        ProductEntity product = ProductEntity.builder()
                .userEntity(userEntity)
                .name(productRegisterRequest.getProductName())
                .price(productRegisterRequest.getPrice())
                .isFree(productRegisterRequest.isFree())
                .description(productRegisterRequest.getProductDescription())
                .productConditionEntity(productCondition)
                .isActive(true)
                .isSold(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .includeShipping(productRegisterRequest.isInCludeShipping())
                .build();
        productRepository.save(product);

        // 이미지의 productEntity를 연결하고 저장
        productImageEntities.forEach(image -> {
            image.setProductEntity(product);
            productImageRepository.save(image);
        });

        // 태그의 productEntity를 연결하고 저장
        productTagEntities.forEach(tag -> {
            tag.setProductEntity(product);
            productTagRepository.save(tag);
        });

        // 상품 - 카테고리 관계 저장
        ProductCategoryRelationEntity relation = ProductCategoryRelationEntity.builder()
                .productEntity(product)
                .categoryEntity(category)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        productCategoryRelationRepository.save(relation);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "상품 등록 성공"));
    }


    public List<ProductResponse> getRecentProducts(int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ProductEntity> productPage = productRepository.findAllByIsActiveTrue(pageable);
        List<ProductEntity> products = productPage.getContent();
        List<Long> productIds = products.stream().map(ProductEntity::getId).toList();

        Map<Long, List<String>> imageMap = productImageRepository.findByProductEntityIdIn(productIds).stream()
                .collect(Collectors.groupingBy(
                        img -> img.getProductEntity().getId(),
                        Collectors.mapping(ProductImageEntity::getImageURL, Collectors.toList())
                ));

        Map<Long, List<String>> tagMap = productTagRepository.findByProductEntityIdIn(productIds).stream()
                .collect(Collectors.groupingBy(
                        tag -> tag.getProductEntity().getId(),
                        Collectors.mapping(ProductTagEntity::getTagName, Collectors.toList())
                ));

        return products.stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .isFree(product.isFree())
                        .includeShipping(product.isIncludeShipping())
                        .createdAt(product.getCreatedAt())
                        .imageUrls(imageMap.getOrDefault(product.getId(), List.of()))
                        .tags(tagMap.getOrDefault(product.getId(), List.of()))
                        .likeCount(getLikeCount(product.getId()))
                        .likedByMe(isLikedByUser(userId, product.getId()))
                        .build())
                .toList();
    }


    public ProductDetailResponse getProductDetail(Long productId, Long userId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 최근 본 상품 Redis에 저장
        if (userId != null) {
            String key = "user:" + userId + ":recent_viewed";
            redisTemplate.opsForList().remove(key, 1, productId.toString()); // 중복 제거
            redisTemplate.opsForList().leftPush(key, productId.toString()); // 앞에 추가
            redisTemplate.opsForList().trim(key, 0, 19); // 최대 20개
        }

        // 1. 이미지 URL 리스트
        List<String> imageUrls = productImageRepository.findAllByProductEntity(product)
                .stream()
                .map(ProductImageEntity::getImageURL)
                .toList();

        // 2. 태그 리스트
        List<String> tags = productTagRepository.findAllByProductEntity(product)
                .stream()
                .map(ProductTagEntity::getTagName)
                .toList();

        // 3. 카테고리 정보 (Top > Middle > Bottom)
        ProductCategoryRelationEntity relation = productCategoryRelationRepository.findByProductEntity(product)
                .orElseThrow(() -> new IllegalArgumentException("카테고리 연관 정보를 찾을 수 없습니다."));
        CategoryEntity category = relation.getCategoryEntity();
        String categoryDescription = category.getTopCategory().getDescription() + " > "
                + category.getMiddleCategory().getDescription() + " > "
                + category.getBottomCategory().getDescription();

        // 4. 판매자 닉네임
        ProfileEntity profile = profileRepository.findByUserId(product.getUserEntity())
                .orElseThrow(() -> new IllegalArgumentException("프로필 정보가 없습니다."));

        // 5. 최종 응답 빌드
        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .includeShipping(product.isIncludeShipping())
                .likeCount(getLikeCount(product.getId()))
                .likedByMe(isLikedByUser(userId, product.getId()))
                .createdAt(product.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .imageUrls(imageUrls)
                .tags(tags)
                .free(product.isFree())
                .description(product.getDescription())
                .category(categoryDescription)
                .profileImages(profile.getProfileImage())
                .sellerName(profile.getNickname())
                .rating(4)
                .reviewCount(13)
                .condition(product.getProductConditionEntity().getProductConditionCategory().getDescription())
                .build();
    }

    public List<ProductResponse> getPopularProducts(int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "likeCount"));
        Page<ProductEntity> productPage = productRepository.findAllByIsActiveTrue(pageable);

        List<ProductEntity> products = productPage.getContent();
        List<Long> productIds = products.stream().map(ProductEntity::getId).toList();

        List<ProductImageEntity> allImages = productImageRepository.findByProductEntityIdIn(productIds);
        Map<Long, List<String>> imageMap = allImages.stream()
                .collect(Collectors.groupingBy(
                        img -> img.getProductEntity().getId(),
                        Collectors.mapping(ProductImageEntity::getImageURL, Collectors.toList())
                ));

        List<ProductTagEntity> allTags = productTagRepository.findByProductEntityIdIn(productIds);
        Map<Long, List<String>> tagMap = allTags.stream()
                .collect(Collectors.groupingBy(
                        tag -> tag.getProductEntity().getId(),
                        Collectors.mapping(ProductTagEntity::getTagName, Collectors.toList())
                ));

        // 순위 매기기 (페이지 내에서 1부터 시작)
        return IntStream.range(0, products.size())
                .mapToObj(i -> {
                    ProductEntity product = products.get(i);
                    return ProductResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .price(product.getPrice())
                            .isFree(product.isFree())
                            .includeShipping(product.isIncludeShipping())
                            .likeCount(product.getLikeCount())
                            .likeCount(getLikeCount(product.getId()))
                            .likedByMe(isLikedByUser(userId, product.getId()))
                            .createdAt(product.getCreatedAt())
                            .imageUrls(imageMap.getOrDefault(product.getId(), List.of()))
                            .tags(tagMap.getOrDefault(product.getId(), List.of()))
                            .rank(page * size + i + 1)
                            .build();
                })
                .toList();
    }

    public List<ProductResponse> getCategoryProducts(int page, int size, int categoryCode, String topCategory, Long userId) {
        String codeStr = String.format("%06d", categoryCode);
        TopCategory top = TopCategory.fromGender(topCategory.toUpperCase());
        MiddleCategory middle = MiddleCategory.fromCode(codeStr.substring(0, 3));
        BottomCategory bottom = BottomCategory.fromCode(codeStr.substring(3));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "likeCount"));

        List<ProductEntity> products;
        if ("000".equals(bottom.getCode())) {
            products = productRepository.findByTopAndMiddle(top, middle, pageable).getContent();
        } else {
            products = productRepository.findByTopMiddleBottom(top, middle, bottom, pageable).getContent();
        }

        List<Long> productIds = products.stream()
                .map(ProductEntity::getId)
                .toList();

        Map<Long, List<String>> imageMap = productImageRepository.findByProductEntityIdIn(productIds).stream()
                .collect(Collectors.groupingBy(
                        img -> img.getProductEntity().getId(),
                        Collectors.mapping(ProductImageEntity::getImageURL, Collectors.toList())
                ));

        Map<Long, List<String>> tagMap = productTagRepository.findByProductEntityIdIn(productIds).stream()
                .collect(Collectors.groupingBy(
                        tag -> tag.getProductEntity().getId(),
                        Collectors.mapping(ProductTagEntity::getTagName, Collectors.toList())
                ));

        return products.stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .isFree(product.isFree())
                        .includeShipping(product.isIncludeShipping())
                        .likeCount(getLikeCount(product.getId()))
                        .likedByMe(isLikedByUser(userId, product.getId()))
                        .likeCount(product.getLikeCount())
                        .createdAt(product.getCreatedAt())
                        .imageUrls(imageMap.getOrDefault(product.getId(), List.of()))
                        .tags(tagMap.getOrDefault(product.getId(), List.of()))
                        .build())
                .toList();
    }

    public List<ProductResponse> getRecentViewedProducts(Long userId) {
        if (userId == null) return Collections.emptyList();

        String key = "user:" + userId + ":recent_viewed";
        List<String> idStrings = redisTemplate.opsForList().range(key, 0, 19);
        if (idStrings == null || idStrings.isEmpty()) return Collections.emptyList();

        List<Long> ids = idStrings.stream()
                .map(Long::valueOf)
                .toList();

        List<ProductEntity> products = productRepository.findAllById(ids);
        Map<Long, ProductEntity> productMap = products.stream()
                .collect(Collectors.toMap(ProductEntity::getId, p -> p));

        Map<Long, List<String>> imageMap = productImageRepository.findByProductEntityIdIn(ids).stream()
                .collect(Collectors.groupingBy(
                        img -> img.getProductEntity().getId(),
                        Collectors.mapping(ProductImageEntity::getImageURL, Collectors.toList())
                ));

        Map<Long, List<String>> tagMap = productTagRepository.findByProductEntityIdIn(ids).stream()
                .collect(Collectors.groupingBy(
                        tag -> tag.getProductEntity().getId(),
                        Collectors.mapping(ProductTagEntity::getTagName, Collectors.toList())
                ));

        return ids.stream()
                .map(id -> productMap.get(id))
                .filter(Objects::nonNull)
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .isFree(product.isFree())
                        .includeShipping(product.isIncludeShipping())
                        .createdAt(product.getCreatedAt())
                        .imageUrls(imageMap.getOrDefault(product.getId(), List.of()))
                        .tags(tagMap.getOrDefault(product.getId(), List.of()))
                        .likeCount(getLikeCount(product.getId()))
                        .likedByMe(isLikedByUser(userId, product.getId()))
                        .build())
                .toList();
    }
}
