package com.proyectoecommerce.repository;

import com.proyectoecommerce.model.OrdenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends JpaRepository<OrdenModel, Long> {
}
