package fitloop.payment.repository;

import fitloop.payment.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyerId(Long buyerId);

    List<Order> findBySellerId(Long sellerId);
}
