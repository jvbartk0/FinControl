package com.fincontrol.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.fincontrol.model.Transaction;
import com.fincontrol.model.TransactionType;
import com.fincontrol.model.Category;
import com.fincontrol.dao.TransactionDAO;
import com.fincontrol.dao.CategoryDAO;
import com.fincontrol.util.Navigation;
import java.time.LocalDate;
import java.util.List;

public class TransactionController {

    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, String> dateColumn;
    @FXML private TableColumn<Transaction, String> descriptionColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, String> categoryColumn;
    @FXML private TableColumn<Transaction, String> amountColumn;

    @FXML private DatePicker dateField;
    @FXML private TextField descriptionField;
    @FXML private ComboBox<TransactionType> typeComboBox;
    @FXML private ComboBox<Category> categoryComboBox;
    @FXML private TextField amountField;
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button backButton;

    private TransactionDAO transactionDAO;
    private CategoryDAO categoryDAO;
    private ObservableList<Transaction> transactions;
    private ObservableList<Category> categories;
    private Transaction selectedTransaction;

    @FXML
    public void initialize() {
        transactionDAO = new TransactionDAO();
        categoryDAO = new CategoryDAO();

        loadData();
        setupTable();
        setupForm();
        setupEventHandlers();
        clearSelection();
    }

    private void loadData() {
        List<Transaction> transactionList = transactionDAO.loadTransactions();
        transactions = FXCollections.observableArrayList(transactionList);

        List<Category> categoryList = categoryDAO.loadCategories();
        categories = FXCollections.observableArrayList(categoryList);

        transactionsTable.setItems(transactions);
        categoryComboBox.setItems(categories);
    }

    private void setupTable() {
        dateColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getDate().toString()));

        descriptionColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getDescription()));

        typeColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getType().getDescricao()));

        categoryColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getCategory().getName()));

        amountColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        String.format("R$ %.2f", cellData.getValue().getAmount())));
    }

    private void setupForm() {
        typeComboBox.setItems(FXCollections.observableArrayList(TransactionType.values()));
        dateField.setValue(LocalDate.now());
    }

    private void setupEventHandlers() {
        transactionsTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectTransaction(newSelection);
                    }
                });

        addButton.setOnAction(e -> handleAddTransaction());
        updateButton.setOnAction(e -> handleUpdateTransaction());
        deleteButton.setOnAction(e -> handleDeleteTransaction());
        clearButton.setOnAction(e -> clearSelection());
        backButton.setOnAction(e -> Navigation.goToDashboard());
    }

    private void handleAddTransaction() {
        if (!validateForm()) return;

        Transaction transaction = new Transaction(
                dateField.getValue(),
                descriptionField.getText(),
                typeComboBox.getValue(),
                categoryComboBox.getValue(),
                Double.parseDouble(amountField.getText())
        );

        transactions.add(transaction);
        transactionDAO.saveTransactions(transactions);
        clearSelection();
    }

    private void handleUpdateTransaction() {
        if (selectedTransaction == null || !validateForm()) return;

        selectedTransaction.setDate(dateField.getValue());
        selectedTransaction.setDescription(descriptionField.getText());
        selectedTransaction.setType(typeComboBox.getValue());
        selectedTransaction.setCategory(categoryComboBox.getValue());
        selectedTransaction.setAmount(Double.parseDouble(amountField.getText()));

        transactionsTable.refresh();
        transactionDAO.saveTransactions(transactions);
        clearSelection();
    }

    private void handleDeleteTransaction() {
        if (selectedTransaction == null) return;

        transactions.remove(selectedTransaction);
        transactionDAO.saveTransactions(transactions);
        clearSelection();
    }

    private void selectTransaction(Transaction transaction) {
        selectedTransaction = transaction;
        dateField.setValue(transaction.getDate());
        descriptionField.setText(transaction.getDescription());
        typeComboBox.setValue(transaction.getType());
        categoryComboBox.setValue(transaction.getCategory());
        amountField.setText(String.format("%.2f", transaction.getAmount()));

        addButton.setDisable(true);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }

    private void clearSelection() {
        selectedTransaction = null;
        dateField.setValue(LocalDate.now());
        descriptionField.clear();
        typeComboBox.setValue(null);
        categoryComboBox.setValue(null);
        amountField.clear();

        transactionsTable.getSelectionModel().clearSelection();
        addButton.setDisable(false);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private boolean validateForm() {
        if (dateField.getValue() == null) {
            showAlert("Data é obrigatória");
            return false;
        }
        if (descriptionField.getText().trim().isEmpty()) {
            showAlert("Descrição é obrigatória");
            return false;
        }
        if (typeComboBox.getValue() == null) {
            showAlert("Tipo é obrigatório");
            return false;
        }
        if (categoryComboBox.getValue() == null) {
            showAlert("Categoria é obrigatória");
            return false;
        }
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                showAlert("Valor deve ser maior que zero");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Valor deve ser um número válido");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validação");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}