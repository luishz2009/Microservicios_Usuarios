package com.usuario.service.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    private String nombre;
    private String email;

    public Usuario() {
    }
}
