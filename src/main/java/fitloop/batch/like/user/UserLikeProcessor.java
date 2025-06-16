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
        String value = entry.getValue();

        if (!value.contains("_")) {
            throw new IllegalArgumentException("잘못된 형식의 좋아요 데이터: " + value);
        }

        String[] parts = value.split("_");

        if (parts.length != 2) {
            throw new IllegalArgumentException("형식 오류: " + value);
        }

        String typeStr = parts[0].toUpperCase();
        Long contentId = Long.parseLong(parts[1]);

        ContentType contentType = ContentType.valueOf(typeStr);

        return LikeEntity.builder()
                .userId(userId)
                .contentId(contentId)
                .contentType(contentType)
                .likedAt(LocalDateTime.now())
                .build();
    }
}
