package fitloop.payment.controller;

import fitloop.member.auth.VerifiedMember;
import fitloop.member.auth.MemberIdentity;
import fitloop.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    // 주문 생성 (buyerId는 로그인 유저 ID로 처리)
    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestParam Long sellerId,
            @RequestParam Long productId,
            @RequestParam Long amount,
            @VerifiedMember MemberIdentity member
    ) {
        return orderService.createOrder(member.id(), sellerId, productId, amount);
    }

    // 주문 상태 변경 (예: 배송중, 송장번호 업데이트 등)
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status,
            @RequestParam(required = false) String trackingNumber,
            @VerifiedMember MemberIdentity member
    ) {
        return orderService.updateStatus(orderId, status, trackingNumber, member.id());
    }

    // 주문 완료 처리 (구매자가 상품 수령 확정)
    @PostMapping("/{orderId}/complete")
    public ResponseEntity<?> completeOrder(
            @PathVariable Long orderId,
            @VerifiedMember MemberIdentity member
    ) {
        return orderService.completeOrder(orderId, member.id());
    }
}
