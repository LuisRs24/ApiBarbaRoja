package com.cibertec.apibarbaroja.controllers;

import com.cibertec.apibarbaroja.entities.ReservaEntity;
import com.cibertec.apibarbaroja.entities.ServicioMasLlamadoDTO;
import com.cibertec.apibarbaroja.services.ReservaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/reservas")
public class ReservaController extends BaseControllerImpl<ReservaEntity, ReservaServiceImpl>{

    @Autowired
    private ReservaServiceImpl reservaService;

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<?> reservasPorCliente(@PathVariable int idCliente) {
        try {
            List<ReservaEntity> reservas = reservaService.reservasPorUsuario(idCliente);
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            //Jajaja hay un monton de httpStatus, hay que usarlos
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("mensaje", e.getMessage()));
        }
    }

    @GetMapping("/reservasProximas")
    public ResponseEntity<?> reservasProximas() {
        try {
            List<ReservaEntity> reservas = reservaService.reservasProximas();
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("mensaje", e.getMessage()));
        }
    }

    /*DONDE:
    estado 1: "PENDIENTE"
    estado 2: "FINALIZADA"
    estado 3: "CANCELADA"
    */
    @PutMapping("/actualizarEstado/{idReserva}")
    public ResponseEntity<?> actualizarEstadoReserva(@PathVariable Integer idReserva, @RequestParam Integer estado) {
        try {
            if (estado < 4) {
                String mensaje = reservaService.updateEstadoByIdReserva(idReserva, estado);
                return ResponseEntity.ok(Map.of("mensaje", mensaje));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("mensaje", "Elija por favor un estado valido"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al actualizar el estado de la reserva: " + e.getMessage()));
        }
    }

    @GetMapping("/serviciosDestacados")
    public ResponseEntity<List<ServicioMasLlamadoDTO>> getServiciosMasLlamados() {
        List<ServicioMasLlamadoDTO> serviciosMasLlamados = reservaService.getServiciosMasLlamados();
        return ResponseEntity.ok(serviciosMasLlamados);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearReserva(@RequestBody ReservaEntity reserva) {
        try {
            // Validar hora de la reserva
            LocalTime horaReserva = reserva.getFechaReserva().toLocalTime();

            if (horaReserva.isBefore(LocalTime.of(10, 0)) || horaReserva.isAfter(LocalTime.of(21, 0))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("mensaje", "La hora de la reserva debe estar entre las 10:00am y las 09:00pm"));
            }

            System.out.println("Fecha y hora de la reserva: " + reserva);

            // Validar disponibilidad de reserva
            Object[] resultado = reservaService.validarDisponibilidadReserva(reserva.getId(), reserva.getFechaReserva());
            boolean disponibilidad = (boolean) resultado[0];

            if (disponibilidad) {
                reservaService.save(reserva);
                return ResponseEntity.ok(Map.of("mensaje", "Reserva creada correctamente"));
            } else {
                LocalDateTime inicio = (LocalDateTime) resultado[1];
                LocalDateTime fin = (LocalDateTime) resultado[2];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

                String inicioFormateado = inicio.format(formatter);
                String finFormateado = fin.format(formatter);

                String mensaje = "Ya existe una reserva para el mismo servicio en el rango de tiempo especificado. " +
                        "Prueba con estas horas: " + inicioFormateado + " o " + finFormateado;

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("mensaje", mensaje));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al crear la reserva: " + e.getMessage()));
        }
    }
}
