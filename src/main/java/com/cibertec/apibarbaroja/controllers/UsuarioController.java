package com.cibertec.apibarbaroja.controllers;


import com.cibertec.apibarbaroja.entities.UsuarioEntity;
import com.cibertec.apibarbaroja.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/usuarios")
public class UsuarioController extends BaseControllerImpl<UsuarioEntity, UsuarioServiceImpl>{
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @PostMapping("/loginUsuario")
    public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody) {
        String correo = requestBody.get("correo");
        String pass = requestBody.get("pass");

        ResponseEntity<?> respuesta = usuarioService.loginUsuario(correo, pass);

        if (respuesta.getStatusCode().is2xxSuccessful()) {
            // Inicio de sesión exitoso: se accede a los datos del usuario y al mensaje de éxito
            Map<String, Object> responseBody = (Map<String, Object>) respuesta.getBody();
            UsuarioEntity usuario = (UsuarioEntity) responseBody.get("usuario");
            String mensaje = (String) responseBody.get("mensaje");
            return ResponseEntity.ok(Map.of(
                    "usuario", usuario,
                    "mensaje", mensaje
            ));
        } else {
            // Inicio de sesión fallido: se devuelve el mensaje de error
            return ResponseEntity.status(respuesta.getStatusCode()).body(Map.of("mensajeError", respuesta.getBody()));
        }
    }
}
