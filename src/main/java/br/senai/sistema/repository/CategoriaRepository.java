package br.senai.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.senai.sistema.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}