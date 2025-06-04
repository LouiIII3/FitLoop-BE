package fitloop.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeEvent {
    private Long userId;
    private Long productId;
    private String action;
    private Long timestamp;
}
