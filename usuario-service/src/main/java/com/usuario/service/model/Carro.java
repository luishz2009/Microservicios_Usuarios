package com.usuario.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Carro {

    private String marca;
    private String modelo;
    private int usuarioId;

    public Carro(){

    }
}
