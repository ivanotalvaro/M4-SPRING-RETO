package com.banco.sysbank.repository;

import com.banco.sysbank.domain.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    Optional<Transaccion> findByCodigoTransaccion(long codigoTransaccion);
}