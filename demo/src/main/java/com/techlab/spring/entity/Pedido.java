package com.techlab.spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String estado;

    private double total;

    private LocalDateTime fecha;

    @ManyToOne
    private Usuario usuario;

    @ManyToMany
    private List<Producto> productos;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
    }
}
