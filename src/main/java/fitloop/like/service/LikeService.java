package fitloop.like.service;

import fitloop.kafka.dto.LikeEvent;
import fitloop.kafka.producer.LikeKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeKafkaProducer kafkaProducer;

    public void likeProduct(Long userId, Long productId) {
        LikeEvent event = new LikeEvent(userId, productId, "LIKE", System.currentTimeMillis());
        kafkaProducer.sendLikeEvent(event);
    }

    public void unlikeProduct(Long userId, Long productId) {
        LikeEvent event = new LikeEvent(userId, productId, "UNLIKE", System.currentTimeMillis());
        kafkaProducer.sendLikeEvent(event);
    }
}
