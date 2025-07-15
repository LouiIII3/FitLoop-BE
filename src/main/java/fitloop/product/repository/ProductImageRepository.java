package fitloop.product.repository;

import fitloop.product.entity.ProductEntity;
import fitloop.product.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {
    List<ProductImageEntity> findByProductEntityIdIn(List<Long> productIds);
    List<ProductImageEntity> findAllByProductEntity(ProductEntity product);
    List<ProductImageEntity> findByProductEntityId(Long productId);
}