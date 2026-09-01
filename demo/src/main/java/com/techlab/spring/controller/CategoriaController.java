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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorias", description = "Operaciones para gestionar categorias")
public class CategoriaController {
    //TODO terminar de hacer el controlador de categoria
    private final CategoriaService categoriaService;
    private final ProductoService productoService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService, ProductoService productoService) {
        this.categoriaService = categoriaService;
        this.productoService = productoService;
    }

    @Operation(summary = "Listar categorias", description = "Devuelve todas las categorias")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operacion exitosa")})
    @GetMapping("/")
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        List<CategoriaResponseDTO> categoriaResponseDTOS = categoriaService.listarCategorias();
        return ResponseEntity.ok(categoriaResponseDTOS);
    }

    @Operation(summary = "Listar categorias inactivas", description = "Devuelve categorias marcadas como inactivas")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operacion exitosa")})
    @GetMapping("/inactivas")
    public ResponseEntity<List<CategoriaResponseDTO>> listarInactivas(){
        List<CategoriaResponseDTO> categoriaResponseDTOS = categoriaService.listarInactivas();
        return ResponseEntity.ok(categoriaResponseDTOS);
    }

    @Operation(summary = "Obtener categoria", description = "Obtener una categoria por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Encontrada"), @ApiResponse(responseCode = "404", description = "No encontrada")})
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoria(@PathVariable Integer id) {
        CategoriaResponseDTO categoriaResponseDTOS = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(categoriaResponseDTOS);
    }

    @Operation(summary = "Obtener por nombre", description = "Obtener categoria por nombre (query param 'nombre')")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Encontrada"), @ApiResponse(responseCode = "404", description = "No encontrada")})
    @GetMapping(params = "nombre")
    public ResponseEntity<CategoriaResponseDTO> obtenerPorNombre(@RequestParam String nombre) {
        CategoriaResponseDTO categoriaResponseDTO = categoriaService.obtenerPorNombre(nombre);
        return ResponseEntity.ok(categoriaResponseDTO);
    }

    @Operation(summary = "Obtener productos por categoria", description = "Devuelve productos relacionados a una categoria por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operacion exitosa")})
    @GetMapping("/{categoriaId}/productos")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductosPorCategoria(@PathVariable Integer categoriaId) {
        List<ProductoResponseDTO> productosPorCategoria = productoService.obtenerPorCategoria(categoriaId);
        return ResponseEntity.ok(productosPorCategoria);
    }


    @Operation(summary = "Crear categoria", description = "Crear una nueva categoria")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Creada"), @ApiResponse(responseCode = "400", description = "Datos invalidos")})
    @PostMapping("/")
    public ResponseEntity<CategoriaResponseDTO> crear(@Valid @RequestBody CategoriaRequestDTO nuevo) {
        CategoriaResponseDTO creado = categoriaService.crear(nuevo);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @Operation(summary = "Crear varias categorias", description = "Crear una lista de categorias")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Creadas"), @ApiResponse(responseCode = "400", description = "Datos invalidos")})
    @PostMapping("/list")
    public ResponseEntity<List<CategoriaResponseDTO>> crearCategorias(@Valid @RequestBody List<CategoriaRequestDTO> categorias) {
        List<CategoriaResponseDTO> creado = categoriaService.crearCategorias(categorias);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar categoria", description = "Actualizar una categoria por ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Actualizada"), @ApiResponse(responseCode = "404", description = "No encontrada")})
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(@PathVariable Integer id, @Valid @RequestBody CategoriaRequestDTO datos) {
        CategoriaResponseDTO categoriaResponseDTO = categoriaService.actualizar(id, datos);
        return ResponseEntity.ok(categoriaResponseDTO);
    }

    @Operation(summary = "Desactivar categoria", description = "Marcar una categoria como inactiva")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Sin contenido"), @ApiResponse(responseCode = "404", description = "No encontrada")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarCategoria(@PathVariable Integer id) {
        categoriaService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Activar categoria", description = "Marcar una categoria como activa")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Sin contenido"), @ApiResponse(responseCode = "400", description = "Solicitud invalida")})
    @PatchMapping("/{id}")
    public ResponseEntity<Void> activarCategoria(@PathVariable Integer id, @RequestBody Map<String,Boolean> estado){
        if(Boolean.TRUE.equals(estado.get("activo"))){
            categoriaService.activar(id);
        }
        return ResponseEntity.noContent().build();
    }
}
