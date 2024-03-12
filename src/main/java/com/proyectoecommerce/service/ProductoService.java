package com.proyectoecommerce.service;

import com.proyectoecommerce.model.ProductoModel;

import java.util.Optional;

public interface ProductoService {

    public ProductoModel save(ProductoModel producto);
    public Optional<ProductoModel> get(Long id);
    public void update(ProductoModel producto);
    public void delete(Long id);
}
