package fitloop.payment.repository;

import fitloop.payment.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    // 특정 유저의 거래 내역 조회 (최신순)
    List<WalletTransaction> findByUserIdOrderByCreatedAtDesc(Long userId);

    // 특정 주문과 관련된 거래 내역 조회
    List<WalletTransaction> findByOrderId(Long orderId);

    // 특정 상품과 관련된 거래 내역 조회
    List<WalletTransaction> findByProductId(Long productId);
}
