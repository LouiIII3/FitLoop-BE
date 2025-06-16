package fitloop.product.sync;

import fitloop.product.entity.ProductEntity;
import fitloop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductLikeRedisSync implements ApplicationRunner {

    private final StringRedisTemplate redisTemplate;
    private final ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) {
        List<ProductEntity> products = productRepository.findAllByIsActiveTrue();

        for (ProductEntity product : products) {
            Long productId = product.getId();
            Long likeCount = product.getLikeCount();

            String redisKey = "like_count:PRODUCT:" + productId;
            redisTemplate.opsForValue().set(redisKey, String.valueOf(likeCount));
        }

        System.out.println("Redis like_count:PRODUCT 초기화 완료: " + products.size() + "개 상품");
    }
}