package com.cibertec.apibarbaroja.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="usuarios")
public class UsuarioEntity extends BaseEntity{

    @Column(name = "NombreUsuario")
    private String nombreUsuario;

    @Column(name = "ApellidoUsuario")
    private String apellidoUsuario;

    @Column(name = "Correo")
    private String correo;

    @Column(name = "Pass")
    private String pass;

    @Column(name = "Telefono")
    private String telefono;

    @Column(name = "isAdmin")
    private boolean isAdmin;

}
