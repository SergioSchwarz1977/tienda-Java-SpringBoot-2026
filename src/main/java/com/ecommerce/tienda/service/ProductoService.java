package com.ecommerce.tienda.service;

import java.util.List;

import com.ecommerce.tienda.dto.DTOProductoCategoria;
import com.ecommerce.tienda.entity.Producto;
import com.ecommerce.tienda.repository.ProductoRepository;

import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;

    public ProductoService(ProductoRepository productoRepository, CategoriaService categoriaService) {
        this.productoRepository = productoRepository;
        this.categoriaService = categoriaService;
    }

    public List<DTOProductoCategoria> listarConCategoria() {
        return productoRepository.listarConCategoria();
    }
    
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public Producto obtener(int id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto crear(Producto producto) {
        return productoRepository.save(producto);
    }

     public Producto getProductoById(int id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto actualizar(Producto producto){  
        return productoRepository.save(producto);
    }

    public void  eliminar(int id){
        productoRepository.deleteById(id);    
    }

   public List<Producto> findByCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
        
    }
}
