package fitloop.payment.controller;

import fitloop.member.auth.VerifiedMember;
import fitloop.member.auth.MemberIdentity;
import fitloop.payment.service.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/withdrawals")
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    @PostMapping
    public ResponseEntity<?> requestWithdrawal(
            @RequestParam Long amount,
            @RequestParam String bankName,
            @RequestParam String accountNumber,
            @VerifiedMember MemberIdentity member
    ) {
        return withdrawalService.requestWithdrawal(member.id(), amount, bankName, accountNumber);
    }

    @PostMapping("/{withdrawalId}/complete")
    public ResponseEntity<?> completeWithdrawal(
            @PathVariable Long withdrawalId,
            @VerifiedMember MemberIdentity member
    ) {
        return withdrawalService.completeWithdrawal(withdrawalId, member.id());
    }
}
