package fitloop.payment.service.util;

import fitloop.payment.entity.Order;
import fitloop.payment.entity.OrderItem;
import fitloop.payment.dto.CreateOrderRequest;

import java.util.ArrayList;
import java.util.List;

public final class OrderPricing {

    private OrderPricing() {}

    public static class Result {
        public Order orderHeader;
        public List<OrderItem> orderItems = new ArrayList<>();
    }

    public static Result price(CreateOrderRequest req) {
        long subtotal = 0L;
        for (var it : req.getItems()) {
            subtotal += it.getUnitPrice() * it.getQuantity();
        }

        long shipping   = nz(req.getShippingFee());
        long platform   = nz(req.getPlatformFee());
        long coupon     = nz(req.getCouponDiscount());
        long tier       = nz(req.getTierDiscount());
        long other      = nz(req.getOtherDiscount());

        long totalDiscount = coupon + tier + other;
        if (totalDiscount > subtotal + shipping) totalDiscount = subtotal + shipping;

        long grandTotal = subtotal + shipping - totalDiscount;
        if (grandTotal < 0) grandTotal = 0;

        Order header = Order.builder()
                .buyerId(req.getBuyerId())
                .sellerId(req.getSellerId())
                .subtotal(subtotal)
                .shippingFee(shipping)
                .platformFee(platform)
                .couponDiscount(coupon)
                .tierDiscount(tier)
                .otherDiscount(other)
                .grandTotal(grandTotal)
                .status("ORDERED")
                .build();

        List<OrderItem> items = new ArrayList<>();
        long allocated = 0L;
        long remaining = totalDiscount;

        for (int i = 0; i < req.getItems().size(); i++) {
            var it = req.getItems().get(i);
            long itemSubtotal = it.getUnitPrice() * it.getQuantity();

            long itemDiscount;
            if (i < req.getItems().size() - 1 && subtotal > 0) {
                itemDiscount = Math.floorDiv(itemSubtotal * totalDiscount, subtotal);
                allocated += itemDiscount;
                remaining = totalDiscount - allocated;
            } else {
                itemDiscount = Math.max(0, remaining);
            }
            if (itemDiscount > itemSubtotal) itemDiscount = itemSubtotal;

            items.add(OrderItem.builder()
                    .productId(it.getProductId())
                    .unitPrice(it.getUnitPrice())
                    .quantity(it.getQuantity())
                    .itemSubtotal(itemSubtotal)
                    .itemDiscount(itemDiscount)
                    .build());
        }

        Result r = new Result();
        r.orderHeader = header;
        r.orderItems = items;
        return r;
    }

    private static long nz(Long v) { return v == null ? 0L : v; }
}
