package com.cibertec.apibarbaroja.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "servicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioEntity extends BaseEntity {


    @Column(name = "NombreServicio")
    private String nombreServicio;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Precio")
    private BigDecimal precio;

    @Column(name = "RutaImagen")
    private String rutaImagen;

    @ManyToOne
    @JoinColumn(name = "IdCategoria", referencedColumnName = "id")
    private CategoriaServicioEntity categoria;

}
