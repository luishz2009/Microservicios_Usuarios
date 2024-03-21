package com.carro.service.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "carros")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    private String marca;
    private String modelo;
    private int usuarioId;

    public Carro() {
    }
}
