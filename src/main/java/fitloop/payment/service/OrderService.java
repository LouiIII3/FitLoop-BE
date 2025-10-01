package fitloop.payment.service;

import fitloop.payment.entity.Order;
import fitloop.payment.entity.Wallet;
import fitloop.payment.repository.OrderRepository;
import fitloop.payment.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public ResponseEntity<?> createOrder(Long buyerId, Long sellerId, Long productId, Long amount) {
        Wallet buyerWallet = walletRepository.findByUserId(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("지갑을 찾을 수 없습니다."));
        if (buyerWallet.getBalance() < amount) {
            return ResponseEntity.badRequest().body("잔액이 부족합니다.");
        }
        buyerWallet.setBalance(buyerWallet.getBalance() - amount);

        Order order = Order.builder()
                .buyerId(buyerId)
                .sellerId(sellerId)
                .productId(productId)
                .amount(amount)
                .status("ORDERED")
                .build();
        orderRepository.save(order);

        return ResponseEntity.ok(order);
    }

    @Transactional
    public ResponseEntity<?> updateStatus(Long orderId, String status, String trackingNumber, Long userId) {
        Optional<Order> opt = orderRepository.findById(orderId);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body("주문을 찾을 수 없습니다.");
        }
        Order order = opt.get();

        if (!order.getSellerId().equals(userId)) {
            return ResponseEntity.status(403).body("본인만 주문 상태를 변경할 수 있습니다.");
        }

        order.setStatus(status);
        if (trackingNumber != null) {
            order.setTrackingNumber(trackingNumber);
        }
        orderRepository.save(order);

        return ResponseEntity.ok(order);
    }

    @Transactional
    public ResponseEntity<?> completeOrder(Long orderId, Long buyerId) {
        Optional<Order> opt = orderRepository.findById(orderId);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body("주문을 찾을 수 없습니다.");
        }
        Order order = opt.get();

        if (!order.getBuyerId().equals(buyerId)) {
            return ResponseEntity.status(403).body("본인만 주문을 완료할 수 있습니다.");
        }

        order.setStatus("COMPLETED");
        orderRepository.save(order);

        Wallet sellerWallet = walletRepository.findByUserId(order.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("판매자 지갑을 찾을 수 없습니다."));
        sellerWallet.setBalance(sellerWallet.getBalance() + order.getAmount());

        return ResponseEntity.ok(order);
    }

    public ResponseEntity<?> getOrdersByBuyer(Long buyerId) {
        return ResponseEntity.ok(orderRepository.findByBuyerId(buyerId));
    }
}
