package projeto;

import java.util.Scanner;

public class Gerente {
    
    public static void mostrarMenu(Scanner ler) {
        int opcao = -1;
        
        while(opcao != 5) {
            limparTerminal();
            System.out.println("\n=== MENU GERENTE ===");// um menu simples para o gerente bolado
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Ver estoque");
            System.out.println("3 - Editar produto");
            System.out.println("4 - Visualizar Lucro Total"); 
            System.out.println("5 - Voltar"); 
            System.out.print("Escolha: ");
            
            opcao = ler.nextInt();
            ler.nextLine(); // Limpar buffer
            
            switch(opcao) {
                case 1:// aqui cadastra produto
                    cadastrarProduto(ler);
                    pausar(ler);
                    break;
                case 2:// aqui é para visualizar o estoque
                    mostrarEstoque();
                    pausar(ler);
                    break;
                case 3: // se tu fez algo errado ao cadastrar um produto você pode alterar
                    editarProduto(ler);
                    break;
                case 4: // para visualizar o lucro
                    visualizarLucroTotal();
                    pausar(ler);
                    break;
                case 5: // Opção "Voltar" é 5
                    System.out.println("saindo...");
                    pausar(ler);
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private static void cadastrarProduto(Scanner ler) {
       limparTerminal();
       System.out.println("Cadastrar produto");// basicamente quando você vai cadatras o produto é pedido nome do produto, marca, tipo, valor e quantidade que sera adicionada ao estoque
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
        ler.nextLine(); // Limpar buffer
        
        Produto.getEstoque().add(new Produto(nome, marca, tipo, valor, quantidade));
        System.out.println("\nProduto cadastrado com sucesso!");
    }
    
    private static void mostrarEstoque() {
        limparTerminal();
        System.out.println("\n=== ESTOQUE ===");// ao escolher a opção estoque você ne... ve o estoque, se não tiver nenhum produto cadastrado vai aparecer uma mensagem
        if(Produto.getEstoque().isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        
        System.out.printf("%-4s %-20s %-15s %-15s %-10s %-10s%n", 
                          "ID", "Nome", "Marca", "Tipo", "Valor", "Estoque");
        System.out.println("---------------------------------------------------------------");
        
        int i = 1;
        for(Produto p : Produto.getEstoque()) {
            System.out.printf("%-4d %-20s %-15s %-15s R$%-9.2f %-10d%n",
                            i++, p.getNome(), p.getMarca(), p.getTipo(), 
                            p.getValor(), p.getQuantidade());
        }
    }
    
    private static void editarProduto(Scanner ler) { //nessa parte você edita o produto do estoque, você seleciona ele
        limparTerminal();
        mostrarEstoque();
        
        if(Produto.getEstoque().isEmpty()) {
            return;
        }
        
        System.out.print("\nDigite o ID do produto para editar: ");
        int id = ler.nextInt() - 1;
        ler.nextLine(); // Limpar buffer
        
        if(id < 0 || id >= Produto.getEstoque().size()) {
            System.out.println("ID inválido!");
            return;
        }
        
        Produto produto = Produto.getEstoque().get(id);// podendo alterar nome,marca,tipo,valor,quantidade
        
        System.out.println("\nEditando produto: " + produto);
        
        System.out.print("Novo nome (atual: " + produto.getNome() + "): ");
        produto.setNome(ler.nextLine());
        
        System.out.print("Nova marca (atual: " + produto.getMarca() + "): ");
        produto.setMarca(ler.nextLine());
        
        System.out.print("Novo tipo (atual: " + produto.getTipo() + "): ");
        produto.setTipo(ler.nextLine());
        
        System.out.print("Novo valor (atual: " + produto.getValor() + "): ");
        produto.setValor(ler.nextDouble());
        
        System.out.print("Nova quantidade (atual: " + produto.getQuantidade() + "): ");
        produto.setQuantidade(ler.nextInt());
        ler.nextLine();
        
        System.out.println("\nProduto atualizado com sucesso!");
    }

    // Novo método para visualizar o lucro total
    private static void visualizarLucroTotal() {
        limparTerminal();
        System.out.println("\n=== LUCRO TOTAL DA EMPRESA ==="); // aqui vemos oque importa $$
        System.out.printf("Total de Vendas Realizadas: R$ %.2f%n", Produto.getTotalVendasRealizadas());
    }
    
    public static void limparTerminal() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Não foi possível limpar o terminal.");
        }
    }
    
    private static void pausar(Scanner ler) {
        System.out.println("\nPressione Enter para continuar...");
        ler.nextLine();
    }
}
