package com.fincontrol.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.fincontrol.model.Category;
import com.fincontrol.dao.CategoryDAO;
import com.fincontrol.util.Navigation;
import java.util.List;

public class CategoryController {

    @FXML private ListView<Category> categoriesList;
    @FXML private TextField nameField;
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button backButton;

    private CategoryDAO categoryDAO;
    private ObservableList<Category> categories;
    private Category selectedCategory;

    @FXML
    public void initialize() {
        categoryDAO = new CategoryDAO();
        loadCategories();
        setupEventHandlers();
        clearSelection();
    }

    private void loadCategories() {
        List<Category> categoryList = categoryDAO.loadCategories();
        categories = FXCollections.observableArrayList(categoryList);
        categoriesList.setItems(categories);
    }

    private void setupEventHandlers() {
        categoriesList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectCategory(newSelection);
                    }
                });

        addButton.setOnAction(e -> handleAddCategory());
        updateButton.setOnAction(e -> handleUpdateCategory());
        deleteButton.setOnAction(e -> handleDeleteCategory());
        clearButton.setOnAction(e -> clearSelection());
        backButton.setOnAction(e -> Navigation.goToDashboard());
    }

    private void handleAddCategory() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            showAlert("Nome da categoria é obrigatório");
            return;
        }

        // Verificar se já existe
        if (categories.stream().anyMatch(c -> c.getName().equalsIgnoreCase(name))) {
            showAlert("Já existe uma categoria com este nome");
            return;
        }

        Category category = new Category(name);
        categories.add(category);
        categoryDAO.saveCategories(categories);
        clearSelection();
    }

    private void handleUpdateCategory() {
        if (selectedCategory == null) return;

        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            showAlert("Nome da categoria é obrigatório");
            return;
        }

        // Verificar se já existe (excluindo a atual)
        if (categories.stream()
                .filter(c -> !c.equals(selectedCategory))
                .anyMatch(c -> c.getName().equalsIgnoreCase(name))) {
            showAlert("Já existe uma categoria com este nome");
            return;
        }

        selectedCategory.setName(name);
        categoriesList.refresh();
        categoryDAO.saveCategories(categories);
        clearSelection();
    }

    private void handleDeleteCategory() {
        if (selectedCategory == null) return;

        // Verificar se a categoria está sendo usada em transações
        // (implementação simplificada - em produção, verificar referências)

        categories.remove(selectedCategory);
        categoryDAO.saveCategories(categories);
        clearSelection();
    }

    private void selectCategory(Category category) {
        selectedCategory = category;
        nameField.setText(category.getName());

        addButton.setDisable(true);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }

    private void clearSelection() {
        selectedCategory = null;
        nameField.clear();
        categoriesList.getSelectionModel().clearSelection();

        addButton.setDisable(false);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validação");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}