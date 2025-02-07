package org.example;

import org.example.model.Funcionario;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        // 1
        List<Funcionario> funcionarios = new ArrayList<>();
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

        // 2
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getNome().equals("João")) {
                funcionarios.remove(i);
                break;
            }
        }

        // 3
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

        for (Funcionario funcionario : funcionarios) {
            String dataFormatada = funcionario.getDataNascimento().format(dateFormatter);
            String salarioFormatado = decimalFormat.format(funcionario.getSalario());

            System.out.println("Nome: " + funcionario.getNome() +
                    ", Data de Nascimento: " + dataFormatada +
                    ", Salário: " + salarioFormatado +
                    ", Função: " + funcionario.getFuncao());
        }

        // 4
        for (Funcionario funcionario : funcionarios) {
            BigDecimal salarioAtual = funcionario.getSalario();
            BigDecimal aumento = salarioAtual.multiply(new BigDecimal("0.10"));
            BigDecimal novoSalario = salarioAtual.add(aumento);
            funcionario.setSalario(novoSalario);
        }

        // 5
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        for (Funcionario funcionario : funcionarios) {
            String funcao = funcionario.getFuncao();
            if (!funcionariosPorFuncao.containsKey(funcao)) {
                funcionariosPorFuncao.put(funcao, new ArrayList<>());
            }
            funcionariosPorFuncao.get(funcao).add(funcionario);
        }

        // 6
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (Funcionario funcionario : entry.getValue()) {
                System.out.println("  - " + funcionario.getNome());
            }
        }

        // 8
        System.out.println("\nFuncionários que fazem aniversário no mês 10 e 12:");
        for (Funcionario funcionario : funcionarios) {
            int mes = funcionario.getDataNascimento().getMonthValue();
            if (mes == 10 || mes == 12) {
                System.out.println("Nome: " + funcionario.getNome() +
                        ", Data de Nascimento: " + funcionario.getDataNascimento().format(dateFormatter));
            }
        }

        // 9
        Funcionario maisVelho = funcionarios.get(0);
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getDataNascimento().isBefore(maisVelho.getDataNascimento())) {
                maisVelho = funcionario;
            }
        }
        int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.println("\nFuncionário com maior idade:");
        System.out.println("Nome: " + maisVelho.getNome());
        System.out.println("Idade: " + idade + " anos");

        // 10
        for (int i = 0; i < funcionarios.size() - 1; i++) {
            for (int j = i + 1; j < funcionarios.size(); j++) {
                if (funcionarios.get(i).getNome().compareTo(funcionarios.get(j).getNome()) > 0) {
                    Funcionario temp = funcionarios.get(i);
                    funcionarios.set(i, funcionarios.get(j));
                    funcionarios.set(j, temp);
                }
            }
        }
        System.out.println("\nFuncionários por ordem alfabética:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.getNome() +
                    ", Data de Nascimento: " + funcionario.getDataNascimento().format(dateFormatter) +
                    ", Salário: " + decimalFormat.format(funcionario.getSalario()) +
                    ", Função: " + funcionario.getFuncao());
        }

        // 11
        BigDecimal totalSalarios = BigDecimal.ZERO;
        for (Funcionario funcionario : funcionarios) {
            totalSalarios = totalSalarios.add(funcionario.getSalario());
        }
        System.out.printf("\nTotal dos salários: %s\n", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalSalarios));

        // 12
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nSalários em múltiplos de salário mínimo:");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal multiplos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.printf("Nome: %s, Salários mínimos: %.2f\n", funcionario.getNome(), multiplos);
        }
    }
}