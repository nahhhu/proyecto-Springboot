package com.techlab.spring.controller;

import com.techlab.spring.dto.CategoriaRequestDTO;
import com.techlab.spring.dto.CategoriaResponseDTO;
import com.techlab.spring.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    //TODO terminar de hacer el controlador de categoria
    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        List<CategoriaResponseDTO> categoriaResponseDTOS = categoriaService.listarCategorias();
        return ResponseEntity.ok(categoriaResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoria(@PathVariable int id) {
        CategoriaResponseDTO categoriaResponseDTOS = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(categoriaResponseDTOS);
    }

    @GetMapping
    public ResponseEntity<CategoriaResponseDTO> obtenerPorNombre(@RequestParam String nombre) {
        CategoriaResponseDTO categoriaResponseDTO = categoriaService.obtenerPorNombre(nombre);
        return ResponseEntity.ok(categoriaResponseDTO);
    }

    @PostMapping("/")
    public ResponseEntity<CategoriaResponseDTO> crear(@Valid @RequestBody CategoriaRequestDTO nuevo) {
        CategoriaResponseDTO creado = categoriaService.crear(nuevo);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PostMapping("/list")
    public ResponseEntity<List<CategoriaResponseDTO>> crearCategorias(@Valid @RequestBody List<CategoriaRequestDTO> categorias) {
        List<CategoriaResponseDTO> creado = categoriaService.crearCategorias(categorias);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(@PathVariable int id, @Valid @RequestBody CategoriaRequestDTO datos) {
        CategoriaResponseDTO categoriaResponseDTO = categoriaService.actualizar(id, datos);
        return ResponseEntity.ok(categoriaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarCategoria(@PathVariable int id) {
        categoriaService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    //Todo crear metodo para activar categoria
}
