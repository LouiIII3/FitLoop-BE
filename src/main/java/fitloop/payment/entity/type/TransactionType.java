package fitloop.payment.entity.type;

public enum TransactionType {
    CHARGE,        // 충전
    PURCHASE,      // 구매
    SALE,          // 판매 수익
    WITHDRAWAL,    // 출금
    REFUND,        // 환불
    FEE,           // 수수료
    POINT_EARN,    // 포인트 적립
    POINT_SPEND    // 포인트 사용
}
