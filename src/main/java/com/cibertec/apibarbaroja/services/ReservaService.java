package com.cibertec.apibarbaroja.services;

import com.cibertec.apibarbaroja.entities.ReservaEntity;
import com.cibertec.apibarbaroja.entities.ServicioMasLlamadoDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaService extends BaseService<ReservaEntity, Integer>{

    List<ReservaEntity> reservasPorUsuario(int idUsuario) throws Exception;

    List<ReservaEntity> reservasProximas() throws Exception;
    /*DONDE:
        estado 1: "PENDIENTE"
        estado 2: "FINALIZADA"
        estado 3: "CANCELADA"
     */



    String updateEstadoByIdReserva(int idReserva, int estado) throws Exception;
    List<ServicioMasLlamadoDTO> getServiciosMasLlamados();
    Object[] validarDisponibilidadReserva(int idServicio, LocalDateTime fechaReserva);















}
