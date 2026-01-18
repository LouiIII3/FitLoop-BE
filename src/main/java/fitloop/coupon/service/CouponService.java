package fitloop.coupon.service;

import fitloop.coupon.dto.CouponRegisterRequest;
import fitloop.coupon.entity.CouponIssue;
import fitloop.coupon.repository.CouponRepository;
import fitloop.member.auth.MemberIdentity;
import fitloop.member.entity.UserEntity;
import fitloop.member.jwt.JWTUtil;
import fitloop.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public ResponseEntity<?> createCoupon(CouponRegisterRequest request, MemberIdentity member, String accessToken) {
        // role 확인
        String role = jwtUtil.getRole(accessToken);

        if (!"MEMBER".equals(role)) {
            return ResponseEntity.status(403).body(member.username()+"은 권한이 없습니다.");
        }

        // 쿠폰 유효기간 검증
        LocalDateTime validFrom = request.getValidFrom();
        LocalDateTime validTo = request.getValidTo();

        if (!validFrom.isBefore(validTo)) {
            return ResponseEntity.badRequest().body("유효기간 시작일은 종료일보다 이전이어야 합니다.");
        }

        // 중복 쿠폰 검사
        boolean isDuplicate = couponRepository.existsBySellerIdAndNameAndValidFromAndValidTo(
                member.id(),
                request.getCouponName(),
                validFrom,
                validTo
        );

        if (isDuplicate) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("이미 동일한 조건의 쿠폰이 존재합니다.");
        }

        // 사용자 조회
        String username = member.username();
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "유저를 찾을 수 없습니다."));
        }

        // 쿠폰 생성
        CouponIssue coupon = new CouponIssue();
        coupon.setSellerId(member.id());
        coupon.setName(request.getCouponName());
        coupon.setDescription(request.getDescription());
        coupon.setDiscountType(CouponIssue.DiscountType.valueOf(request.getDiscountType().name()));
        coupon.setDiscountValue(request.getDiscountValue());
        coupon.setMinOrderValue(request.getMinOrderValue());
        coupon.setMaxDiscount(request.getMaxDiscount());
        coupon.setValidFrom(request.getValidFrom());
        coupon.setValidTo(request.getValidTo());

        couponRepository.save(coupon);

        return ResponseEntity.ok("쿠폰 생성 완료");
    }
}