package com.proyectoecommerce.repository;

import com.proyectoecommerce.model.OrdenModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<OrdenModel, Long> {
}
