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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/imagenes")
@RequiredArgsConstructor
@Tag(name = "Imagenes", description = "Operaciones para subir y borrar imagenes")
public class ImagenController {

    private final ImagenService imagenService;
    private final ImagenMapper mapper;

    @Operation(summary = "Subir imagen", description = "Sube una imagen y la asocia a un producto")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Imagen subida"), @ApiResponse(responseCode = "500", description = "Error en servidor")})
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

    @Operation(summary = "Borrar imagen", description = "Borra una imagen por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Borrada"), @ApiResponse(responseCode = "404", description = "No encontrada")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id){
        imagenService.borrarImagen(id);
        return ResponseEntity.noContent().build();
    }

}
