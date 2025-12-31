package fitloop.stats.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_stats")
public class UserStats {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "purchase_count", nullable = false)
    private long purchaseCount;

    @Column(name = "sales_count", nullable = false)
    private long salesCount;

    @Column(name = "review_count", nullable = false)
    private long reviewCount;

    @Column(name = "coupon_available_count", nullable = false)
    private long couponAvailableCount;

    @Column(name = "point_balance", nullable = false)
    private long pointBalance;

    @Version
    @Column(name = "version")
    private Long version;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void incPurchase() { this.purchaseCount++; }
    public void incSales()    { this.salesCount++;   }
    public void incReview()   { this.reviewCount++;  }

    public void addCoupons(long delta) {
        this.couponAvailableCount += delta;
        if (this.couponAvailableCount < 0) this.couponAvailableCount = 0;
    }

    public void addPoints(long delta) {
        this.pointBalance += delta;
        if (this.pointBalance < 0) this.pointBalance = 0;
    }

    public static UserStats zeroOf(Long memberId) {
        return UserStats.builder()
                .memberId(memberId)
                .purchaseCount(0)
                .salesCount(0)
                .reviewCount(0)
                .couponAvailableCount(0)
                .pointBalance(0)
                .build();
    }
}
