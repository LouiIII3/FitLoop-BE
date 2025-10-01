package fitloop.payment.service;

import fitloop.payment.entity.Wallet;
import fitloop.payment.entity.Withdrawal;
import fitloop.payment.repository.WalletRepository;
import fitloop.payment.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public ResponseEntity<?> requestWithdrawal(Long sellerId, Long amount, String bankName, String accountNumber) {
        Wallet wallet = walletRepository.findByUserId(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("지갑을 찾을 수 없습니다."));

        if (wallet.getBalance() < amount) {
            return ResponseEntity.badRequest().body("잔액이 부족합니다.");
        }
        wallet.setBalance(wallet.getBalance() - amount);

        Withdrawal withdrawal = Withdrawal.builder()
                .sellerId(sellerId)
                .amount(amount)
                .bankName(bankName)
                .accountNumber(accountNumber)
                .status("REQUESTED")
                .build();
        withdrawalRepository.save(withdrawal);

        return ResponseEntity.ok(withdrawal);
    }

    @Transactional
    public ResponseEntity<?> completeWithdrawal(Long withdrawalId, Long sellerId) {
        Withdrawal withdrawal = withdrawalRepository.findById(withdrawalId)
                .orElseThrow(() -> new IllegalArgumentException("출금 요청을 찾을 수 없습니다."));

        if (!withdrawal.getSellerId().equals(sellerId)) {
            return ResponseEntity.status(403).body("본인 출금 요청만 완료할 수 있습니다.");
        }

        withdrawal.setStatus("COMPLETED");
        withdrawalRepository.save(withdrawal);

        return ResponseEntity.ok(withdrawal);
    }

    public ResponseEntity<?> getWithdrawHistory(Long sellerId) {
        return ResponseEntity.ok(withdrawalRepository.findBySellerId(sellerId));
    }
}
