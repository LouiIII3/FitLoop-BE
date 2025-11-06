package fitloop.payment.controller;

import fitloop.member.auth.VerifiedMember;
import fitloop.member.auth.MemberIdentity;
import fitloop.payment.entity.Wallet;
import fitloop.payment.service.WalletService;
import fitloop.payment.service.PaymentVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;
    private final PaymentVerificationService paymentVerificationService;

    @PostMapping("/charge/verify")
    public ResponseEntity<?> verifyAndCharge(
            @RequestParam String impUid,
            @VerifiedMember MemberIdentity member
    ) {
        try {
            Long paidAmount = paymentVerificationService.verifyPayment(impUid);

            Wallet wallet = walletService.charge(member.id(), paidAmount);

            return ResponseEntity.ok(Map.of(
                    "message", "충전 성공",
                    "balance", wallet.getBalance(),
                    "paidAmount", paidAmount
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/deduct")
    public ResponseEntity<?> deduct(
            @RequestParam Long amount,
            @VerifiedMember MemberIdentity member
    ) {
        try {
            Wallet wallet = walletService.deduct(member.id(), amount);
            return ResponseEntity.ok(Map.of("balance", wallet.getBalance()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(
            @VerifiedMember MemberIdentity member
    ) {
        Long balance = walletService.getBalance(member.id());
        return ResponseEntity.ok(Map.of("balance", balance));
    }
}
