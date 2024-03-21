package com.proyectoecommerce.service;

import com.proyectoecommerce.model.DetalleOrdenModel;
import com.proyectoecommerce.repository.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenServiceImpl implements DetalleOrdenService{

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;
    @Override
    public DetalleOrdenModel save(DetalleOrdenModel detalleOrdenModel) {
        return detalleOrdenRepository.save(detalleOrdenModel);
    }
}
