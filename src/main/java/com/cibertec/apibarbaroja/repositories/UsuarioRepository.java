package com.cibertec.apibarbaroja.repositories;

import com.cibertec.apibarbaroja.entities.UsuarioEntity;

public interface UsuarioRepository extends BaseRepository<UsuarioEntity, Integer>{
    UsuarioEntity findByCorreo(String correo);
}
