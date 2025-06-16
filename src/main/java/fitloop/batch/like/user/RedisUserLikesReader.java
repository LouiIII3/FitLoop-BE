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
        // 처음 한 번만 데이터 로딩
        if (iterator == null) {
            Set<String> keys = redisTemplate.keys("user:*:likes");
            List<Map.Entry<Long, String>> allEntries = new ArrayList<>();

            if (keys != null) {
                for (String key : keys) {
                    String userIdStr = key.replace("user:", "").replace(":likes", "");
                    Long userId = Long.parseLong(userIdStr);

                    Set<String> likedContents = redisTemplate.opsForSet().members(key);
                    if (likedContents != null) {
                        for (String content : likedContents) {
                            allEntries.add(Map.entry(userId, content));
                        }
                    }
                }
            }

            iterator = allEntries.iterator();
        }

        return iterator.hasNext() ? iterator.next() : null;
    }
}
