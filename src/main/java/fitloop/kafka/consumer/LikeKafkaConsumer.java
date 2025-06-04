package fitloop.kafka.consumer;

import fitloop.kafka.dto.LikeEvent;
import fitloop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class LikeKafkaConsumer {

    private final StringRedisTemplate redisTemplate;
    private final ProductRepository productRepository;

    @KafkaListener(topics = "product-like-events", groupId = "fitloop-like-group")
    public void consume(LikeEvent event) {
        String userKey = "user:" + event.getUserId() + ":likes";
        String productKey = "like_count:PRODUCT:" + event.getProductId();
        String productVal = "PRODUCT_" + event.getProductId();

        // Redis 캐시 초기화 (존재하지 않을 경우 DB에서 조회)
        if (Boolean.FALSE.equals(redisTemplate.hasKey(productKey))) {
            Integer likeCount = productRepository.getLikeCountByProductId(event.getProductId());
            if (likeCount == null) likeCount = 0;
            redisTemplate.opsForValue().set(productKey, likeCount.toString());
        }

        // 좋아요 / 좋아요 취소 반영
        if ("LIKE".equals(event.getAction())) {
            redisTemplate.opsForSet().add(userKey, productVal);
            redisTemplate.opsForValue().increment(productKey);
        } else if ("UNLIKE".equals(event.getAction())) {
            redisTemplate.opsForSet().remove(userKey, productVal);
            redisTemplate.opsForValue().decrement(productKey);
        }
    }
}

