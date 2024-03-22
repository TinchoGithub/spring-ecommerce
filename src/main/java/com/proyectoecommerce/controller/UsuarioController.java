package com.proyectoecommerce.controller;

import com.proyectoecommerce.model.UsuarioModel;
import com.proyectoecommerce.repository.UsuarioRepository;
import com.proyectoecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String create(){

        return "usuario/registro";
    }

    @PostMapping("/save")
    public String save(UsuarioModel usuario){
        logger.info("Usuario registro: {}", usuario);
        usuario.setTipo("USER");
        usuarioService.save(usuario);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "usuario/login";
    }

    @PostMapping("/acceder")
    public String acceder(UsuarioModel usuario, HttpSession session){
        logger.info("Accesos: {}", usuario);

        Optional<UsuarioModel> user = usuarioService.findByEmail(usuario.getEmail());
        //logger.info("Usuario de db: {}", user.get());

        if (user.isPresent()){
            session.setAttribute("idUsuario", user.get().getId());
            if (user.get().getTipo().equals("ADMIN")){
                return "redirect:/administrador";
            }else {
                return "redirect:/";
            }
        }else {
            logger.info("Usuario no exite");
        }

        return "redirect:/";
    }



}
