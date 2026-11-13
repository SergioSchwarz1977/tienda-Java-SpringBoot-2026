package com.ecommerce.tienda.service;

import java.util.List;

import com.ecommerce.tienda.entity.Categoria;
import com.ecommerce.tienda.repository.CategoriaRepository;

import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }


    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria findById(int id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    
    public void deleteById(int id) {
        categoriaRepository.deleteById(id);
    }


}
