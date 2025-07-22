package projeto;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            Gerente.limparTerminal();
            System.out.println("=== Sistema da Loja ===");// basicamente você escolhe se você é cliente ou gerente, ou que quer encerrar o codigo.
            System.out.println("Você é:");
            System.out.println("1 - Gerente");
            System.out.println("2 - Cliente");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            int tipo = ler.nextInt();
            ler.nextLine(); // Limpar buffer

            switch (tipo) {
                case 1:
                    // Login simplificado para gerente (qualquer dado é aceito, eu testei...)
                    System.out.print("Digite o nome do funcionario: ");
                    ler.nextLine();
                    System.out.print("Digite a senha: ");
                    ler.nextLine();
                    Gerente.mostrarMenu(ler);
                    break;

                case 2:
                    Cliente.menuCliente(ler);
                    break;

                case 0:
                    executando = false;
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    pausar(ler);
            }
        }

        ler.close();
    }

    private static void pausar(Scanner ler) {
        System.out.println("\nPressione Enter para continuar...");
        ler.nextLine();
    }
}