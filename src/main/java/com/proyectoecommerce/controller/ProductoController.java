package com.proyectoecommerce.controller;

import com.proyectoecommerce.model.ProductoModel;
import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @GetMapping("")
    public String show(){
        return "productos/show";
    }
    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(ProductoModel producto){
        logger.info("Este es el objeto producto {}",producto);
        return "redirect:/productos";
    }


}
