package com.proyectoecommerce.service;

import com.proyectoecommerce.model.OrdenModel;
import com.proyectoecommerce.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenServiceImpl implements OrdenService{

    @Autowired
    private OrdenRepository ordenRepository;

    @Override
    public List<OrdenModel> findAll() {
        return ordenRepository.findAll();
    }

    public String generarNumeroOrden(){

        int numero = 0;
        String numeroConcatenado = "";

        List<OrdenModel> ordenes = findAll();
        List<Integer> numeros = new ArrayList<Integer>();
        ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));
        if (ordenes.isEmpty()){
            numero = 1;
        }else{
            numero = numeros.stream().max(Integer::compare).get();
            numero++;
        }
        if (numero<10){
            numeroConcatenado = "000000000"+String.valueOf(numero);
        } else if (numero<100) {
            numeroConcatenado = "00000000"+String.valueOf(numero);
        } else if (numero<1000) {
            numeroConcatenado = "0000000"+String.valueOf(numero);
        } else if (numero<10000) {
            numeroConcatenado = "000000"+String.valueOf(numero);
        }
        return numeroConcatenado;
    }

    @Override
    public OrdenModel save(OrdenModel ordenModel) {
        return ordenRepository.save(ordenModel);
    }
}
