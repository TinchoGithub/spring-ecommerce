package com.proyectoecommerce.controller;

import com.proyectoecommerce.model.ProductoModel;
import com.proyectoecommerce.model.UsuarioModel;
import com.proyectoecommerce.service.ProductoService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String show(Model model){
        model.addAttribute("productos", productoService.findAll());
        return "productos/show";
    }
    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(ProductoModel producto){
        logger.info("Este es el objeto producto {}",producto);
        UsuarioModel usuario = new UsuarioModel(1L,"","","","","","","");
        producto.setUsuario(usuario);

        productoService.save(producto);
        return "redirect:/productos";
    }


}
