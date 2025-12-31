package fitloop.payment.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateOrderRequest {
    private Long buyerId;
    private Long sellerId;

    private Long shippingFee;
    private Long platformFee;
    private Long couponDiscount;
    private Long tierDiscount;
    private Long otherDiscount;

    private List<Item> items;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Item {
        private Long productId;
        private Long unitPrice;
        private Integer quantity;
    }
}
