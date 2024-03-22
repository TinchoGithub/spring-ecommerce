package com.proyectoecommerce.service;

import com.proyectoecommerce.model.UsuarioModel;

import java.util.Optional;

public interface UsuarioService {

    public Optional<UsuarioModel> findById(Long id);
    public UsuarioModel save(UsuarioModel usuario);
    Optional<UsuarioModel> findByEmail(String email);
}
