package br.senai.sistema.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * Entidade Produto: representa o produto que o sistema guarda.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela "produtos" no banco.
 * As anotações de validação (@NotBlank, @Size...) definem as regras dos campos do formulário.
 */
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Código interno do produto (não se repete). */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 20, message = "Use no máximo 20 caracteres.")
    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    /** Nome do produto. */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 100, message = "Use no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    /** Descrição detalhada do produto. */
    @Size(max = 255, message = "Use no máximo 255 caracteres.")
    @Column(length = 255)
    private String descricao;

    /** Unidade de medida (UN, CX, KG, PAR...). */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 10, message = "Use no máximo 10 caracteres.")
    @Column(nullable = false, length = 10)
    private String unidade;

    /** Valor pago por unidade. */
    @DecimalMin(value = "0.00", message = "O valor não pode ser negativo.")
    @Column(precision = 10, scale = 2)
    private BigDecimal precoCusto;

    /** Preço de venda ao público. */
    @DecimalMin(value = "0.00", message = "O valor não pode ser negativo.")
    @Column(precision = 10, scale = 2)
    private BigDecimal precoVenda;

    /** Quantidade mínima desejada em estoque. */
    @NotNull(message = "Campo obrigatório.")
    @PositiveOrZero(message = "O valor não pode ser negativo.")
    @Column(nullable = false)
    private Integer estoqueMinimo;

    /** Quantidade atual (será atualizada pelas movimentações). */
    @NotNull(message = "Campo obrigatório.")
    @PositiveOrZero(message = "O valor não pode ser negativo.")
    @Column(nullable = false)
    private Integer saldo;

    /** Localização física no armazém. */
    @Size(max = 30, message = "Use no máximo 30 caracteres.")
    @Column(length = 30)
    private String localizacao;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VariacaoProduto> variacoes;

    /** Construtor vazio: obrigatório para o JPA e para o formulário criarem objetos. */
    public Produto() {
    }

    /** Construtor completo com todos os dados principais e localização. */
    public Produto(String codigo, String nome, String descricao, BigDecimal precoCusto, BigDecimal precoVenda, Integer estoqueMinimo, Integer saldo, String unidade, String localizacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.estoqueMinimo = estoqueMinimo;
        this.saldo = saldo;
        this.unidade = unidade;
        this.localizacao = localizacao;
    }

    // ---- Getters e setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(BigDecimal precoCusto) {
        this.precoCusto = precoCusto;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public List<VariacaoProduto> getVariacoes() {
        return variacoes;
    }

    public void setVariacoes(List<VariacaoProduto> variacoes) {
        this.variacoes = variacoes;
    }
}