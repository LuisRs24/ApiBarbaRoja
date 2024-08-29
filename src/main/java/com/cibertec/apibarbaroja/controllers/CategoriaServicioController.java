package com.cibertec.apibarbaroja.controllers;


import com.cibertec.apibarbaroja.entities.CategoriaServicioEntity;
import com.cibertec.apibarbaroja.services.CategoriaServicioServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/categoriaServicios")
public class CategoriaServicioController extends BaseControllerImpl<CategoriaServicioEntity, CategoriaServicioServiceImpl>{
}
