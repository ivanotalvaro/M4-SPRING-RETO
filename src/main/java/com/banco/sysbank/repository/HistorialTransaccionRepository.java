package com.banco.sysbank.repository;

import com.banco.sysbank.domain.HistorialTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialTransaccionRepository extends JpaRepository<HistorialTransaccion, Long> {
}