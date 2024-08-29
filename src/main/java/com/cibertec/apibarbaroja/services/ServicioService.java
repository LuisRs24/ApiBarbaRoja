package com.cibertec.apibarbaroja.services;

import com.cibertec.apibarbaroja.entities.ServicioEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ServicioService extends BaseService<ServicioEntity, Integer>{
    String subirImagenServicio(MultipartFile file, String nombreServicio) throws IOException;

}
