package com.proyectoecommerce.service;

import com.proyectoecommerce.model.UsuarioModel;
import com.proyectoecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public Optional<UsuarioModel> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public UsuarioModel save(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }
}
