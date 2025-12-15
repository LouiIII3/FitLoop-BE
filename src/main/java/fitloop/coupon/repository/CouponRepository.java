package fitloop.coupon.repository;

import fitloop.coupon.entity.CouponIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CouponRepository extends JpaRepository<CouponIssue, Long> {
    boolean existsBySellerIdAndNameAndValidFromAndValidTo(
            long sellerId,
            String name,
            LocalDateTime validFrom,
            LocalDateTime validTo
    );
}