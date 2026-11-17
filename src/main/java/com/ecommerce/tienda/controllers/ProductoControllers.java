package com.ecommerce.tienda.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.tienda.dto.DTOProductoCategoria;
import com.ecommerce.tienda.entity.Producto;
import com.ecommerce.tienda.exception.ProductoNoEncontradoException;
import com.ecommerce.tienda.service.ProductoService;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:5173/")
public class ProductoControllers {
    private final ProductoService productoService;

    public ProductoControllers(ProductoService productoService) {
        this.productoService = productoService;
    }

    @RequestMapping("/listar")
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }

    @RequestMapping("/listarConCategoria")
    public ResponseEntity<List<DTOProductoCategoria>> listarConCategoria() {
        return ResponseEntity.ok(productoService.listarConCategoria());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable int id) {
        Producto producto = productoService.getProductoById(id);            
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping("/crear")
    public ResponseEntity<Producto>crear(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(producto));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable int id, @RequestBody Producto producto) {
        Producto productoActual = productoService.getProductoById(id);
        if (productoActual == null) {
           
            throw new ProductoNoEncontradoException("Producto no encontrado");
        }
        productoActual.setNombre(producto.getNombre());
        productoActual.setDescripcion(producto.getDescripcion());
        productoActual.setCategoria(producto.getCategoria());
        productoActual.setPrecio(producto.getPrecio());
        productoActual.setStock(producto.getStock());
        productoActual.setCategoria(producto.getCategoria());
        return ResponseEntity.ok(productoService.actualizar(productoActual));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
