package com.proyectoecommerce.controller;

import com.proyectoecommerce.model.ProductoModel;
import com.proyectoecommerce.model.UsuarioModel;
import com.proyectoecommerce.service.ProductoService;
import com.proyectoecommerce.service.UploadImagenService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);
    private UploadImagenService uploadImagenService;

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
    public String save(ProductoModel producto,@RequestParam("img") MultipartFile file){
        logger.info("Este es el objeto producto {}",producto);
        UsuarioModel usuario = new UsuarioModel(1L,"","","","","","","");
        producto.setUsuario(usuario);

        if (producto.getId() == null){
            String nombreImagen = uploadImagenService.saveImage(file);
            producto.setImagen(nombreImagen);
        } else if (file.isEmpty()) {
            ProductoModel p = new ProductoModel();
            p = productoService.get(producto.getId()).get();
            producto.setImagen(p.getImagen());

        }else {
            String nombreImagen = uploadImagenService.saveImage(file);
            producto.setImagen(nombreImagen);
        }

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
