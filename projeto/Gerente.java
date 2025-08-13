// Pacote do projeto.
package projeto;

// Importa a classe Scanner para ler a entrada do usuário.
import java.util.Scanner;

// Declaração da classe Gerente, que contém as funcionalidades administrativas.
public class Gerente {
    
    // Método que exibe o menu principal para o gerente.
    public static void mostrarMenu(Scanner ler) {
        // Variável 'opcao' para armazenar a escolha do gerente no menu.
        int opcao = -1;
        
        // Loop 'while' que mantém o menu ativo até que a opção 5 (Voltar) seja escolhida.
        while(opcao != 5) {
            limparTerminal(); // Limpa a tela para uma nova exibição do menu.
            System.out.println("\n=== MENU GERENTE ==="); // Cabeçalho do menu.
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Ver estoque");
            System.out.println("3 - Editar produto");
            System.out.println("4 - Visualizar Lucro Total"); 
            System.out.println("5 - Voltar"); 
            System.out.print("Escolha: ");
            
            opcao = ler.nextInt(); // Lê a opção do gerente.
            ler.nextLine(); // Limpa o buffer do scanner.
            
            // Estrutura 'switch' para tratar a opção escolhida.
            switch(opcao) {
                case 1: // Opção para cadastrar um novo produto.
                    cadastrarProduto(ler);
                    break;
                case 2: // Opção para visualizar o estoque completo.
                    mostrarEstoque();
                    pausar(ler); // Pausa para o gerente poder analisar o estoque.
                    break;
                case 3: // Opção para editar os detalhes de um produto existente.
                    editarProduto(ler);
                    break;
                case 4: // Opção para visualizar o total de vendas realizadas.
                    visualizarLucroTotal();
                    pausar(ler);
                    break;
                case 5: // Opção para voltar ao menu principal.
                    System.out.println("Voltando...");
                    // Nenhuma ação extra é necessária, o loop 'while' vai terminar.
                    break;
                default: // Caso a opção seja inválida.
                    System.out.println("Opção inválida!");
                    pausar(ler);
            }
        }
    }
    
    // Método para cadastrar um novo produto no sistema.
    private static void cadastrarProduto(Scanner ler) {
       limparTerminal();
       System.out.println("--- Cadastrar Produto ---"); // Título da tela de cadastro.
       // Solicita e lê cada informação do produto.
        System.out.print("\nNome: ");
        String nome = ler.nextLine();
        
        System.out.print("Marca: ");
        String marca = ler.nextLine();
        
        System.out.print("Tipo: ");
        String tipo = ler.nextLine();
        
        System.out.print("Valor: ");
        double valor = ler.nextDouble();
        
        System.out.print("Quantidade: ");
        int quantidade = ler.nextInt();
        ler.nextLine(); // Limpa o buffer.
        
        // Cria uma nova instância de 'Produto' com os dados fornecidos e a adiciona à lista estática 'estoque'.
        Produto.getEstoque().add(new Produto(nome, marca, tipo, valor, quantidade));
        System.out.println("\nProduto cadastrado com sucesso!");
        pausar(ler);
    }
    
    // Método para mostrar todos os produtos em estoque.
    private static void mostrarEstoque() {
        limparTerminal();
        System.out.println("\n=== ESTOQUE ===");
        // Verifica se a lista de estoque está vazia.
        if(Produto.getEstoque().isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return; // Encerra o método se não houver produtos.
        }
        
        // Imprime um cabeçalho formatado para a tabela de estoque.
        System.out.printf("%-4s %-20s %-15s %-15s %-10s %-10s%n", 
                          "ID", "Nome", "Marca", "Tipo", "Valor", "Estoque");
        System.out.println("--------------------------------------------------------------------");
        
        int i = 1; // Contador para o ID do produto na exibição.
        // Itera pela lista de estoque.
        for(Produto p : Produto.getEstoque()) {
            // Imprime os detalhes de cada produto de forma formatada.
            System.out.printf("%-4d %-20s %-15s %-15s R$%-9.2f %-10d%n",
                            i++, p.getNome(), p.getMarca(), p.getTipo(), 
                            p.getValor(), p.getQuantidade());
        }
    }
    
    // Método para editar um produto existente no estoque.
    private static void editarProduto(Scanner ler) {
        limparTerminal();
        mostrarEstoque(); // Mostra o estoque para o gerente saber qual ID editar.
        
        if(Produto.getEstoque().isEmpty()) {
            return; // Se não há produtos, não há o que editar.
        }
        
        System.out.print("\nDigite o ID do produto para editar: ");
        int id = ler.nextInt() - 1; // Lê o ID e converte para o índice da lista (ID-1).
        ler.nextLine(); // Limpa o buffer.
        
        // Valida se o ID fornecido é um índice válido para a lista de estoque.
        if(id < 0 || id >= Produto.getEstoque().size()) {
            System.out.println("ID inválido!");
            pausar(ler);
            return;
        }
        
        // Obtém a referência ao objeto 'Produto' que será editado.
        Produto produto = Produto.getEstoque().get(id);
        
        System.out.println("\n--- Editando produto: " + produto.getNome() + " ---");
        // Solicita cada novo dado, mostrando o valor atual entre parênteses.
        System.out.print("Novo nome (atual: " + produto.getNome() + "): ");
        produto.setNome(ler.nextLine()); // Usa o método 'setter' para atualizar o nome.
        
        System.out.print("Nova marca (atual: " + produto.getMarca() + "): ");
        produto.setMarca(ler.nextLine()); // Atualiza a marca.
        
        System.out.print("Novo tipo (atual: " + produto.getTipo() + "): ");
        produto.setTipo(ler.nextLine()); // Atualiza o tipo.
        
        System.out.print("Novo valor (atual: " + produto.getValor() + "): ");
        produto.setValor(ler.nextDouble()); // Atualiza o valor.
        
        System.out.print("Nova quantidade (atual: " + produto.getQuantidade() + "): ");
        produto.setQuantidade(ler.nextInt()); // Atualiza a quantidade.
        ler.nextLine(); // Limpa o buffer.
        
        System.out.println("\nProduto atualizado com sucesso!");
        pausar(ler);
    }

    // Método para visualizar o faturamento total da loja.
    private static void visualizarLucroTotal() {
        limparTerminal();
        System.out.println("\n=== LUCRO TOTAL DA EMPRESA ===");
        // Busca o valor total das vendas e o exibe formatado com duas casas decimais.
        System.out.printf("Total de Vendas Realizadas: R$ %.2f%n", Produto.getTotalVendasRealizadas());
    }
    
    // Método utilitário para limpar a tela do console.
    public static void limparTerminal() {
        try {
            // Verifica se o sistema operacional é Windows.
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                // Se for Windows, executa o comando 'cls' no prompt de comando.
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Para outros sistemas (Linux, macOS), usa códigos de escape ANSI para limpar a tela.
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Se ocorrer um erro ao tentar limpar o terminal, exibe uma mensagem.
            System.out.println("Não foi possível limpar o terminal.");
        }
    }
    
    // Método auxiliar para pausar a execução até o Enter ser pressionado.
    private static void pausar(Scanner ler) {
        System.out.println("\nPressione Enter para continuar...");
        ler.nextLine();
    }
}
