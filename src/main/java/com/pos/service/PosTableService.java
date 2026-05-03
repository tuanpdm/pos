package com.pos.service;

import com.pos.entity.PosTable;
import com.pos.repository.PosTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PosTableService {
    @Autowired
    private PosTableRepository posTableRepository;

    public List<PosTable> getAllTables() {
        return posTableRepository.findAll();
    }

    public List<PosTable> getTablesByStatus(String status) {
        return posTableRepository.findByStatus(status);
    }

    public Optional<PosTable> getTableById(Long id) {
        return posTableRepository.findById(id);
    }

    public PosTable createTable(PosTable table) {
        return posTableRepository.save(table);
    }

    public PosTable updateTable(Long id, PosTable table) {
        Optional<PosTable> existing = posTableRepository.findById(id);
        if (existing.isPresent()) {
            PosTable t = existing.get();
            t.setName(table.getName());
            t.setStatus(table.getStatus());
            t.setDescription(table.getDescription());
            return posTableRepository.save(t);
        }
        return null;
    }

    public void deleteTable(Long id) {
        posTableRepository.deleteById(id);
    }

    public PosTable updateTableStatus(Long id, String status) {
        Optional<PosTable> table = posTableRepository.findById(id);
        if (table.isPresent()) {
            PosTable t = table.get();
            t.setStatus(status);
            return posTableRepository.save(t);
        }
        return null;
    }
}

