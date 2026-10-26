package br.senai.sistema.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sistema.model.Movimentacao;
import br.senai.sistema.model.Produto;
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

    @Transactional
    public void salvar(Movimentacao movimentacao) {
        Produto produto = produtoRepository.findById(movimentacao.getProduto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Produto inválido"));

        int saldoAtual = produto.getSaldo() != null ? produto.getSaldo() : 0;

        if ("ENTRADA".equalsIgnoreCase(movimentacao.getTipo())) {
            produto.setSaldo(saldoAtual + movimentacao.getQuantidade());
        } else if ("SAIDA".equalsIgnoreCase(movimentacao.getTipo())) {
            if (saldoAtual < movimentacao.getQuantidade()) {
                throw new RuntimeException("Quantidade em stock insuficiente!");
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
                .orElseThrow(() -> new IllegalArgumentException("Movimentação inválida"));
        
        Produto produto = mov.getProduto();
        int saldoAtual = produto.getSaldo() != null ? produto.getSaldo() : 0;

        if ("ENTRADA".equalsIgnoreCase(mov.getTipo())) {
            produto.setSaldo(saldoAtual - mov.getQuantidade());
        } else if ("SAIDA".equalsIgnoreCase(mov.getTipo())) {
            produto.setSaldo(saldoAtual + mov.getQuantidade());
        }
        produtoRepository.save(produto);
        
        movimentacaoRepository.deleteById(id);
    }
}