package br.senai.sistema.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    // Método para buscar categorias por parte do nome (ignorando maiúsculas/minúsculas)
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
}