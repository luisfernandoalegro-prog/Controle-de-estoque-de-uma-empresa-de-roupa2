package br.senai.sistema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O código é obrigatório")
    private String codigo;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
    private String nome;

    @NotBlank(message = "A unidade é obrigatória")
    private String unidade;

    @PositiveOrZero(message = "O preço de custo não pode ser negativo")
    private BigDecimal precoCusto;

    @NotNull(message = "O preço de venda é obrigatório")
    @PositiveOrZero(message = "O preço de venda não pode ser negativo")
    private BigDecimal precoVenda;

    @NotNull(message = "O estoque mínimo é obrigatório")
    @PositiveOrZero(message = "O estoque mínimo não pode ser negativo")
    private Integer estoqueMinimo;

    @NotNull(message = "O saldo é obrigatório")
    @PositiveOrZero(message = "O saldo não pode ser negativo")
    private Integer saldo;

    private String localizacao;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = true)
    private Categoria categoria;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimentacao> movimentacoes = new ArrayList<>();

    // Construtores
    public Produto() {
    }

    public Produto(String codigo, String nome, String unidade, BigDecimal precoCusto, BigDecimal precoVenda, Integer estoqueMinimo, Integer saldo, String localizacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.unidade = unidade;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.estoqueMinimo = estoqueMinimo;
        this.saldo = saldo;
        this.localizacao = localizacao;
    }

    public Produto(String codigo, String nome, String unidade, BigDecimal precoCusto, BigDecimal precoVenda, Integer estoqueMinimo, Integer saldo, String localizacao, String campoExtra) {
        this.codigo = codigo;
        this.nome = nome;
        this.unidade = unidade;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.estoqueMinimo = estoqueMinimo;
        this.saldo = saldo;
        this.localizacao = localizacao;
    }

    private void validarNaoNegativo(Integer valor, String campo) {
        if (valor != null && valor < 0) {
            throw new IllegalArgumentException(campo + " não pode ser negativo");
        }
    }

    // Métodos de compatibilidade com Movimentações e Thymeleaf (p.quantidade e p.estoque)
    public Integer getQuantidade() {
        return saldo != null ? saldo : 0;
    }

    public void setQuantidade(Integer quantidade) {
        validarNaoNegativo(quantidade, "A quantidade");
        this.saldo = quantidade;
    }

    public Integer getEstoque() {
        return saldo != null ? saldo : 0;
    }

    public void setEstoque(Integer estoque) {
        validarNaoNegativo(estoque, "O estoque");
        this.saldo = estoque;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }

    public BigDecimal getPrecoCusto() { return precoCusto; }
    public void setPrecoCusto(BigDecimal precoCusto) {
        if (precoCusto != null && precoCusto.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço de custo não pode ser negativo");
        }
        this.precoCusto = precoCusto;
    }

    public BigDecimal getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(BigDecimal precoVenda) {
        if (precoVenda != null && precoVenda.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço de venda não pode ser negativo");
        }
        this.precoVenda = precoVenda;
    }

    public Integer getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(Integer estoqueMinimo) {
        validarNaoNegativo(estoqueMinimo, "O estoque mínimo");
        this.estoqueMinimo = estoqueMinimo;
    }

    public Integer getSaldo() { return saldo; }
    public void setSaldo(Integer saldo) {
        validarNaoNegativo(saldo, "O saldo");
        this.saldo = saldo;
    }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public List<Movimentacao> getMovimentacoes() { return movimentacoes; }
    public void setMovimentacoes(List<Movimentacao> movimentacoes) { this.movimentacoes = movimentacoes; }
}