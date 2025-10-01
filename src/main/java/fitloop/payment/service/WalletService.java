package fitloop.payment.service;

import fitloop.payment.entity.Wallet;
import fitloop.payment.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    public Wallet charge(Long userId, Long amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseGet(() -> walletRepository.save(Wallet.builder()
                        .userId(userId)
                        .balance(0L)
                        .build()));
        wallet.setBalance(wallet.getBalance() + amount);
        return wallet;
    }

    @Transactional
    public Wallet deduct(Long userId, Long amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("지갑을 찾을 수 없습니다."));

        if (wallet.getBalance() < amount) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        wallet.setBalance(wallet.getBalance() - amount);
        return wallet;
    }

    @Transactional(readOnly = true)
    public Long getBalance(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("지갑을 찾을 수 없습니다."));
        return wallet.getBalance();
    }
}
