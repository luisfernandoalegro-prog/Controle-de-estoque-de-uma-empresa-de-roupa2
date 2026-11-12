package br.senai.sistema.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Indicador;
import br.senai.sistema.model.Produto;
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

    public long getTotalProdutos() {
        return produtoRepository.count();
    }

    public long getProdutosAbaixoMinimoCount() {
        return produtoRepository.findAll().stream()
                .filter(p -> p.getQuantidade() <= p.getEstoqueMinimo())
                .count();
    }

    public List<Produto> getProdutosAbaixoMinimo() {
        return produtoRepository.findAll().stream()
                .filter(p -> p.getQuantidade() <= p.getEstoqueMinimo())
                .toList();
    }

    public long getTotalEntradas() {
        try {
            return movimentacaoRepository.countByTipo(TipoMovimentacao.ENTRADA);
        } catch (Exception e) {
            return 0;
        }
    }

    public long getTotalSaidas() {
        try {
            return movimentacaoRepository.countByTipo(TipoMovimentacao.SAIDA);
        } catch (Exception e) {
            return 0;
        }
    }

    public double getValorTotalEstoque() {
        List<Produto> produtos = produtoRepository.findAll();
        BigDecimal total = BigDecimal.ZERO;
        for (Produto p : produtos) {
            if (p.getPrecoCusto() != null) {
                BigDecimal qtd = BigDecimal.valueOf(p.getQuantidade());
                BigDecimal subtotal = p.getPrecoCusto().multiply(qtd);
                total = total.add(subtotal);
            }
        }
        return total.doubleValue();
    }

    // --- DIFERENCIAL AVA 5: Status de Saúde do Estoque ---
    public String getStatusSaudeEstoque() {
        long total = produtoRepository.count();
        if (total == 0) return "Sistema Inicializado - Aguardando Cadastros";
        
        long abaixo = getProdutosAbaixoMinimoCount();
        if (abaixo > 0) {
            return "⚠️ Atenção: " + abaixo + " produto(s) precisam de reposição urgente!";
        }
        return "✅ Estoque Saudável - Todos os níveis acima do mínimo.";
    }
}