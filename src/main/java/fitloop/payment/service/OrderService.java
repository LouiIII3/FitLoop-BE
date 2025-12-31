package fitloop.payment.service;

import fitloop.payment.entity.Order;
import fitloop.payment.entity.OrderItem;
import fitloop.payment.entity.Wallet;
import fitloop.payment.entity.WalletTransaction;
import fitloop.payment.entity.type.TransactionType;
import fitloop.payment.repository.OrderItemRepository;
import fitloop.payment.repository.OrderRepository;
import fitloop.payment.repository.WalletRepository;
import fitloop.payment.repository.WalletTransactionRepository;
import fitloop.payment.dto.CreateOrderRequest;
import fitloop.payment.service.util.OrderPricing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    @Transactional
    public ResponseEntity<?> createOrder(CreateOrderRequest req) {
        var priced = OrderPricing.price(req);
        Order header = orderRepository.save(priced.orderHeader);

        for (OrderItem oi : priced.orderItems) {
            oi.setOrderId(header.getId());
            orderItemRepository.save(oi);
        }

        Wallet buyerWallet = walletRepository.findByUserId(req.getBuyerId())
                .orElseThrow(() -> new IllegalArgumentException("지갑을 찾을 수 없습니다."));
        if (buyerWallet.getBalance() < header.getGrandTotal()) {
            return ResponseEntity.badRequest().body("잔액이 부족합니다.");
        }
        buyerWallet.setBalance(buyerWallet.getBalance() - header.getGrandTotal());
        walletRepository.save(buyerWallet);

        WalletTransaction buyerTx = WalletTransaction.builder()
                .userId(req.getBuyerId())
                .counterpartyId(req.getSellerId())
                .orderId(header.getId())
                .productId(null)
                .amount(header.getGrandTotal())
                .type(TransactionType.PURCHASE)
                .method("WALLET")
                .description("다상품 구매 결제")
                .build();
        walletTransactionRepository.save(buyerTx);

        return ResponseEntity.ok(header);
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
        sellerWallet.setBalance(sellerWallet.getBalance() + order.getGrandTotal());
        walletRepository.save(sellerWallet);

        return ResponseEntity.ok(order);
    }

    public ResponseEntity<?> getOrdersByBuyer(Long buyerId) {
        return ResponseEntity.ok(orderRepository.findByBuyerId(buyerId));
    }
}
