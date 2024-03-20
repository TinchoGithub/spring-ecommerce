package com.proyectoecommerce.controller;

import com.proyectoecommerce.model.DetalleOrdenModel;
import com.proyectoecommerce.model.OrdenModel;
import com.proyectoecommerce.model.ProductoModel;
import com.proyectoecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoService productoService;
    List<DetalleOrdenModel> detalles = new ArrayList<DetalleOrdenModel>();
    OrdenModel orden = new OrdenModel();

    @GetMapping("")
    public String home(Model model){

        model.addAttribute("productos", productoService.findAll());
        return "usuario/home";
    }
    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Long id, Model model){
        log.info("id producto enviado como parametro {}", id);
        ProductoModel producto = new ProductoModel();
        Optional<ProductoModel> productoModelOptional = productoService.get(id);
        producto = productoModelOptional.get();
        model.addAttribute("producto", producto);
        return "usuario/producto_home";
    }

    @PostMapping("/cart")
    public String addcart(@RequestParam Long id, @RequestParam int cantidad, Model model){
        DetalleOrdenModel detalleOrden = new DetalleOrdenModel();
        ProductoModel producto = new ProductoModel();
        double sumaTotal = 0;

        Optional<ProductoModel> optionalProducto = productoService.get(id);
        log.info("El producto añadido es : {}", optionalProducto.get());
        log.info("cantidad: {}", cantidad);
        producto= optionalProducto.get();
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);
        detalles.add(detalleOrden);
        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable Long id, Model model){

        List<DetalleOrdenModel> ordenesNuevas = new ArrayList<DetalleOrdenModel>();

        for (DetalleOrdenModel detalleOrdenModel: detalles){
            if (detalleOrdenModel.getProducto().getId() != id){
                ordenesNuevas.add(detalleOrdenModel);
            }
        }
        detalles = ordenesNuevas;
        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

}
