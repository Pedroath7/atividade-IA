import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class App {


    // Função para validar CPF
    public static boolean validarCPF(String cpf) {
        // Remove caracteres não numéricos do CPF
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF possui 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos do CPF são iguais
        boolean allEqual = true;
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                allEqual = false;
                break;
            }
        }
        if (allEqual) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) {
            firstDigit = 0;
        }

        // Verifica o primeiro dígito verificador
        if (cpf.charAt(9) - '0' != firstDigit) {
            return false;
        }

        // Calcula o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) {
            secondDigit = 0;
        }

        // Verifica o segundo dígito verificador
        if (cpf.charAt(10) - '0' != secondDigit) {
            return false;
        }

        // CPF válido
        return true;
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite seu nome: ");
        String nome = sc.nextLine();

        System.out.println("Digite o salário bruto: ");
        double salariob = sc.nextDouble();

        System.out.println("Digite o número de dependentes: ");
        int dependentes = sc.nextInt();
        // Número de pessoas que dependem de um indivíduo ou de uma família

        // Limpa a nova linha pendente após a leitura do número de dependentes
        sc.nextLine();

        double imposto = salariob * 0.11;
        double inss = 0.08 * salariob;
        double salariol = salariob - imposto - inss;

        System.out.println("Digite seu CPF: ");
        String cpf = sc.nextLine();

        // Verifica se o CPF é válido
        if (validarCPF(cpf)) {
            System.out.println("CPF válido.");
        } else {
            System.out.println("CPF inválido.");
            // Encerra o programa se o CPF for inválido
            return;
        }

        System.out.println("Digite o seu CEP: ");
        String cep = sc.nextLine();

        System.out.println("Olá, " + nome + "! Você tem um total de " + dependentes + " dependente(s) em seu nome.");
        System.out.println("Do seu salário de R$" + salariob + ", houve um desconto de R$" + inss + " referente ao INSS e um desconto de R$" + imposto + " referente ao IRRF, resultando em um salário líquido de R$" + salariol + ".");

        try {
            @SuppressWarnings("deprecation")
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            StringBuilder response;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            System.out.println("Dados do CEP: " + response.toString());
        } catch (IOException e) {
        }

        sc.close();
    }
}