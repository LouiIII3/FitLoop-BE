package fitloop.batch.like.product;

import fitloop.product.entity.ProductEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class ProductLikeCountWriter implements ItemWriter<ProductEntity> {

    private final EntityManager entityManager;

    @Override
    public void write(Chunk<? extends ProductEntity> items) {
        for (ProductEntity incoming : items) {
            ProductEntity existing = entityManager.find(ProductEntity.class, incoming.getId());
            if (existing != null) {
                existing.setLikeCount(incoming.getLikeCount());
            }
        }
    }
}
