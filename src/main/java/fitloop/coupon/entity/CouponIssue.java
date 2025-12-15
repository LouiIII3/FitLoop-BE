package fitloop.coupon.entity;

import fitloop.member.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_issue",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"seller_id", "name", "valid_from", "valid_to"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long sellerId;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "discount_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    //할인금액
    @Column(name = "discount_value", nullable = false)
    private Double discountValue;

    //최소 주문 금액
    @Column(name = "min_order_value")
    private Double minOrderValue;

    //최대 할인 금액
    @Column(name = "max_discount")
    private Double maxDiscount;

    //유효 시작 끝(분까지)
    @Column(name = "valid_from")
    private LocalDateTime validFrom;

    @Column(name = "valid_to")
    private LocalDateTime validTo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum DiscountType {
        FIXED, PERCENT
    }
}