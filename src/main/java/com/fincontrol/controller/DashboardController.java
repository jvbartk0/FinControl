package com.fincontrol.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.concurrent.Task;
import com.fincontrol.model.Transaction;
import com.fincontrol.model.TransactionType;
import com.fincontrol.dao.TransactionDAO;
import com.fincontrol.util.Navigation;
import com.fincontrol.util.CurrencyService;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML private Label totalBalanceLabel;
    @FXML private Label totalIncomeLabel;
    @FXML private Label totalExpenseLabel;
    @FXML private Label exchangeRateLabel;
    @FXML private PieChart categoryPieChart;
    @FXML private BarChart<String, Number> monthlyBarChart;
    @FXML private LineChart<String, Number> balanceLineChart;
    @FXML private Button transactionsButton;
    @FXML private Button categoriesButton;
    @FXML private Button logoutButton;

    private TransactionDAO transactionDAO;
    private CurrencyService currencyService;
    private List<Transaction> transactions;

    @FXML
    public void initialize() {
        transactionDAO = new TransactionDAO();
        currencyService = new CurrencyService();
        loadTransactions();
        updateDashboard();
        setupEventHandlers();
        loadExchangeRate();
    }

    private void loadTransactions() {
        transactions = transactionDAO.loadTransactions();
    }

    private void updateDashboard() {
        updateBalanceInfo();
        updateCategoryChart();
        updateMonthlyChart();
        updateBalanceLineChart();
    }

    private void updateBalanceInfo() {
        double totalIncome = transactions.stream()
                .filter(t -> t.getType() == TransactionType.RECEITA)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.DESPESA)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double balance = totalIncome - totalExpense;

        totalIncomeLabel.setText(String.format("R$ %.2f", totalIncome));
        totalExpenseLabel.setText(String.format("R$ %.2f", totalExpense));
        totalBalanceLabel.setText(String.format("R$ %.2f", balance));

        // Color coding for balance
        if (balance >= 0) {
            totalBalanceLabel.getStyleClass().remove("negative-balance");
            totalBalanceLabel.getStyleClass().add("positive-balance");
        } else {
            totalBalanceLabel.getStyleClass().remove("positive-balance");
            totalBalanceLabel.getStyleClass().add("negative-balance");
        }
    }

    private void updateCategoryChart() {
        categoryPieChart.getData().clear();

        Map<String, Double> categoryExpenses = transactions.stream()
                .filter(t -> t.getType() == TransactionType.DESPESA)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        categoryExpenses.forEach((category, amount) -> {
            PieChart.Data slice = new PieChart.Data(category + " - R$ " +
                    String.format("%.2f", amount), amount);
            categoryPieChart.getData().add(slice);
        });
    }

    private void updateMonthlyChart() {
        monthlyBarChart.getData().clear();

        // Dados para despesas
        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Despesas");

        // Dados para receitas
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Receitas");

        // Agrupar por mês
        Map<Month, Double> monthlyExpenses = transactions.stream()
                .filter(t -> t.getType() == TransactionType.DESPESA)
                .collect(Collectors.groupingBy(
                        t -> t.getDate().getMonth(),
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        Map<Month, Double> monthlyIncome = transactions.stream()
                .filter(t -> t.getType() == TransactionType.RECEITA)
                .collect(Collectors.groupingBy(
                        t -> t.getDate().getMonth(),
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        // Preencher os dados para todos os meses
        for (Month month : Month.values()) {
            String monthName = month.getDisplayName(TextStyle.SHORT, Locale.getDefault());
            double expense = monthlyExpenses.getOrDefault(month, 0.0);
            double income = monthlyIncome.getOrDefault(month, 0.0);

            expenseSeries.getData().add(new XYChart.Data<>(monthName, expense));
            incomeSeries.getData().add(new XYChart.Data<>(monthName, income));
        }

        monthlyBarChart.getData().addAll(expenseSeries, incomeSeries);
    }

    private void updateBalanceLineChart() {
        balanceLineChart.getData().clear();

        XYChart.Series<String, Number> balanceSeries = new XYChart.Series<>();
        balanceSeries.setName("Saldo");

        // Agrupar transações por mês e calcular saldo mensal
        Map<Month, Double> monthlyIncome = transactions.stream()
                .filter(t -> t.getType() == TransactionType.RECEITA)
                .collect(Collectors.groupingBy(
                        t -> t.getDate().getMonth(),
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        Map<Month, Double> monthlyExpense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.DESPESA)
                .collect(Collectors.groupingBy(
                        t -> t.getDate().getMonth(),
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        // Calcular saldo acumulado mês a mês
        double accumulatedBalance = 0;
        for (Month month : Month.values()) {
            double income = monthlyIncome.getOrDefault(month, 0.0);
            double expense = monthlyExpense.getOrDefault(month, 0.0);
            accumulatedBalance += (income - expense);

            String monthName = month.getDisplayName(TextStyle.SHORT, Locale.getDefault());
            balanceSeries.getData().add(new XYChart.Data<>(monthName, accumulatedBalance));
        }

        balanceLineChart.getData().add(balanceSeries);
    }

    private void loadExchangeRate() {
        Task<Double> task = new Task<Double>() {
            @Override
            protected Double call() throws Exception {
                return currencyService.getUSDtoBRLRate();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                double rate = getValue();
                exchangeRateLabel.setText(String.format("USD/BRL: R$ %.2f", rate));
            }

            @Override
            protected void failed() {
                super.failed();
                exchangeRateLabel.setText("USD/BRL: Erro ao carregar");
            }
        };

        new Thread(task).start();
    }

    private void setupEventHandlers() {
        transactionsButton.setOnAction(e -> Navigation.goToTransactions());
        categoriesButton.setOnAction(e -> Navigation.goToCategories());
        logoutButton.setOnAction(e -> Navigation.goToLogin());
    }
}
