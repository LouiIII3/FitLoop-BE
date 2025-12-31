package fitloop.stats.repository;

import fitloop.stats.entity.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserStatsRepository extends JpaRepository<UserStats, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update UserStats s set s.pointBalance = s.pointBalance + :delta where s.memberId = :memberId")
    int addPoints(@Param("memberId") Long memberId, @Param("delta") long delta);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update UserStats s set s.couponAvailableCount = GREATEST(s.couponAvailableCount + :delta, 0) where s.memberId = :memberId")
    int addCoupons(@Param("memberId") Long memberId, @Param("delta") long delta);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update UserStats s set s.purchaseCount = s.purchaseCount + 1 where s.memberId = :memberId")
    int incPurchase(@Param("memberId") Long memberId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update UserStats s set s.salesCount = s.salesCount + 1 where s.memberId = :memberId")
    int incSales(@Param("memberId") Long memberId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update UserStats s set s.reviewCount = s.reviewCount + 1 where s.memberId = :memberId")
    int incReview(@Param("memberId") Long memberId);
}
