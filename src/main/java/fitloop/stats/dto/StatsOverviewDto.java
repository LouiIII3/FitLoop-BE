package fitloop.stats.dto;

import fitloop.stats.entity.UserStats;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record StatsOverviewDto(
        Long memberId,
        long purchaseCount,
        long salesCount,
        long reviewCount,
        long couponCount,
        long pointBalance,
        LocalDateTime updatedAt
) {
    public static StatsOverviewDto from(UserStats s) {
        return StatsOverviewDto.builder()
                .memberId(s.getMemberId())
                .purchaseCount(s.getPurchaseCount())
                .salesCount(s.getSalesCount())
                .reviewCount(s.getReviewCount())
                .couponCount(s.getCouponAvailableCount())
                .pointBalance(s.getPointBalance())
                .updatedAt(s.getUpdatedAt())
                .build();
    }

    public static StatsOverviewDto zero(Long memberId) {
        return StatsOverviewDto.builder()
                .memberId(memberId)
                .purchaseCount(0)
                .salesCount(0)
                .reviewCount(0)
                .couponCount(0)
                .pointBalance(0)
                .updatedAt(null)
                .build();
    }
}
