package com.cibertec.apibarbaroja.controllers;


import com.cibertec.apibarbaroja.entities.ServicioEntity;
import com.cibertec.apibarbaroja.services.ServicioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/servicios")
public class ServicioController extends BaseControllerImpl<ServicioEntity, ServicioServiceImpl>{

    @Autowired
    private ServicioServiceImpl servicio;

    @PostMapping("/crearConImagen")
    public ResponseEntity<?> crearServicioConImagen(@RequestParam("imagen") MultipartFile imagen, @ModelAttribute ServicioEntity entity){
        try {
            // Verifica si hay una imagen adjunta
            if (imagen != null && !imagen.isEmpty()) {
                // Sube la imagen y obtén la URL
                String imageUrl = servicio.subirImagenServicio(imagen, entity.getNombreServicio());
                entity.setRutaImagen(imageUrl);
                // Guarda el servicio
                return ResponseEntity.status(HttpStatus.OK).body(servicio.save(entity));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error DE IMAGEN\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error. Por favor intente más tarde.\"}, \n{" +
                            // Nomas pa ver el error xdd (BORRAR EN PRODUCCION)
                            e.getMessage() +
                            "}");
        }
    }

}
