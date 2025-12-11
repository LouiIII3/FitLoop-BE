package fitloop.coupon.controller;

import fitloop.coupon.dto.CouponRegisterRequest;
import fitloop.coupon.service.CouponService;
import fitloop.member.auth.Login;
import fitloop.member.auth.MemberIdentity;
import fitloop.member.auth.VerifiedMember;
import fitloop.member.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupon")
public class CouponController {
    private final CouponService couponService;

    @Login
    @PostMapping("/register")
    public ResponseEntity<?> createCoupon(
            @RequestBody @Valid CouponRegisterRequest couponRegisterRequest,
            @VerifiedMember MemberIdentity member,
            @RequestHeader("access") String accessToken
    ) {
        return couponService.createCoupon(couponRegisterRequest, member, accessToken);
    }
}