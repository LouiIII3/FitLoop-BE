package fitloop.batch.like.product;

import org.springframework.batch.item.ItemReader;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;

public class RedisProductLikeCountReader implements ItemReader<Map.Entry<Long, Long>> {

    private final StringRedisTemplate redisTemplate;
    private Iterator<Map.Entry<Long, Long>> iterator;

    public RedisProductLikeCountReader(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Map.Entry<Long, Long> read() {
        if (iterator == null) {
            Set<String> keys = redisTemplate.keys("like_count:PRODUCT:*");
            Map<Long, Long> result = new HashMap<>();

            if (keys != null) {
                for (String key : keys) {
                    try {
                        Long productId = Long.parseLong(key.replace("like_count:PRODUCT:", ""));
                        String count = redisTemplate.opsForValue().get(key);
                        if (count != null) {
                            System.out.println("[RedisReader] productId: " + productId + ", likeCount: " + count);
                            result.put(productId, Long.parseLong(count));
                        }
                    } catch (NumberFormatException ignore) {}
                }
            }

            iterator = result.entrySet().iterator();
        }

        return iterator != null && iterator.hasNext() ? iterator.next() : null;
    }
}
