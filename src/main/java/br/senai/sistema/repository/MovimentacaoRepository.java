package br.senai.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.senai.sistema.model.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
}