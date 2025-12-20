package fitloop.coupon.dto;

import fitloop.coupon.entity.CouponIssue;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreatedCouponResponse {

    private Long id;
    private String title;
    private String discountText;
    private String minOrderText;
    private String periodText;
    private String status;
    private int issuedCount;
    private int usedCount;

    public static CreatedCouponResponse from(CouponIssue coupon) {
        String discountText =
                coupon.getDiscountType() == CouponIssue.DiscountType.PERCENT
                        ? String.format("%.0f%% 할인", coupon.getDiscountValue())
                        : String.format("%,.0f원 할인", coupon.getDiscountValue());

        String minOrderText =
                String.format("최소 주문 %,.0f원", coupon.getMinOrderValue());

        String periodText = "";
        if (coupon.getValidTo() != null) {
            periodText = String.format(
                    "%d.%02d.%02d까지",
                    coupon.getValidTo().getYear(),
                    coupon.getValidTo().getMonthValue(),
                    coupon.getValidTo().getDayOfMonth()
            );
        }

        String status = "ACTIVE";
        LocalDateTime now = LocalDateTime.now();
        if (coupon.getValidTo() != null && now.isAfter(coupon.getValidTo())) {
            status = "EXPIRED";
        }

        return CreatedCouponResponse.builder()
                .id(coupon.getId())
                .title(coupon.getName())
                .discountText(discountText)
                .minOrderText(minOrderText)
                .periodText(periodText)
                .status(status)
                .issuedCount(0)
                .usedCount(0)
                .build();
    }
}
