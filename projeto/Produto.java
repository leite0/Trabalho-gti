package projeto;

import java.util.ArrayList;
import java.util.List;

public class Produto {
    // Atributos do produto
    String nome;
    String marca;
    String tipo;
    Double valor;
    int quantidade;

    // Variável estática para acumular o total de vendas
    private static double totalVendasRealizadas = 0.0; 

    // Construtor
    public Produto(String nome, String marca, String tipo, Double valor, int quantidade) {
        this.nome = nome;
        this.marca = marca;
        this.tipo = tipo;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    // aqui tu coloca os valores e nomes e recebe de volta 
    public String getNome() { return nome; }
    public String getMarca() { return marca; }
    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
    public int getQuantidade() { return quantidade; }
    public void setNome(String nome) { this.nome = nome; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setValor(double valor) { this.valor = valor; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    // Representação textual do produto (usada para exibição)
    @Override
    public String toString() {
        return nome + " (" + marca + ") - " + tipo + " | R$" + String.format("%.2f", valor) + " | Estoque: " + quantidade;
    }

    // Listas que armazenam o estoque e o carrinho de compras
    static List<Produto> estoque = new ArrayList<>();
    static List<Produto> carrinho = new ArrayList<>();

    public static List<Produto> getEstoque() {
        return estoque;
    }

    public static List<Produto> getCarrinho() {
        return carrinho;
    }

    // Métodos para gerenciar o total de vendas
    public static void adicionarVenda(double valorVenda) { 
        totalVendasRealizadas += valorVenda;
    }

    public static double getTotalVendasRealizadas() { 
        return totalVendasRealizadas;
    }
}
