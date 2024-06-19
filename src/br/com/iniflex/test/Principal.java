package br.com.iniflex.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        // ======= Ação 3.1: Inserir todos os funcionários ========
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // ======= Ação 3.2: Remover o funcionário “João” ========
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // ======= Ação 3.3: Imprimir todos os funcionários ========
        System.out.println("===== Todos os Funcionários =====");
        for (Funcionario funcionario : funcionarios) {
            System.out.println(formatFuncionario(funcionario));
        }

        // ======= Ação 3.4: Aumentar salário em 10% ========
        funcionarios.forEach(funcionario -> {
            BigDecimal novoSalario = funcionario.getSalario().multiply(new BigDecimal("1.10"));
            funcionario.setSalario(novoSalario);
        });

        // ======= Ação 3.5: Agrupar por função ========
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // ======= Ação 3.6: Imprimir funcionários agrupados por função ========
        System.out.println("\n===== Funcionários Agrupados por Função =====");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(funcionario -> System.out.println(formatFuncionario(funcionario)));
        });

        // ======= Ação 3.8: Imprimir aniversariantes dos meses 10 e 12 ========
        System.out.println("\n===== Funcionários que fazem aniversário nos meses 10 e 12 =====");
        funcionarios.stream()
                .filter(funcionario -> {
                    int mes = funcionario.getDataNascimento().getMonthValue();
                    return mes == 10 || mes == 12;
                })
                .forEach(funcionario -> System.out.println(formatFuncionario(funcionario)));

        // ======= Ação 3.9: Imprimir o funcionário com a maior idade ========
        Funcionario funcionarioMaisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);
        if (funcionarioMaisVelho != null) {
            int idade = LocalDate.now().getYear() - funcionarioMaisVelho.getDataNascimento().getYear();
            System.out.println("\n===== Funcionário com a Maior Idade =====");
            System.out.println("Nome: " + funcionarioMaisVelho.getNome() + ", Idade: " + idade);
        }

        // ======= Ação 3.10: Imprimir funcionários por ordem alfabética ========
        System.out.println("\n===== Funcionários por Ordem Alfabética =====");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(funcionario -> System.out.println(formatFuncionario(funcionario)));

        // ======= Ação 3.11: Imprimir o total dos salários ========
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\n===== Total dos Salários =====");
        System.out.printf(Locale.GERMANY, "R$ %,.2f%n", totalSalarios);

        // ======= Ação 3.12: Imprimir quantos salários mínimos cada funcionário ganha ========
        System.out.println("\n===== Quantidade de Salários Mínimos por Funcionário =====");
        funcionarios.forEach(funcionario -> {
            BigDecimal quantidadeSalariosMinimos = funcionario.getSalario().divide(SALARIO_MINIMO, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println("Nome: " + funcionario.getNome() + ", Salários Mínimos: " + quantidadeSalariosMinimos);
        });
    }

    private static String formatFuncionario(Funcionario funcionario) {
        return String.format("Nome: %s, Data de Nascimento: %s, Salário: R$ %,.2f, Função: %s",
                funcionario.getNome(),
                funcionario.getDataNascimento().format(DATE_FORMATTER),
                funcionario.getSalario(),
                funcionario.getFuncao());
    }
}
