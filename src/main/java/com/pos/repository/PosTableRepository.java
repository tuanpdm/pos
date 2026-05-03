package com.pos.repository;

import com.pos.entity.PosTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PosTableRepository extends JpaRepository<PosTable, Long> {
    List<PosTable> findByStatus(String status);
}

