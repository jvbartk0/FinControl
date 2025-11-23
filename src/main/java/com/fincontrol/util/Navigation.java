package com.fincontrol.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Navigation {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void goToLogin() {
        loadScene("/com/fincontrol/login.fxml", "Login - FinControl", 400, 500);
    }

    public static void goToDashboard() {
        loadScene("/com/fincontrol/dashboard.fxml", "Dashboard - FinControl", 1000, 700);
    }

    public static void goToTransactions() {
        loadScene("/com/fincontrol/transaction.fxml", "Transações - FinControl", 900, 600);
    }

    public static void goToCategories() {
        loadScene("/com/fincontrol/categories.fxml", "Categorias - FinControl", 600, 500);
    }

    private static void loadScene(String fxmlPath, String title, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(Navigation.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);
            scene.getStylesheets().add(Navigation.class.getResource("/com/fincontrol/style.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            System.err.println("Erro ao carregar tela: " + e.getMessage());
            e.printStackTrace();
        }
    }
}