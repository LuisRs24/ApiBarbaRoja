package com.cibertec.apibarbaroja.repositories;

import com.cibertec.apibarbaroja.entities.ReservaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends BaseRepository<ReservaEntity, Integer>{
    List<ReservaEntity> findByUsuario_Id(int usuario_id);

    //Esta consulta selecciona todas las reservas cuya fecha de reserva
    //sea igual o posterior a la fecha y hora actual

    @Query("SELECT r FROM ReservaEntity r WHERE r.fechaReserva >= CURRENT_TIMESTAMP ORDER BY r.fechaReserva ASC")
    List<ReservaEntity> findAllReservasProximas();

    // Usamos SQL NATIVO
    @Transactional
    @Modifying
    @Query(value = "UPDATE ReservaEntity r SET r.estado = :estado WHERE r.id = :id")
    void updateEstadoById(@Param("id") Integer id, @Param("estado") String estado);

    // El Object[] contiene el objeto ServicioEntity en la posición 0
    // y las veces que fue reservado el servicio en la posición 1.
    @Query(value = "SELECT s, COUNT(r) as totalLlamadas " +
            "FROM ReservaEntity r " +
            "JOIN r.servicio s " +
            "GROUP BY s.id " +
            "ORDER BY totalLlamadas DESC")
    List<Object[]> findServiciosMasLlamados();

    //Devuelve un número entero que representa la cantidad de reservas encontradas.
    @Query("SELECT COUNT(r) FROM ReservaEntity r " +
            "WHERE (r.fechaReserva BETWEEN :fechaInicio AND :fechaFin)")
    int contarReservasEnRango(@Param("fechaInicio") LocalDateTime fechaInicio,
                              @Param("fechaFin") LocalDateTime fechaFin);
}
