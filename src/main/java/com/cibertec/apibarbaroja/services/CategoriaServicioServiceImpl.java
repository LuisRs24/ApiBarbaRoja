package com.cibertec.apibarbaroja.services;

import com.cibertec.apibarbaroja.entities.CategoriaServicioEntity;
import com.cibertec.apibarbaroja.repositories.BaseRepository;
import org.springframework.stereotype.Service;


@Service
public class CategoriaServicioServiceImpl extends BaseServiceImpl<CategoriaServicioEntity, Integer> implements CategoriaServicioService {
    public CategoriaServicioServiceImpl(BaseRepository<CategoriaServicioEntity, Integer> baseRepository) {
        super(baseRepository);
    }
}
