package fitloop.batch.like.user;

import fitloop.like.entity.LikeEntity;
import fitloop.like.entity.type.ContentType;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.util.Map;

public class UserLikeProcessor implements ItemProcessor<Map.Entry<Long, String>, LikeEntity> {

    @Override
    public LikeEntity process(Map.Entry<Long, String> entry) {
        Long userId = entry.getKey();
        String[] parts = entry.getValue().split("_"); // ex: PRODUCT_50

        ContentType contentType = ContentType.valueOf(parts[0]);
        Long contentId = Long.parseLong(parts[1]);

        return LikeEntity.builder()
                .userId(userId)
                .contentId(contentId)
                .contentType(contentType)
                .likedAt(LocalDateTime.now())
                .build();
    }
}
