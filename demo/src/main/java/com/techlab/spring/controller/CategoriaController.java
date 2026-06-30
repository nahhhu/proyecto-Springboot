package com.techlab.spring.controller;

import com.techlab.spring.dto.CategoriaRequestDTO;
import com.techlab.spring.dto.CategoriaResponseDTO;
import com.techlab.spring.dto.ProductoResponseDTO;
import com.techlab.spring.service.CategoriaService;
import com.techlab.spring.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {
    //TODO terminar de hacer el controlador de categoria
    private final CategoriaService categoriaService;
    private final ProductoService productoService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService, ProductoService productoService) {
        this.categoriaService = categoriaService;
        this.productoService = productoService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        List<CategoriaResponseDTO> categoriaResponseDTOS = categoriaService.listarCategorias();
        return ResponseEntity.ok(categoriaResponseDTOS);
    }

    @GetMapping("/inactivas")
    public ResponseEntity<List<CategoriaResponseDTO>> listarInactivas(){
        List<CategoriaResponseDTO> categoriaResponseDTOS = categoriaService.listarInactivas();
        return ResponseEntity.ok(categoriaResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoria(@PathVariable Integer id) {
        CategoriaResponseDTO categoriaResponseDTOS = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(categoriaResponseDTOS);
    }

    @GetMapping(params = "nombre")
    public ResponseEntity<CategoriaResponseDTO> obtenerPorNombre(@RequestParam String nombre) {
        CategoriaResponseDTO categoriaResponseDTO = categoriaService.obtenerPorNombre(nombre);
        return ResponseEntity.ok(categoriaResponseDTO);
    }

    @GetMapping("/{categoriaId}/productos")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductosPorCategoria(@PathVariable Integer categoriaId) {
        List<ProductoResponseDTO> productosPorCategoria = productoService.obtenerPorCategoria(categoriaId);
        return ResponseEntity.ok(productosPorCategoria);
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
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(@PathVariable Integer id, @Valid @RequestBody CategoriaRequestDTO datos) {
        CategoriaResponseDTO categoriaResponseDTO = categoriaService.actualizar(id, datos);
        return ResponseEntity.ok(categoriaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarCategoria(@PathVariable Integer id) {
        categoriaService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> activarCategoria(@PathVariable Integer id, @RequestBody Map<String,Boolean> estado){
        if(Boolean.TRUE.equals(estado.get("activo"))){
            categoriaService.activar(id);
        }
        return ResponseEntity.noContent().build();
    }
}
