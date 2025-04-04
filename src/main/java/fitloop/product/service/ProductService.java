package fitloop.product.service;

import fitloop.member.entity.UserEntity;
import fitloop.member.jwt.JWTUtil;
import fitloop.member.repository.UserRepository;
import fitloop.product.dto.request.ProductRegisterRequest;
import fitloop.product.entity.*;
import fitloop.product.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Transactional
    public ResponseEntity<?> createProduct(ProductRegisterRequest productRegisterRequest, Object principal, String accessToken) {
        String username = jwtUtil.getUsername(accessToken);

        UserEntity userEntity = userRepository.findByUsername(username)
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
        List<ProductImageEntity> productImageEntities = new ArrayList<>();
        for (String imageUrl : productRegisterRequest.getImages()) {
            ProductImageEntity productImageEntity = ProductImageEntity.builder()
                    .imageURL(imageUrl)
                    .build();

            productImageEntities.add(productImageEntity);
        }

        // 상품 태그 저장
        List<ProductTagEntity> productTagEntities = new ArrayList<>();
        for (String tag : productRegisterRequest.getTags()) {
            ProductTagEntity productTagEntity = ProductTagEntity.builder()
                    .tagName(tag)
                    .build();

            productTagEntities.add(productTagEntity);
        }

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
        for (ProductImageEntity productImageEntity : productImageEntities) {
            productImageEntity.setProductEntity(product);
            productImageRepository.save(productImageEntity);
        }

        // 태그의 productEntity를 연결하고 저장
        for (ProductTagEntity productTagEntity : productTagEntities) {
            productTagEntity.setProductEntity(product);
            productTagRepository.save(productTagEntity);
        }

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
}
