package com.proyectoecommerce.service;

import com.proyectoecommerce.model.OrdenModel;
import com.proyectoecommerce.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImpl implements OrdenService{

    @Autowired
    private OrdenRepository ordenRepository;
    @Override
    public OrdenModel save(OrdenModel ordenModel) {
        return ordenRepository.save(ordenModel);
    }
}
