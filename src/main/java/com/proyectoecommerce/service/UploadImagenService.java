package com.proyectoecommerce.service;

import com.proyectoecommerce.controller.ProductoController;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadImagenService {

    private String folder="images//";
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    public String saveImage(MultipartFile file){
        try {
            if(!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(folder + file.getOriginalFilename());
                Files.write(path, bytes);
                return file.getOriginalFilename();
            }
        }catch (Exception e){
            logger.info("¡ERROR!, archivo vacío");
        }
        return "default.jpg";
    }
    public void delete(String nombre){
        String ruta = "images//";
        File file = new File(ruta+nombre);
        file.delete();
    }
}
