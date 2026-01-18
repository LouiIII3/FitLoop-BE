package fitloop.coupon.dto;

import fitloop.coupon.entity.CouponIssue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CouponRegisterRequest {
    @NotBlank
    private String couponName;

    @NotBlank
    private String description;

    private CouponIssue.DiscountType discountType; // FIXED, PERCENT

    @NotNull
    private Double discountValue;

    @NotNull
    private Double minOrderValue;

    private Double maxDiscount;

    @NotNull
    private LocalDateTime validFrom;

    @NotNull
    private LocalDateTime validTo;
}