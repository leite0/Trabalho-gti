// Pacote do projeto.
package projeto;

// Importa as classes ArrayList e List para criar e gerenciar listas dinâmicas.
import java.util.ArrayList;
import java.util.List;

// Declaração da classe Produto, que serve como um modelo para todos os produtos da loja.
public class Produto {
    // Atributos (características) que todo produto terá.
    String nome;
    String marca;
    String tipo;
    Double valor;
    int quantidade;

    // Variável 'static' para acumular o valor total de todas as vendas.
    // 'static' significa que esta variável pertence à classe 'Produto' como um todo, e não a uma instância individual.
    // Ou seja, existe apenas uma 'totalVendasRealizadas' para a loja inteira.
    private static double totalVendasRealizadas = 0.0;

    // Construtor da classe: é chamado quando um novo objeto 'Produto' é criado (com 'new Produto(...)').
    // Ele inicializa os atributos do objeto com os valores passados como parâmetros.
    public Produto(String nome, String marca, String tipo, Double valor, int quantidade) {
        this.nome = nome;       // 'this.nome' se refere ao atributo da classe, e 'nome' ao parâmetro do método.
        this.marca = marca;
        this.tipo = tipo;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    // --- MÉTODOS GETTERS E SETTERS ---
    // Getters são usados para "pegar" ou ler o valor de um atributo.
    public String getNome() { return nome; }
    public String getMarca() { return marca; }
    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
    public int getQuantidade() { return quantidade; }

    // Setters são usados para "definir" ou alterar o valor de um atributo.
    public void setNome(String nome) { this.nome = nome; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setValor(double valor) { this.valor = valor; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    // O método 'toString' é sobrescrito para fornecer uma representação textual do objeto.
    // É útil para imprimir os detalhes de um produto de forma rápida e legível.
    @Override
    public String toString() {
        return nome + " (" + marca + ") - " + tipo + " | R$" + String.format("%.2f", valor) + " | Estoque: " + quantidade;
    }

    // --- LISTAS ESTÁTICAS ---
    // Lista estática para armazenar todos os produtos disponíveis no estoque.
    // Sendo 'static', esta lista é única e compartilhada por todo o programa.
    static List<Produto> estoque = new ArrayList<>();

    // Lista estática para armazenar os produtos que o cliente adiciona ao seu carrinho.
    // Neste modelo simples, há apenas um carrinho para toda a aplicação.
    static List<Produto> carrinho = new ArrayList<>();

    // Método estático que retorna a lista de estoque.
    public static List<Produto> getEstoque() {
        return estoque;
    }

    // Método estático que retorna la lista do carrinho.
    public static List<Produto> getCarrinho() {
        return carrinho;
    }

    // --- MÉTODOS PARA GERENCIAR VENDAS ---
    // Método estático para adicionar o valor de uma venda concluída ao total geral.
    public static void adicionarVenda(double valorVenda) {
        totalVendasRealizadas += valorVenda; // Acumula o valor.
    }

    // Método estático que retorna o valor total acumulado das vendas.
    public static double getTotalVendasRealizadas() {
        return totalVendasRealizadas;
    }
}
