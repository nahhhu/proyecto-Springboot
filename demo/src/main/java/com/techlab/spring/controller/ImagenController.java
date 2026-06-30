package com.techlab.spring.controller;

//TODO Crear exception por si llega a fallar la subida.

import com.techlab.spring.dto.ImagenResponseDTO;
import com.techlab.spring.entity.Imagen;
import com.techlab.spring.mapper.ImagenMapper;
import com.techlab.spring.service.ImagenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/imagenes")
@RequiredArgsConstructor
public class ImagenController {

    private final ImagenService imagenService;
    private final ImagenMapper mapper;

    @PostMapping("/")
    public ResponseEntity<ImagenResponseDTO> subirFoto(@RequestParam("archivo") MultipartFile archivo, @RequestParam("productoId") Integer productoId, @RequestParam(value = "descripcion", required = false) String descripcion) {
        try {
            Imagen imagenGuardada = imagenService.subirImagenACloudinary(archivo, productoId,descripcion);

            return ResponseEntity.ok(mapper.toDto(imagenGuardada));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al subir a Cloudinary: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error inesperado: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id){
        imagenService.borrarImagen(id);
        return ResponseEntity.noContent().build();
    }

}
