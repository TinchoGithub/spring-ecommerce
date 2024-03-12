package com.proyectoecommerce.repository;

import com.proyectoecommerce.model.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoModel, Long> {
}
