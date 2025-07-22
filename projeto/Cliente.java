package projeto;

import java.util.Scanner;

public class Cliente {
    public static void menuCliente(Scanner ler) {
        int opcao = -1;

        while (opcao != 6) {
            Gerente.limparTerminal();
            System.out.println("=== MENU CLIENTE ==="); //menu padrão
            System.out.println("1 - Ver produtos");
            System.out.println("2 - Adicionar ao carrinho");
            System.out.println("3 - Ver carrinho");
            System.out.println("4 - Ir para pagamento");
            System.out.println("5 - Editar carrinho"); 
            System.out.println("6 - Sair");
            System.out.print("Escolha: ");
            opcao = ler.nextInt();
            ler.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1: // aqui ele ve o estoque.
                    mostrarEstoque();
                    pausar(ler);
                    break;

                case 2: // aqui ele adiciona produtos ao seu carrinho
                    adicionarCarrinho(ler);
                    break;

                case 3: // aqui ele olha o conteudo do carrinho
                    mostrarCarrinho();
                    pausar(ler);
                    break;

                case 4: //aqui o cliente vai para a area de pagamento
                    menuPagamento(ler);
                    break;

                case 5: // Nova opção para editar o carrinho
                    editarCarrinho(ler);
                    break;

                case 6: // aqui ele volta pro menu principal
                    sairCarrinho();
                    pausar(ler);
                    break;

                default:
                    System.out.println("Opção inválida.");
                    pausar(ler);
            }
        }
    }

    // parte do estoque onde é mostrado o estoque ao cliente
    private static void mostrarEstoque() {
        Gerente.limparTerminal();
        System.out.println("\n=== ESTOQUE ===");
        if (Produto.getEstoque().isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            System.out.printf("%-4s %-15s %-15s %-15s %-10s %-10s%n",
                "ID", "Nome", "Marca", "Tipo", "Valor", "No Estoque");
            System.out.println("-----------------------------------------------------------");
            for (int i = 0; i < Produto.getEstoque().size(); i++) {
                Produto p = Produto.getEstoque().get(i);
                System.out.printf("%-4d %-15s %-15s %-15s R$%-9.2f %-10d%n",
                    i + 1, p.getNome(), p.getMarca(), p.getTipo(), p.getValor(), p.getQuantidade());
            }
        }
    }
     // aqui adiciona produtos ao carrinho, podendo escolher os produtos e a quantidade
    private static void adicionarCarrinho(Scanner ler) {
        Gerente.limparTerminal();
        if (Produto.getEstoque().isEmpty()) {
            System.out.println("Nenhum produto no estoque.");
            pausar(ler);
            return;
        }

        mostrarEstoque();

        System.out.print("Digite o número do produto para adicionar ao carrinho: ");
        int indice = ler.nextInt() - 1;
        ler.nextLine();

        if (indice < 0 || indice >= Produto.getEstoque().size()) {
            System.out.println("Produto inválido.");
            pausar(ler);
            return;
        }

        Produto selecionado = Produto.getEstoque().get(indice);

        System.out.print("Quantidade desejada: ");
        int qtd = ler.nextInt();
        ler.nextLine();

        if (qtd <= 0) {
            System.out.println("Quantidade deve ser maior que zero.");
        } else if (qtd > selecionado.getQuantidade()) {
            System.out.println("Quantidade insuficiente no estoque.");
        } else {
            // Adiciona ao carrinho criando um novo objeto Produto (com a quantidade desejada)
            Produto itemCarrinho = new Produto(
                selecionado.getNome(),
                selecionado.getMarca(),
                selecionado.getTipo(),
                selecionado.getValor(),
                qtd
            );
            Produto.getCarrinho().add(itemCarrinho);

            // Atualiza o estoque diminuindo a quantidade vendida
            selecionado.setQuantidade(selecionado.getQuantidade() - qtd);

            System.out.println("Produto adicionado ao carrinho!");
        }

        pausar(ler);
    }
    // você tem acesso ao conteudo do carrinho ou ve se ele ta vazio
    private static void mostrarCarrinho() {
        Gerente.limparTerminal();
        System.out.println("\n=== SEU CARRINHO ===");
        if (Produto.getCarrinho().isEmpty()) {
            System.out.println("Carrinho vazio.");
        } else {
            double total = 0;
            System.out.printf("%-4s %-15s %-10s %-10s %-10s%n", "ID", "Nome", "Qtd", "Valor Unit.", "Subtotal");
            System.out.println("------------------------------------------------");
            for (int i = 0; i < Produto.getCarrinho().size(); i++) {
                Produto p = Produto.getCarrinho().get(i);
                double subtotal = p.getValor() * p.getQuantidade();
                System.out.printf("%-4d %-15s %-10d R$%-9.2f R$%-9.2f%n",
                    i + 1, p.getNome(), p.getQuantidade(), p.getValor(), subtotal);
                total += subtotal;
            }
            System.out.printf("\nTOTAL A PAGAR: R$ %.2f%n", total);
        }
    }
    // se teu carrinho ta vazio vai aparecer uma mensagem dizendo que nao há oque pagar
    private static void menuPagamento(Scanner ler) {
        Gerente.limparTerminal();
        if (Produto.getCarrinho().isEmpty()) {
            System.out.println("Carrinho vazio. Não há o que pagar.");
            pausar(ler);
            return;
        }

        mostrarCarrinho();

        // Calcula o total da compra antes de pedir a forma de pagamento
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

        switch (opcao) {
            case 1:
                System.out.println("Pagamento via Pix selecionado.");
                break;
            case 2:
                System.out.println("Pagamento via Cartão selecionado.");
                break;
            case 3:
                System.out.println("Pagamento em Dinheiro selecionado.");
                break;
            default:
                System.out.println("Opção inválida.");
        }

        // Registrar a venda no sistema de lucro total
        Produto.adicionarVenda(totalDaCompra); 

        System.out.println("Obrigado pela compra!");
        Produto.getCarrinho().clear(); // Limpa o carrinho após o pagamento
        pausar(ler);
    }

    private static void editarCarrinho(Scanner ler) {
        Gerente.limparTerminal();
        if (Produto.getCarrinho().isEmpty()) {
            System.out.println("Carrinho vazio. Não há itens para editar.");
            pausar(ler);
            return;
        }

        mostrarCarrinho();
       // aqui é a parte da edição dos itens que você colocou no carrinho seja para remover ou adicionar mais do mesmo produto ou menos
        System.out.println("\n=== EDITAR CARRINHO ===");
        System.out.print("Digite o ID do item que deseja editar ou remover: ");
        int indice = ler.nextInt() - 1;
        ler.nextLine();

        if (indice < 0 || indice >= Produto.getCarrinho().size()) {
            System.out.println("ID de item inválido.");
            pausar(ler);
            return;
        }

        Produto itemParaEditar = Produto.getCarrinho().get(indice);
       // aqui estão as opçoes
        System.out.println("\nItem selecionado: " + itemParaEditar.getNome() + " (Qtd atual: " + itemParaEditar.getQuantidade() + ")");
        System.out.println("1 - Alterar quantidade");
        System.out.println("2 - Remover item");
        System.out.print("Escolha: ");
        int opcaoEdicao = ler.nextInt();
        ler.nextLine();

        switch (opcaoEdicao) {
            case 1:
                System.out.print("Digite a nova quantidade (0 para remover): ");
                int novaQtd = ler.nextInt();
                ler.nextLine();

                if (novaQtd < 0) {
                    System.out.println("Quantidade inválida.");
                } else if (novaQtd == 0) {
                    // Remover o item
                    Produto.getCarrinho().remove(indice);
                    // Devolver a quantidade ao estoque
                    // Encontrar o produto original no estoque para atualizar a quantidade
                    for (Produto pEstoque : Produto.getEstoque()) {
                        if (pEstoque.getNome().equals(itemParaEditar.getNome()) &&
                            pEstoque.getMarca().equals(itemParaEditar.getMarca()) &&
                            pEstoque.getTipo().equals(itemParaEditar.getTipo())) {
                            pEstoque.setQuantidade(pEstoque.getQuantidade() + itemParaEditar.getQuantidade());
                            break;
                        }
                    }
                    System.out.println("Item removido do carrinho.");
                } else {
                    // Calcular a diferença de quantidade para ajustar o estoque
                    int diferencaQtd = novaQtd - itemParaEditar.getQuantidade();

                    // Encontrar o produto original no estoque para verificar a disponibilidade
                    Produto produtoNoEstoque = null;
                    for (Produto pEstoque : Produto.getEstoque()) {
                        if (pEstoque.getNome().equals(itemParaEditar.getNome()) &&
                            pEstoque.getMarca().equals(itemParaEditar.getMarca()) &&
                            pEstoque.getTipo().equals(itemParaEditar.getTipo())) {
                            produtoNoEstoque = pEstoque;
                            break;
                        }
                    }

                    if (produtoNoEstoque != null) {
                        if (diferencaQtd > 0 && diferencaQtd > produtoNoEstoque.getQuantidade()) {
                            System.out.println("Quantidade insuficiente no estoque para aumentar.");
                        } else {
                            itemParaEditar.setQuantidade(novaQtd);
                            produtoNoEstoque.setQuantidade(produtoNoEstoque.getQuantidade() - diferencaQtd);
                            System.out.println("Quantidade do item atualizada.");
                        }
                    } else {
                        System.out.println("Erro: Produto original não encontrado no estoque.");
                    }
                }
                break;

            case 2:
                // Remover o item
                Produto.getCarrinho().remove(indice);
                // Devolver a quantidade ao estoque
                for (Produto pEstoque : Produto.getEstoque()) {
                    if (pEstoque.getNome().equals(itemParaEditar.getNome()) &&
                        pEstoque.getMarca().equals(itemParaEditar.getMarca()) &&
                        pEstoque.getTipo().equals(itemParaEditar.getTipo())) {
                        pEstoque.setQuantidade(pEstoque.getQuantidade() + itemParaEditar.getQuantidade());
                        break;
                    }
                }
                System.out.println("Item removido do carrinho.");
                break;

            default:
                System.out.println("Opção inválida.");
        }
        pausar(ler);
    }

    private static void sairCarrinho() {
    if (Produto.getCarrinho().isEmpty()) {
        return;
    }

    for (Produto item : Produto.getCarrinho()) {
        // Devolver a quantidade ao estoque
        for (Produto pEstoque : Produto.getEstoque()) {
            if (pEstoque.getNome().equals(item.getNome()) &&
                pEstoque.getMarca().equals(item.getMarca()) &&
                pEstoque.getTipo().equals(item.getTipo())) {
                pEstoque.setQuantidade(pEstoque.getQuantidade() + item.getQuantidade());
                break;
            }
        }
    }

    Produto.getCarrinho().clear(); // Limpa o carrinho
    System.out.println("Todos os itens foram removidos do carrinho e devolvidos ao estoque.");
}
    
    private static void pausar(Scanner ler) {
        System.out.println("\nPressione Enter para continuar...");
        ler.nextLine();
    }
}
