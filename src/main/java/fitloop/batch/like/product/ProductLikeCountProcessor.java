package fitloop.batch.like.product;

import fitloop.product.entity.ProductEntity;
import fitloop.product.repository.ProductRepository;
import org.springframework.batch.item.ItemProcessor;

import java.util.Map;
import java.util.Optional;

public class ProductLikeCountProcessor implements ItemProcessor<Map.Entry<Long, Long>, ProductEntity> {

    private final ProductRepository productRepository;

    public ProductLikeCountProcessor(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity process(Map.Entry<Long, Long> entry) {
        Long productId = entry.getKey();
        Long likeCount = entry.getValue();

        Optional<ProductEntity> optional = productRepository.findById(productId);
        if (optional.isEmpty()) return null;

        ProductEntity product = optional.get();
        product.setLikeCount(likeCount);
        return product;
    }
}
