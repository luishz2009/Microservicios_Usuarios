package com.usuario.service.model;

import lombok.Data;

@Data
public class Moto {

    private String marca;
    private String modelo;
    private int usuarioId;

    public Moto(){

    }
}
