// Pacote do projeto, que organiza as classes.
package projeto;

// Importa a classe Scanner, que é utilizada para ler a entrada do usuário (teclado).
import java.util.Scanner;

// Declaração da classe principal 'Main', onde o programa começa.
public class Main {

    // Método 'main', o ponto de entrada de qualquer aplicação Java.
    public static void main(String[] args) {
        // Cria uma instância de Scanner para ler as entradas do console.
        Scanner ler = new Scanner(System.in);
        // Variável booleana para controlar o loop principal do programa. Enquanto for 'true', o sistema continua executando.
        boolean executando = true;

        // Loop 'while' que mantém o menu principal ativo até que o usuário decida sair.
        while (executando) {
            // Chama um método para limpar a tela do terminal, proporcionando uma interface mais limpa.
            Gerente.limparTerminal();
            // Imprime o cabeçalho do menu principal.
            System.out.println("=== Sistema da Loja ===");
            // Apresenta as opções de perfil para o usuário.
            System.out.println("Você é:");
            System.out.println("1 - Gerente");
            System.out.println("2 - Cliente");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            // Lê o número inteiro que o usuário digitar como opção.
            int tipo = ler.nextInt();
            // Limpa o buffer do scanner. Após ler um número, o caractere de nova linha (Enter) permanece no buffer,
            // e esta linha o consome para evitar problemas na próxima leitura de texto (ler.nextLine()).
            ler.nextLine();

            // A estrutura 'switch' executa um bloco de código diferente dependendo da escolha do usuário ('tipo').
            switch (tipo) {
                case 1: // Caso o usuário escolha "Gerente".
                    // Simula um login de gerente. Neste projeto, qualquer entrada é aceita para simplificar.
                    System.out.print("Digite o nome do funcionario: ");
                    ler.nextLine(); // Apenas lê e descarta o nome.
                    System.out.print("Digite a senha: ");
                    ler.nextLine(); // Apenas lê e descarta a senha.
                    // Chama o método que exibe o menu de funcionalidades do gerente.
                    Gerente.mostrarMenu(ler);
                    break; // Finaliza o case 1.

                case 2: // Caso o usuário escolha "Cliente".
                    // Chama o método que exibe o menu de funcionalidades do cliente.
                    Cliente.menuCliente(ler);
                    break; // Finaliza o case 2.

                case 0: // Caso o usuário escolha "Sair".
                    // Define 'executando' como 'false', o que fará com que o loop 'while' termine na próxima verificação.
                    executando = false;
                    System.out.println("Encerrando o sistema...");
                    break; // Finaliza o case 0.

                default: // Caso o usuário digite uma opção que não seja 1, 2 ou 0.
                    System.out.println("Opção inválida.");
                    // Chama o método 'pausar' para que o usuário possa ler a mensagem antes do loop recomeçar.
                    pausar(ler);
            }
        }

        // Fecha o objeto Scanner para liberar os recursos do sistema. É uma boa prática fazer isso ao final.
        ler.close();
    }

    // Método auxiliar privado para pausar a execução até que o usuário pressione Enter.
    private static void pausar(Scanner ler) {
        System.out.println("\nPressione Enter para continuar...");
        // Espera o usuário digitar a tecla Enter para prosseguir.
        ler.nextLine();
    }
}
