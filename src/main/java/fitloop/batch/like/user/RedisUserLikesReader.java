package fitloop.batch.like.user;

import org.springframework.batch.item.ItemReader;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;

public class RedisUserLikesReader implements ItemReader<Map.Entry<Long, String>> {

    private final StringRedisTemplate redisTemplate;
    private Iterator<Map.Entry<Long, String>> iterator;

    public RedisUserLikesReader(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Map.Entry<Long, String> read() {
        if (iterator == null) {
            Set<String> keys = redisTemplate.keys("user:*:likes");
            Map<Long, String> result = new HashMap<>();

            if (keys != null) {
                for (String key : keys) {
                    String userIdStr = key.replace("user:", "").replace(":likes", "");
                    Long userId = Long.parseLong(userIdStr);

                    Set<String> likedContents = redisTemplate.opsForSet().members(key);
                    if (likedContents != null) {
                        for (String content : likedContents) {
                            result.put(userId, content); // e.g., "PRODUCT_50"
                        }
                    }
                }
            }

            iterator = result.entrySet().iterator();
        }
        return iterator.hasNext() ? iterator.next() : null;
    }
}
