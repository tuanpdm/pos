package com.pos.service;

import com.pos.entity.Order;
import com.pos.entity.OrderDetail;
import com.pos.repository.OrderRepository;
import com.pos.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> getOrdersByTable(Long tableId) {
        return orderRepository.findByTableId(tableId);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public Optional<Order> getActiveOrderByTable(Long tableId) {
        return orderRepository.findFirstByTableIdAndStatusOrderByCreatedAtDesc(tableId, "PENDING");
    }

    public Order createOrder(Order order) {
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order order) {
        Optional<Order> existing = orderRepository.findById(id);
        if (existing.isPresent()) {
            Order o = existing.get();
            o.setTable(order.getTable());
            o.setUser(order.getUser());
            o.setStatus(order.getStatus());
            o.setPaymentMethod(order.getPaymentMethod());
            o.setVatRate(order.getVatRate());
            o.setDiscountAmount(order.getDiscountAmount());
            o.setNotes(order.getNotes());
            calculateOrderTotal(o);
            return orderRepository.save(o);
        }
        return null;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<OrderDetail> getOrderDetails(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    public OrderDetail addOrderDetail(OrderDetail detail) {
        return orderDetailRepository.save(detail);
    }

    public void removeOrderDetail(Long detailId) {
        orderDetailRepository.deleteById(detailId);
    }

    public Order calculateOrderTotal(Order order) {
        List<OrderDetail> details = getOrderDetails(order.getId());
        BigDecimal subtotal = BigDecimal.ZERO;

        for (OrderDetail detail : details) {
            BigDecimal lineTotal = detail.getUnitPrice().multiply(new BigDecimal(detail.getQuantity()));
            subtotal = subtotal.add(lineTotal);
        }

        BigDecimal discount = order.getDiscountAmount() != null ? order.getDiscountAmount() : BigDecimal.ZERO;
        BigDecimal afterDiscount = subtotal.subtract(discount);

        BigDecimal vatRate = order.getVatRate() != null ? order.getVatRate() : BigDecimal.ZERO;
        BigDecimal vat = afterDiscount.multiply(vatRate).divide(new BigDecimal(100), 2, java.math.RoundingMode.HALF_UP);

        BigDecimal total = afterDiscount.add(vat);
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    public Order payOrder(Long orderId, String paymentMethod) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order o = order.get();
            o.setStatus("PAID");
            o.setPaymentMethod(paymentMethod);
            o.setPaidAt(LocalDateTime.now());
            return orderRepository.save(o);
        }
        return null;
    }

    public Order cancelOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order o = order.get();
            o.setStatus("CANCELLED");
            return orderRepository.save(o);
        }
        return null;
    }

    public List<Order> getOrdersInRange(LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByCreatedAtBetween(start, end);
    }

    public BigDecimal getRevenueInRange(LocalDateTime start, LocalDateTime end) {
        List<Order> orders = orderRepository.findByStatusAndCreatedAtBetween("PAID", start, end);
        return orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}