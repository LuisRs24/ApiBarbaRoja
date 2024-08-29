package com.cibertec.apibarbaroja.services;

import com.cibertec.apibarbaroja.entities.UsuarioEntity;
import com.cibertec.apibarbaroja.repositories.BaseRepository;
import com.cibertec.apibarbaroja.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<UsuarioEntity, Integer> implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(BaseRepository<UsuarioEntity, Integer> baseRepository) {
        super(baseRepository);
    }


    @Override
    public ResponseEntity<?> loginUsuario(String correo, String pass) {
        try {
            // Buscar el usuario por correo
            UsuarioEntity usuario = usuarioRepository.findByCorreo(correo);

            if (usuario != null) {
                // Si el usuario existe se valida la contraseña.
                if (usuario.getPass().equals(pass)) {
                    // Si la contraseña es válida, se devuelve el usuario y un mensaje de éxito
                    return ResponseEntity.ok(Map.of(
                            "usuario", usuario,
                            "mensaje", "Inicio de sesión exitoso"
                    ));
                } else {
                    // Si la contraseña es incorrecta se devuelve otro mensaje
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
                }
            } else {
                // Si el correo no existe se envia el mensaje de error correspondiente
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo no encontrado");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
