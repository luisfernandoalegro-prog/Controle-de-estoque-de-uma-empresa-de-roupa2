package br.senai.sistema.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sistema.model.Movimentacao;
import br.senai.sistema.model.Produto;
import br.senai.sistema.model.TipoMovimentacao;
import br.senai.sistema.repository.MovimentacaoRepository;
import br.senai.sistema.repository.ProdutoRepository;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, ProdutoRepository produtoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<Movimentacao> listarTodas() {
        return movimentacaoRepository.findAll();
    }

    public List<Movimentacao> buscarPorTipo(TipoMovimentacao tipo) {
        if (tipo == null) {
            return listarTodas();
        }
        return movimentacaoRepository.findByTipo(tipo);
    }

    @Transactional
    public void salvar(Movimentacao movimentacao) {
        Produto produto = produtoRepository.findById(movimentacao.getProduto().getId())
                .orElseThrow(() -> new RegraNegocioException("Produto inválido selecionado."));

        int saldoAtual = produto.getSaldo() != null ? produto.getSaldo() : 0;

        if (movimentacao.getTipo() == TipoMovimentacao.ENTRADA) {
            produto.setSaldo(saldoAtual + movimentacao.getQuantidade());
        } else if (movimentacao.getTipo() == TipoMovimentacao.SAIDA) {
            if (saldoAtual < movimentacao.getQuantidade()) {
                throw new RegraNegocioException("Quantidade em stock insuficiente para esta saída!");
            }
            produto.setSaldo(saldoAtual - movimentacao.getQuantidade());
        }

        produtoRepository.save(produto);
        movimentacaoRepository.save(movimentacao);
    }

    public Optional<Movimentacao> buscarPorId(Long id) {
        return movimentacaoRepository.findById(id);
    }

    @Transactional
    public void excluir(Long id) {
        Movimentacao mov = movimentacaoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Movimentação não encontrada."));
        
        Produto produto = mov.getProduto();
        int saldoAtual = produto.getSaldo() != null ? produto.getSaldo() : 0;

        // Reverte a operação no estoque ao excluir
        if (mov.getTipo() == TipoMovimentacao.ENTRADA) {
            produto.setSaldo(saldoAtual - mov.getQuantidade());
        } else if (mov.getTipo() == TipoMovimentacao.SAIDA) {
            produto.setSaldo(saldoAtual + mov.getQuantidade());
        }
        produtoRepository.save(produto);
        
        movimentacaoRepository.deleteById(id);
    }
}