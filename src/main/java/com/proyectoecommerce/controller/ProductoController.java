package com.proyectoecommerce.controller;

import com.proyectoecommerce.model.ProductoModel;
import com.proyectoecommerce.model.UsuarioModel;
import com.proyectoecommerce.service.ProductoService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        ProductoModel producto = new ProductoModel();
        Optional<ProductoModel> optionalProducto = productoService.get(id);
        producto = optionalProducto.get();

        logger.info("Producto buscado: {}", producto);
        model.addAttribute("producto", producto);
        return "/productos/edit";
    }
    @PostMapping("/update")
    public String update(ProductoModel producto){
        productoService.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productoService.delete(id);
        return "redirect:/productos";
    }



}
