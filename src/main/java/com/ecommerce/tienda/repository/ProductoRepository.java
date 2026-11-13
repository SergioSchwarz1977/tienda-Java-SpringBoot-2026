package com.ecommerce.tienda.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.tienda.dto.DTOProductoCategoria;
import com.ecommerce.tienda.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByNombreContainingIgnoreCase(String texto);

    List<Producto> findByCategoria(String categoria);

    @Query("SELECT SUM(p.precio * p.stock) FROM Producto p")
    BigDecimal calcularValorInventario();

   // @Query("SELECT new com.ecommerce.tienda.dto.DTOProductoCategoria(p.id, p.nombre, p.descripcion, p.precio, p.stock, c.categoria) " + "FROM Producto p JOIN p.categoria c")
   // List<DTOProductoCategoria> listarConCategoria();

    @Query("SELECT new com.ecommerce.tienda.dto.DTOProductoCategoria(p.id, p.nombre, p.descripcion, p.precio, p.stock, c.categoria) FROM Producto p JOIN p.categoria c")
    List<DTOProductoCategoria> listarConCategoria();

}
