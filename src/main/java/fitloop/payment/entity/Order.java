package fitloop.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long buyerId;
    private Long sellerId;

    @Column(nullable = false) private Long subtotal;
    @Column(nullable = false) private Long shippingFee;
    @Column(nullable = false) private Long platformFee;
    @Column(nullable = false) private Long couponDiscount;
    @Column(nullable = false) private Long tierDiscount;
    @Column(nullable = false) private Long otherDiscount;
    @Column(nullable = false) private Long grandTotal;

    private String status;
    private String trackingNumber;

    @CreationTimestamp @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp @Column(nullable = false)
    private LocalDateTime updatedAt;
}
