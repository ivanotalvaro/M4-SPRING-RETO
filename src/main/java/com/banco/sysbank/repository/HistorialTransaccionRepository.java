package com.banco.sysbank.repository;

import com.banco.sysbank.domain.HistorialTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HistorialTransaccionRepository extends JpaRepository<HistorialTransaccion, Long> {
    @Query("SELECT h FROM HistorialTransaccion h WHERE h.cuenta.id = :idCuenta ORDER BY h.fechaHora DESC")
    List<HistorialTransaccion> findTop5ByCuentaIdOrderByFechaHoraDesc(@Param("idCuenta") Long idCuenta);
}