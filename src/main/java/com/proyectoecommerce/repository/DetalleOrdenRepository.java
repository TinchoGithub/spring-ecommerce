package com.proyectoecommerce.repository;

import com.proyectoecommerce.model.DetalleOrdenModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrdenModel, Long> {
}
