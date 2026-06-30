package com.techlab.spring.dto;

public record ImagenResponseDTO(
        Integer id,
        String url,
        String descripcion,
        Integer productoId
)
{}
