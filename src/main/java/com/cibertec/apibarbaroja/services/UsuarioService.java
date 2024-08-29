package com.cibertec.apibarbaroja.services;

import com.cibertec.apibarbaroja.entities.UsuarioEntity;

import org.springframework.http.ResponseEntity;

public interface UsuarioService  extends BaseService<UsuarioEntity, Integer>{

    ResponseEntity<?> loginUsuario(String correo, String pass);
}
