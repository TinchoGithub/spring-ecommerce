package com.proyectoecommerce.controller;

import com.proyectoecommerce.model.DetalleOrdenModel;
import com.proyectoecommerce.model.OrdenModel;
import com.proyectoecommerce.model.ProductoModel;
import com.proyectoecommerce.model.UsuarioModel;
import com.proyectoecommerce.service.DetalleOrdenService;
import com.proyectoecommerce.service.OrdenService;
import com.proyectoecommerce.service.ProductoService;
import com.proyectoecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private DetalleOrdenService detalleOrdenService;

    @Autowired
    private ProductoService productoService;
    @Autowired
    private UsuarioService usuarioService;
    List<DetalleOrdenModel> detalles = new ArrayList<DetalleOrdenModel>();
    OrdenModel orden = new OrdenModel();

    @GetMapping("")
    public String home(Model model, HttpSession session){

        log.info("Session del usuario: {}", session.getAttribute("idUsuario"));
        model.addAttribute("productos", productoService.findAll());

        //Session

        model.addAttribute("sesion", session.getAttribute("idUsuario"));

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

        // VALIDAR QUE NO SE AGREGUE EL PRODUCTO 2 VECES
        Long idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);

        if (!ingresado){
            detalles.add(detalleOrden);
        }

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

    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session){

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        //sesion
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "/usuario/carrito";
    }

    @GetMapping("/orden")
    public String verOrden(Model model, HttpSession session){

        UsuarioModel usuario = usuarioService.findById(Long.parseLong(session.getAttribute("idUsuario").toString())).get();

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);
        return "usuario/resumenOrden";
    }

    @GetMapping("/saveOrden")
    public String saveOrden(HttpSession session){

        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());

        UsuarioModel usuario = usuarioService.findById(Long.parseLong(session.getAttribute("idUsuario").toString())).get();
        orden.setUsuario(usuario);
        ordenService.save(orden);

        //guardar detalles
        for (DetalleOrdenModel dt:detalles){
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }

        //Limpio lista y orden
        orden = new OrdenModel();
        detalles.clear();

        return "redirect:/";
    }
    @PostMapping("/buscar")
    public String buscarProducto(@RequestParam String nombre, Model model){
        log.info("Nombre del producto: {}", nombre);
        List<ProductoModel> listaProductos = productoService.findAll().stream().filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("productos", listaProductos);

        return "usuario/home";
    }

}
