package br.senai.sistema.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.senai.sistema.model.Movimentacao;
import br.senai.sistema.model.TipoMovimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    
    // Usado para contar no PainelService
    long countByTipo(TipoMovimentacao tipo);

    // Usado para buscar/filtrar no MovimentacaoService
    List<Movimentacao> findByTipo(TipoMovimentacao tipo);
}