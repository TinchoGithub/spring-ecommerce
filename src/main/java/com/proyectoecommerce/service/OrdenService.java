package com.proyectoecommerce.service;

import com.proyectoecommerce.model.OrdenModel;

import java.util.List;

public interface OrdenService {

    List<OrdenModel> findAll();
    public OrdenModel save(OrdenModel ordenModel);
    public String generarNumeroOrden();
}
