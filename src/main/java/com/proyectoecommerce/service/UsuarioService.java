package com.proyectoecommerce.service;

import com.proyectoecommerce.model.UsuarioModel;

import java.util.Optional;

public interface UsuarioService {

    public Optional<UsuarioModel> findById(Long id);
}
