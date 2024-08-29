package com.cibertec.apibarbaroja.entities;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "categorias_servicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaServicioEntity extends BaseEntity {
    @Column(name = "NombreCategoria")
    private String nombreCategoria;
}
