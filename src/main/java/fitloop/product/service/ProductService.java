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

    private Map<Long, List<String>> getImageMap(List<Long> productIds) {
        return productImageRepository.findByProductEntityIdIn(productIds).stream()
                .collect(Collectors.groupingBy(
                        img -> img.getProductEntity().getId(),
                        Collectors.mapping(ProductImageEntity::getImageURL, Collectors.toList())
                ));
    }

    private Map<Long, List<String>> getTagMap(List<Long> productIds) {
        return productTagRepository.findByProductEntityIdIn(productIds).stream()
                .collect(Collectors.groupingBy(
                        tag -> tag.getProductEntity().getId(),
                        Collectors.mapping(ProductTagEntity::getTagName, Collectors.toList())
                ));
    }

    private ProductResponse toProductResponse(ProductEntity product, Long userId, List<String> images, List<String> tags, Integer rank) {
        ProductResponse.ProductResponseBuilder builder = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .isFree(product.isFree())
                .includeShipping(product.isIncludeShipping())
                .createdAt(product.getCreatedAt())
                .imageUrls(images)
                .tags(tags)
                .likeCount(getLikeCount(product.getId()))
                .likedByMe(isLikedByUser(userId, product.getId()));

        if (rank != null) builder.rank(rank);
        return builder.build();
    }

    @Transactional
    public ResponseEntity<?> createProduct(ProductRegisterRequest request, MemberIdentity member, String accessToken) {
        UserEntity user = userRepository.findById(member.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        CategoryEntity category = categoryRepository.save(CategoryEntity.builder()
                .topCategory(request.getTopCategoryEnum())
                .middleCategory(request.getMiddleCategoryEnum())
                .bottomCategory(request.getBottomCategoryEnum())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        ProductConditionEntity condition = productConditionRepository.save(ProductConditionEntity.builder()
                .productConditionCategory(request.getProductConditionEnum())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        ProductEntity product = productRepository.save(ProductEntity.builder()
                .userEntity(user)
                .name(request.getProductName())
                .price(request.getPrice())
                .isFree(request.isFree())
                .description(request.getProductDescription())
                .productConditionEntity(condition)
                .isActive(true)
                .isSold(false)
                .includeShipping(request.isInCludeShipping())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        request.getImages().forEach(url -> productImageRepository.save(ProductImageEntity.builder()
                .productEntity(product)
                .imageURL(url)
                .build()));

        request.getTags().forEach(tag -> productTagRepository.save(ProductTagEntity.builder()
                .productEntity(product)
                .tagName(tag)
                .build()));

        productCategoryRelationRepository.save(ProductCategoryRelationEntity.builder()
                .productEntity(product)
                .categoryEntity(category)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "상품 등록 성공"));
    }

    public List<ProductResponse> getRecentProducts(int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<ProductEntity> products = productRepository.findAllByIsActiveTrue(pageable).getContent();
        List<Long> ids = products.stream().map(ProductEntity::getId).toList();
        Map<Long, List<String>> images = getImageMap(ids);
        Map<Long, List<String>> tags = getTagMap(ids);

        return products.stream()
                .map(p -> toProductResponse(p, userId, images.getOrDefault(p.getId(), List.of()), tags.getOrDefault(p.getId(), List.of()), null))
                .toList();
    }

    public ProductDetailResponse getProductDetail(Long productId, Long userId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        if (userId != null) {
            String key = "user:" + userId + ":recent_viewed";
            redisTemplate.opsForList().remove(key, 1, productId.toString());
            redisTemplate.opsForList().leftPush(key, productId.toString());
            redisTemplate.opsForList().trim(key, 0, 19);
        }

        List<String> imageUrls = productImageRepository.findAllByProductEntity(product).stream()
                .map(ProductImageEntity::getImageURL).toList();
        List<String> tags = productTagRepository.findAllByProductEntity(product).stream()
                .map(ProductTagEntity::getTagName).toList();

        ProductCategoryRelationEntity relation = productCategoryRelationRepository.findByProductEntity(product)
                .orElseThrow(() -> new IllegalArgumentException("카테고리 연관 정보를 찾을 수 없습니다."));
        CategoryEntity category = relation.getCategoryEntity();
        String categoryDescription = String.join(" > ",
                category.getTopCategory().getDescription(),
                category.getMiddleCategory().getDescription(),
                category.getBottomCategory().getDescription());

        ProfileEntity profile = profileRepository.findByUserId(product.getUserEntity())
                .orElseThrow(() -> new IllegalArgumentException("프로필 정보가 없습니다."));

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
        List<ProductEntity> products = productRepository.findAllByIsActiveTrue(pageable).getContent();
        List<Long> ids = products.stream().map(ProductEntity::getId).toList();
        Map<Long, List<String>> images = getImageMap(ids);
        Map<Long, List<String>> tags = getTagMap(ids);

        return IntStream.range(0, products.size())
                .mapToObj(i -> toProductResponse(products.get(i), userId,
                        images.getOrDefault(products.get(i).getId(), List.of()),
                        tags.getOrDefault(products.get(i).getId(), List.of()),
                        page * size + i + 1))
                .toList();
    }

    public List<ProductResponse> getCategoryProducts(int page, int size, int categoryCode, String topCategory, Long userId) {
        String codeStr = String.format("%06d", categoryCode);
        TopCategory top = TopCategory.fromGender(topCategory.toUpperCase());
        MiddleCategory middle = MiddleCategory.fromCode(codeStr.substring(0, 3));
        BottomCategory bottom = BottomCategory.fromCode(codeStr.substring(3));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "likeCount"));

        List<ProductEntity> products = ("000".equals(bottom.getCode()) ?
                productRepository.findByTopAndMiddle(top, middle, pageable) :
                productRepository.findByTopMiddleBottom(top, middle, bottom, pageable)).getContent();

        List<Long> ids = products.stream().map(ProductEntity::getId).toList();
        Map<Long, List<String>> images = getImageMap(ids);
        Map<Long, List<String>> tags = getTagMap(ids);

        return products.stream()
                .map(p -> toProductResponse(p, userId, images.getOrDefault(p.getId(), List.of()), tags.getOrDefault(p.getId(), List.of()), null))
                .toList();
    }

    public List<ProductResponse> getRecentViewedProducts(Long userId) {
        if (userId == null) return List.of();
        String key = "user:" + userId + ":recent_viewed";
        List<String> idStrings = redisTemplate.opsForList().range(key, 0, 19);
        if (idStrings == null || idStrings.isEmpty()) return List.of();

        List<Long> ids = idStrings.stream().map(Long::valueOf).toList();
        Map<Long, ProductEntity> productMap = productRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(ProductEntity::getId, p -> p));
        Map<Long, List<String>> images = getImageMap(ids);
        Map<Long, List<String>> tags = getTagMap(ids);

        return ids.stream()
                .map(productMap::get)
                .filter(Objects::nonNull)
                .map(p -> toProductResponse(p, userId, images.getOrDefault(p.getId(), List.of()), tags.getOrDefault(p.getId(), List.of()), null))
                .toList();
    }
}
