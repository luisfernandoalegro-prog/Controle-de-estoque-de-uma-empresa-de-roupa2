package br.senai.sistema.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Indicador;
import br.senai.sistema.model.TipoMovimentacao;
import br.senai.sistema.repository.CategoriaRepository;
import br.senai.sistema.repository.MovimentacaoRepository;
import br.senai.sistema.repository.ProdutoRepository;

@Service
public class PainelService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public PainelService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, MovimentacaoRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public List<Indicador> getIndicadores() {
        List<Indicador> indicadores = new ArrayList<>();

        long totalProdutos = produtoRepository.count();
        long totalCategorias = categoriaRepository.count();
        long totalEntradas = movimentacaoRepository.countByTipo(TipoMovimentacao.ENTRADA);
        long totalSaidas = movimentacaoRepository.countByTipo(TipoMovimentacao.SAIDA);

        indicadores.add(new Indicador("Total de Produtos", totalProdutos, "primary"));
        indicadores.add(new Indicador("Categorias Cadastradas", totalCategorias, "success"));
        indicadores.add(new Indicador("Movimentações de Entrada", totalEntradas, "info"));
        indicadores.add(new Indicador("Movimentações de Saída", totalSaidas, "warning"));

        return indicadores;
    }
}