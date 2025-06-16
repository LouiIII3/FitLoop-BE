package fitloop.batch.like.user;

import fitloop.like.entity.LikeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserLikeWriter implements ItemWriter<LikeEntity> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void write(Chunk<? extends LikeEntity> items) {
        for (LikeEntity like : items) {
            try {
                em.persist(like);
                System.out.println("저장됨: user=" + like.getUserId() +
                        ", content=" + like.getContentId() +
                        ", type=" + like.getContentType());
            } catch (Exception e) {
                System.err.println("SAVE 실패: user=" + like.getUserId() +
                        ", content=" + like.getContentId() +
                        ", type=" + like.getContentType());
                e.printStackTrace();
            }
        }
    }
}