// Pacote do projeto.
package projeto;

// Importa a classe Scanner para ler a entrada do usuário.
import java.util.Scanner;

// Declaração da classe Cliente, que contém todas as funcionalidades disponíveis para o cliente.
public class Cliente {

    // Método principal que exibe o menu do cliente e gerencia suas ações.
    public static void menuCliente(Scanner ler) {
        // Variável 'opcao' para armazenar a escolha do usuário no menu. Inicializada com -1.
        int opcao = -1;

        // Loop 'while' que mantém o menu do cliente ativo até que a opção 6 (Sair) seja escolhida.
        while (opcao != 6) {
            Gerente.limparTerminal(); // Limpa a tela.
            System.out.println("=== MENU CLIENTE ==="); // Cabeçalho do menu.
            System.out.println("1 - Ver produtos");
            System.out.println("2 - Adicionar ao carrinho");
            System.out.println("3 - Ver carrinho");
            System.out.println("4 - Ir para pagamento");
            System.out.println("5 - Editar carrinho");
            System.out.println("6 - Sair");
            System.out.print("Escolha: ");
            opcao = ler.nextInt(); // Lê a opção do usuário.
            ler.nextLine(); // Limpa o buffer do scanner.

            // Estrutura 'switch' para tratar a opção escolhida pelo cliente.
            switch (opcao) {
                case 1: // Opção para ver os produtos disponíveis no estoque.
                    mostrarEstoque();
                    pausar(ler); // Pausa para o usuário ver a lista.
                    break;

                case 2: // Opção para adicionar um produto ao carrinho de compras.
                    adicionarCarrinho(ler);
                    break;

                case 3: // Opção para visualizar o conteúdo do carrinho.
                    mostrarCarrinho();
                    pausar(ler);
                    break;

                case 4: // Opção para proceder ao pagamento dos itens no carrinho.
                    menuPagamento(ler);
                    break;

                case 5: // Opção para editar (alterar quantidade ou remover) um item do carrinho.
                    editarCarrinho(ler);
                    break;

                case 6: // Opção para sair do menu do cliente e voltar ao menu principal.
                    sairCarrinho(); // Devolve os itens do carrinho ao estoque antes de sair.
                    System.out.println("Voltando ao menu principal...");
                    break; // O loop 'while' será encerrado pois 'opcao' agora é 6.

                default: // Caso a opção seja inválida.
                    System.out.println("Opção inválida.");
                    pausar(ler);
            }
        }
    }

    // Método para exibir todos os produtos do estoque.
    private static void mostrarEstoque() {
        Gerente.limparTerminal();
        System.out.println("\n=== ESTOQUE ===");
        // Verifica se a lista de estoque está vazia.
        if (Produto.getEstoque().isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            // Imprime um cabeçalho formatado para a tabela de produtos.
            System.out.printf("%-4s %-15s %-15s %-15s %-10s %-10s%n",
                "ID", "Nome", "Marca", "Tipo", "Valor", "No Estoque");
            System.out.println("-----------------------------------------------------------------");
            // Itera pela lista de estoque e imprime os detalhes de cada produto.
            for (int i = 0; i < Produto.getEstoque().size(); i++) {
                Produto p = Produto.getEstoque().get(i);
                // Imprime cada produto formatado na tabela. O ID é o índice da lista + 1.
                System.out.printf("%-4d %-15s %-15s %-15s R$%-9.2f %-10d%n",
                    i + 1, p.getNome(), p.getMarca(), p.getTipo(), p.getValor(), p.getQuantidade());
            }
        }
    }

    // Método para adicionar um produto ao carrinho.
    private static void adicionarCarrinho(Scanner ler) {
        Gerente.limparTerminal();
        // Verifica se há produtos no estoque para vender.
        if (Produto.getEstoque().isEmpty()) {
            System.out.println("Nenhum produto no estoque.");
            pausar(ler);
            return; // Retorna ao menu anterior.
        }

        mostrarEstoque(); // Mostra o estoque para o cliente escolher.

        System.out.print("Digite o número do produto para adicionar ao carrinho: ");
        int indice = ler.nextInt() - 1; // Lê o ID e subtrai 1 para obter o índice da lista.
        ler.nextLine(); // Limpa o buffer.

        // Valida se o índice do produto é válido.
        if (indice < 0 || indice >= Produto.getEstoque().size()) {
            System.out.println("Produto inválido.");
            pausar(ler);
            return;
        }

        // Obtém o produto selecionado da lista de estoque.
        Produto selecionado = Produto.getEstoque().get(indice);

        System.out.print("Quantidade desejada: ");
        int qtd = ler.nextInt(); // Lê a quantidade desejada.
        ler.nextLine();

        // Valida a quantidade.
        if (qtd <= 0) {
            System.out.println("Quantidade deve ser maior que zero.");
        } else if (qtd > selecionado.getQuantidade()) {
            System.out.println("Quantidade insuficiente no estoque.");
        } else {
            // Cria um *novo* objeto Produto para representar o item no carrinho.
            // Isso é importante para não confundir o item do carrinho com o item do estoque.
            Produto itemCarrinho = new Produto(
                selecionado.getNome(),
                selecionado.getMarca(),
                selecionado.getTipo(),
                selecionado.getValor(),
                qtd // A quantidade aqui é a que o cliente quer, não a total do estoque.
            );
            // Adiciona o novo item à lista do carrinho.
            Produto.getCarrinho().add(itemCarrinho);

            // Atualiza o estoque, diminuindo a quantidade do produto original.
            selecionado.setQuantidade(selecionado.getQuantidade() - qtd);

            System.out.println("Produto adicionado ao carrinho!");
        }

        pausar(ler);
    }

    // Método para mostrar os itens que estão no carrinho de compras.
    private static void mostrarCarrinho() {
        Gerente.limparTerminal();
        System.out.println("\n=== SEU CARRINHO ===");
        if (Produto.getCarrinho().isEmpty()) {
            System.out.println("Carrinho vazio.");
        } else {
            double total = 0; // Variável para acumular o valor total da compra.
            // Imprime o cabeçalho da tabela do carrinho.
            System.out.printf("%-4s %-15s %-10s %-10s %-10s%n", "ID", "Nome", "Qtd", "Valor Unit.", "Subtotal");
            System.out.println("------------------------------------------------");
            // Itera pela lista do carrinho.
            for (int i = 0; i < Produto.getCarrinho().size(); i++) {
                Produto p = Produto.getCarrinho().get(i);
                double subtotal = p.getValor() * p.getQuantidade(); // Calcula o subtotal para este item.
                // Imprime os detalhes do item no carrinho.
                System.out.printf("%-4d %-15s %-10d R$%-9.2f R$%-9.2f%n",
                    i + 1, p.getNome(), p.getQuantidade(), p.getValor(), subtotal);
                total += subtotal; // Adiciona o subtotal ao total geral.
            }
            // Imprime o valor total a ser pago.
            System.out.printf("\nTOTAL A PAGAR: R$ %.2f%n", total);
        }
    }

    // Método para o processo de pagamento.
    private static void menuPagamento(Scanner ler) {
        Gerente.limparTerminal();
        // Não permite o pagamento se o carrinho estiver vazio.
        if (Produto.getCarrinho().isEmpty()) {
            System.out.println("Carrinho vazio. Não há o que pagar.");
            pausar(ler);
            return;
        }

        mostrarCarrinho(); // Mostra o resumo da compra.

        // Calcula o total da compra.
        double totalDaCompra = 0;
        for (Produto p : Produto.getCarrinho()) {
            totalDaCompra += p.getValor() * p.getQuantidade();
        }

        System.out.println("\nEscolha a forma de pagamento:");
        System.out.println("1 - Pix");
        System.out.println("2 - Cartão");
        System.out.println("3 - Dinheiro");
        System.out.print("Opção: ");
        int opcao = ler.nextInt();
        ler.nextLine();

        // Apenas exibe uma mensagem com base na forma de pagamento, sem lógica complexa.
        switch (opcao) {
            case 1: System.out.println("Pagamento via Pix selecionado."); break;
            case 2: System.out.println("Pagamento via Cartão selecionado."); break;
            case 3: System.out.println("Pagamento em Dinheiro selecionado."); break;
            default: System.out.println("Opção inválida.");
        }

        // Adiciona o valor total da compra ao registro de lucro da loja.
        Produto.adicionarVenda(totalDaCompra);

        System.out.println("Obrigado pela compra!");
        Produto.getCarrinho().clear(); // Limpa o carrinho, pois a compra foi finalizada.
        pausar(ler);
    }
    
    // Método para editar a quantidade de um item no carrinho ou removê-lo.
    private static void editarCarrinho(Scanner ler) {
        Gerente.limparTerminal();
        if (Produto.getCarrinho().isEmpty()) {
            System.out.println("Carrinho vazio. Não há itens para editar.");
            pausar(ler);
            return;
        }

        mostrarCarrinho(); // Mostra o carrinho para o usuário escolher o que editar.
        
        System.out.println("\n=== EDITAR CARRINHO ===");
        System.out.print("Digite o ID do item que deseja editar ou remover: ");
        int indice = ler.nextInt() - 1; // Lê o ID e converte para índice.
        ler.nextLine();

        if (indice < 0 || indice >= Produto.getCarrinho().size()) {
            System.out.println("ID de item inválido.");
            pausar(ler);
            return;
        }
        
        // Obtém o item do carrinho que será editado.
        Produto itemParaEditar = Produto.getCarrinho().get(indice);
        
        System.out.println("\nItem selecionado: " + itemParaEditar.getNome() + " (Qtd atual: " + itemParaEditar.getQuantidade() + ")");
        System.out.println("1 - Alterar quantidade");
        System.out.println("2 - Remover item");
        System.out.print("Escolha: ");
        int opcaoEdicao = ler.nextInt();
        ler.nextLine();

        switch (opcaoEdicao) {
            case 1: // Alterar quantidade
                System.out.print("Digite a nova quantidade (0 para remover): ");
                int novaQtd = ler.nextInt();
                ler.nextLine();

                if (novaQtd < 0) {
                    System.out.println("Quantidade inválida.");
                } else {
                    // Encontra o produto correspondente no estoque para verificar a disponibilidade e fazer ajustes.
                    Produto produtoNoEstoque = null;
                    for (Produto pEstoque : Produto.getEstoque()) {
                        // Compara nome, marca e tipo para garantir que é o mesmo produto.
                        if (pEstoque.getNome().equals(itemParaEditar.getNome()) &&
                            pEstoque.getMarca().equals(itemParaEditar.getMarca()) &&
                            pEstoque.getTipo().equals(itemParaEditar.getTipo())) {
                            produtoNoEstoque = pEstoque;
                            break;
                        }
                    }

                    if (produtoNoEstoque != null) {
                        // Calcula a diferença entre a nova quantidade e a antiga no carrinho.
                        int diferencaQtd = novaQtd - itemParaEditar.getQuantidade();
                        
                        // Verifica se o estoque tem unidades suficientes para cobrir o aumento.
                        if (diferencaQtd > produtoNoEstoque.getQuantidade()) {
                            System.out.println("Quantidade insuficiente no estoque para aumentar.");
                        } else {
                            // Atualiza a quantidade do item no carrinho.
                            itemParaEditar.setQuantidade(novaQtd);
                            // Ajusta a quantidade no estoque (remove a diferença do estoque).
                            produtoNoEstoque.setQuantidade(produtoNoEstoque.getQuantidade() - diferencaQtd);
                            System.out.println("Quantidade do item atualizada.");
                            
                            // Se a nova quantidade for 0, remove o item do carrinho.
                            if (novaQtd == 0) {
                                Produto.getCarrinho().remove(indice);
                                System.out.println("Item removido do carrinho.");
                            }
                        }
                    } else {
                        System.out.println("Erro: Produto original não encontrado no estoque.");
                    }
                }
                break;

            case 2: // Remover item
                // Devolve a quantidade do item removido de volta para o estoque.
                for (Produto pEstoque : Produto.getEstoque()) {
                    if (pEstoque.getNome().equals(itemParaEditar.getNome()) &&
                        pEstoque.getMarca().equals(itemParaEditar.getMarca()) &&
                        pEstoque.getTipo().equals(itemParaEditar.getTipo())) {
                        pEstoque.setQuantidade(pEstoque.getQuantidade() + itemParaEditar.getQuantidade());
                        break;
                    }
                }
                // Remove o item da lista do carrinho.
                Produto.getCarrinho().remove(indice);
                System.out.println("Item removido do carrinho.");
                break;

            default:
                System.out.println("Opção inválida.");
        }
        pausar(ler);
    }
    
    // Método chamado quando o cliente sai do menu para devolver os itens ao estoque.
    private static void sairCarrinho() {
        // Se o carrinho já estiver vazio, não faz nada.
        if (Produto.getCarrinho().isEmpty()) {
            return;
        }

        // Itera por cada item no carrinho.
        for (Produto item : Produto.getCarrinho()) {
            // Encontra o produto correspondente no estoque.
            for (Produto pEstoque : Produto.getEstoque()) {
                if (pEstoque.getNome().equals(item.getNome()) &&
                    pEstoque.getMarca().equals(item.getMarca()) &&
                    pEstoque.getTipo().equals(item.getTipo())) {
                    // Devolve a quantidade do item do carrinho de volta para o estoque.
                    pEstoque.setQuantidade(pEstoque.getQuantidade() + item.getQuantidade());
                    break; // Para o loop interno, pois o produto já foi encontrado e atualizado.
                }
            }
        }

        Produto.getCarrinho().clear(); // Esvazia o carrinho.
        System.out.println("Itens do carrinho foram devolvidos ao estoque.");
    }
    
    // Método auxiliar para pausar a execução.
    private static void pausar(Scanner ler) {
        System.out.println("\nPressione Enter para continuar...");
        ler.nextLine();
    }
}
