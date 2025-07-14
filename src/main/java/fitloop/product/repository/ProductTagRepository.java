package fitloop.product.repository;

import fitloop.product.entity.ProductEntity;
import fitloop.product.entity.ProductTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTagEntity, Long> {
    List<ProductTagEntity> findByProductEntityIdIn(List<Long> productIds);
    List<ProductTagEntity> findAllByProductEntity(ProductEntity product);
    List<ProductTagEntity> findByProductEntityId(Long productId);
}