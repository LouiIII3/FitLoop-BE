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
            } catch (Exception e) {
                // UniqueConstraint 위반 시 무시
                System.out.println("중복 무시: user=" + like.getUserId() + " content=" + like.getContentId());
            }
        }
    }
}
