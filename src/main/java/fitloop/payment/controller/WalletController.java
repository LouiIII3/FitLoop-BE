package fitloop.payment.controller;

import fitloop.member.auth.VerifiedMember;
import fitloop.member.auth.MemberIdentity;
import fitloop.payment.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    // 지갑 충전
    @PostMapping("/charge")
    public ResponseEntity<?> charge(
            @RequestParam Long amount,
            @VerifiedMember MemberIdentity member
    ) {
        return walletService.charge(member.id(), amount);
    }

    // 지갑 차감
    @PostMapping("/deduct")
    public ResponseEntity<?> deduct(
            @RequestParam Long amount,
            @VerifiedMember MemberIdentity member
    ) {
        return walletService.deduct(member.id(), amount);
    }

    // 잔액 조회
    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(
            @VerifiedMember MemberIdentity member
    ) {
        return walletService.getBalance(member.id());
    }
}
