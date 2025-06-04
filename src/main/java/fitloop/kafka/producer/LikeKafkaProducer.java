package fitloop.kafka.producer;

import fitloop.kafka.dto.LikeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeKafkaProducer {

    private final KafkaTemplate<String, LikeEvent> kafkaTemplate;
    private static final String TOPIC = "product-like-events";

    public void sendLikeEvent(LikeEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
