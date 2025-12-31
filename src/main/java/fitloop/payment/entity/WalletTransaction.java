package fitloop.payment.entity;

import fitloop.payment.entity.type.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_transactions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 유저의 거래인지
    @Column(nullable = false)
    private Long userId;

    // 구매자 ↔ 판매자 거래의 경우 상대방 ID
    private Long counterpartyId;

    // 어떤 주문(혹은 충전)과 연결된 것인지
    private Long orderId;

    // 어떤 상품 거래인지 추적용 (선택)
    private Long productId;

    // 거래 금액
    @Column(nullable = false)
    private Long amount;

    // 거래 타입 (충전, 구매, 판매수익, 출금, 환불 등)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    // 결제 수단 (KAKAOPAY, WALLET, CARD 등)
    private String method;

    // 설명 (예: "카카오페이 충전", "에어포스 구매", "출금 완료")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
