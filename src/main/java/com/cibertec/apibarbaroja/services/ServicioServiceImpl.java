package com.cibertec.apibarbaroja.services;


import com.cibertec.apibarbaroja.entities.ServicioEntity;
import com.cibertec.apibarbaroja.repositories.BaseRepository;
import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ServicioServiceImpl extends BaseServiceImpl<ServicioEntity, Integer> implements ServicioService {
    private final Cloudinary cloudinary;

    public ServicioServiceImpl(BaseRepository<ServicioEntity, Integer> baseRepository, Cloudinary cloudinary) {
        super(baseRepository);
        this.cloudinary = cloudinary;
    }

    @Override
    public String subirImagenServicio(MultipartFile file, String nombreServicio) throws IOException {
        return cloudinary.uploader()
                /*El REPLACE ALL y el TO LOWERCASE sirven para darle formato al nombre de la imagen
                (Sera el nombre del servicio Reemplazando los espacios por guiones bajos)*/
                .upload(file.getBytes(),
                        Map.of("public_id", nombreServicio.replaceAll(" ", "_")
                                //Para pasar todo a minusculas
                                .toLowerCase()
                                //Para quitar los espacios
                                .trim()))
                .get("url").toString();
    }
}
