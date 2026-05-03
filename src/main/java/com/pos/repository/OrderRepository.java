package com.pos.repository;

import com.pos.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(String status);
    List<Order> findByTableId(Long tableId);
    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<Order> findByStatusAndCreatedAtBetween(String status, LocalDateTime start, LocalDateTime end);
    
    // Find the most recent pending order for a specific table
    Optional<Order> findFirstByTableIdAndStatusOrderByCreatedAtDesc(Long tableId, String status);
}
