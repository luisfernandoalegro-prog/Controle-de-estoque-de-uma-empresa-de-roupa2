package br.senai.sistema.model;

public class Indicador {
    private String titulo;
    private Object valor;
    private String cor;

    public Indicador(String titulo, Object valor, String cor) {
        this.titulo = titulo;
        this.valor = valor;
        this.cor = cor;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Object getValor() { return valor; }
    public void setValor(Object valor) { this.valor = valor; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
}