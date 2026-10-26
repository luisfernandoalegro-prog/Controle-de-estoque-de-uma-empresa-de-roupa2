package br.senai.sistema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

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

    private BigDecimal precoCusto;

    @NotNull(message = "O preço de venda é obrigatório")
    private BigDecimal precoVenda;

    @NotNull(message = "O estoque mínimo é obrigatório")
    private Integer estoqueMinimo;

    @NotNull(message = "O saldo é obrigatório")
    private Integer saldo;

    private String localizacao;

    // Relacionamento com Categoria (nullable = true permite que os dados iniciais entrem sem categoria)
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = true)
    private Categoria categoria;

    public Produto() {
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

    // Métodos de compatibilidade com Movimentações (mapeiam a quantidade para o campo saldo)
    public Integer getQuantidade() {
        return saldo != null ? saldo : 0;
    }

    public void setQuantidade(Integer quantidade) {
        this.saldo = quantidade;
    }

    // Getters e Setters tradicionais
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }

    public BigDecimal getPrecoCusto() { return precoCusto; }
    public void setPrecoCusto(BigDecimal precoCusto) { this.precoCusto = precoCusto; }

    public BigDecimal getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(BigDecimal precoVenda) { this.precoVenda = precoVenda; }

    public Integer getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(Integer estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }

    public Integer getSaldo() { return saldo; }
    public void setSaldo(Integer saldo) { this.saldo = saldo; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}