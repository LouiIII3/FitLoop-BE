package fitloop.payment.controller;

import fitloop.member.auth.VerifiedMember;
import fitloop.member.auth.MemberIdentity;
import fitloop.payment.service.OrderService;
import fitloop.payment.dto.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createOrder(
            @RequestBody CreateOrderRequest req,
            @VerifiedMember MemberIdentity member
    ) {
        req.setBuyerId(member.id());
        return orderService.createOrder(req);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status,
            @RequestParam(required = false) String trackingNumber,
            @VerifiedMember MemberIdentity member
    ) {
        return orderService.updateStatus(orderId, status, trackingNumber, member.id());
    }

    @PostMapping("/{orderId}/complete")
    public ResponseEntity<?> completeOrder(
            @PathVariable Long orderId,
            @VerifiedMember MemberIdentity member
    ) {
        return orderService.completeOrder(orderId, member.id());
    }
}
