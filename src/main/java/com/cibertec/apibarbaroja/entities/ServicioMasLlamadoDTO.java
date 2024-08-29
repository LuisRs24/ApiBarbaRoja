package com.cibertec.apibarbaroja.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioMasLlamadoDTO {

    @JsonProperty("servicio")
    private ServicioEntity servicio;

    @JsonProperty("vecesReservado")
    private Long totalLlamadas;
}
