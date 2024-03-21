package com.moto.service.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "motos")
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    private String marca;
    private String modelo;
    private int usuarioId;

    public Moto(){

    }


}
