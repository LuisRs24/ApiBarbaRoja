package com.cibertec.apibarbaroja.services;

import com.cibertec.apibarbaroja.entities.ReservaEntity;
import com.cibertec.apibarbaroja.entities.ServicioEntity;
import com.cibertec.apibarbaroja.entities.ServicioMasLlamadoDTO;
import com.cibertec.apibarbaroja.repositories.BaseRepository;
import com.cibertec.apibarbaroja.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaServiceImpl extends BaseServiceImpl <ReservaEntity, Integer> implements ReservaService {


    @Autowired
    private ReservaRepository reservaRepository;

    public ReservaServiceImpl(BaseRepository<ReservaEntity, Integer> baseRepository) {
        super(baseRepository);
    }


    @Override
    public List<ReservaEntity> reservasPorUsuario(int idCliente) throws Exception {
        try {
            return reservaRepository.findByUsuario_Id(idCliente);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<ReservaEntity> reservasProximas() throws Exception {
        try {
            return reservaRepository.findAllReservasProximas();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String updateEstadoByIdReserva(int idReserva, int estado) throws Exception {
        try {
            if (reservaRepository.existsById(idReserva)) {
                return switch (estado) {
                    case 1 -> {
                        reservaRepository.updateEstadoById(idReserva, "PENDIENTE");
                        yield "Estado de la reserva actualizado correctamente";
                    }
                    case 2 -> {
                        reservaRepository.updateEstadoById(idReserva, "FINALIZADA");
                        yield "Estado de la reserva actualizado correctamente";
                    }
                    case 3 -> {
                        reservaRepository.updateEstadoById(idReserva, "CANCELADA");
                        yield "Estado de la reserva actualizado correctamente";
                    }
                    default -> "Ingrese un estado valido";
                };
            } else {
                return "La reserva no existe";
            }
        } catch (Exception e) {
            throw new Exception("Error al actualizar el estado de la reserva: " + e.getMessage());
        }
    }

    @Override
    public List<ServicioMasLlamadoDTO> getServiciosMasLlamados() {
        List<Object[]> resultados = reservaRepository.findServiciosMasLlamados();
        List<ServicioMasLlamadoDTO> serviciosMasLlamados = new ArrayList<>();

        for (Object[] resultado : resultados) {
            ServicioEntity servicio = (ServicioEntity) resultado[0];
            Long totalLlamadas = (Long) resultado[1];

            ServicioMasLlamadoDTO dto = new ServicioMasLlamadoDTO(servicio, totalLlamadas);
            serviciosMasLlamados.add(dto);
        }


        return serviciosMasLlamados;
    }

    @Override
    public Object[] validarDisponibilidadReserva(int idServicio, LocalDateTime fechaReserva) {
        LocalDateTime fechaInicio = fechaReserva.minusMinutes(70);
        LocalDateTime fechaFin = fechaReserva.plusMinutes(70);

        int reservasEnRango = reservaRepository.contarReservasEnRango(fechaInicio, fechaFin);
        //Si reservasEnRango es igual a 0, entonces disponibilidad se establecerá en true,
        // indicando que no hay reservas en el rango de tiempo especificado.
        boolean disponibilidad = reservasEnRango == 0;

        //DESPUES DE LA VALIDACION PROCEDO A DARLES 1 MINUTO MAS DE MARGEN PARA QUE SE DEVUELVAN COMO RESPUESTA.
        fechaInicio = fechaInicio.minusMinutes(1);
        fechaFin = fechaFin.plusMinutes(1);

        //Devuelve un objeto con los 3 valores que utilizare en el controlador.
        return new Object[]{disponibilidad, fechaInicio, fechaFin};
    }
}
