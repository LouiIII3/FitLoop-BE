package fitloop.payment.repository;

import fitloop.payment.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findBySellerId(Long sellerId);
}
