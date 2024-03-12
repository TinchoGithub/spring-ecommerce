package com.proyectoecommerce.service;

import com.proyectoecommerce.model.ProductoModel;
import com.proyectoecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    @Override
    public ProductoModel save(ProductoModel producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<ProductoModel> get(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public void update(ProductoModel producto) {
        productoRepository.save(producto);
    }


    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }
}
