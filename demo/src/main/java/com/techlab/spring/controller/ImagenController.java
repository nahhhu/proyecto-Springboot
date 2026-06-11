package com.techlab.spring.controller;

//TODO Crear exception por si llega a fallar la subida.

import com.techlab.spring.entity.Imagen;
import com.techlab.spring.service.ImagenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/imagenes")
@RequiredArgsConstructor
public class ImagenController {
    //TODO terminar de crear el controlador de imagen

    private final ImagenService imagenService;

    @PostMapping("/subir")
    public ResponseEntity<?> subirFoto(@RequestParam("archivo") MultipartFile archivo, @RequestParam("productoId") Integer productoId) {
        try {
            Imagen imagenGuardada = imagenService.subirImagenACloudinary(archivo, productoId);

            return ResponseEntity.ok(imagenGuardada);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir a Cloudinary:" + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error inesperado: " + e.getMessage());
        }
    }
}
