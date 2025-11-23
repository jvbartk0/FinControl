package com.fincontrol.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.fincontrol.util.Navigation;
import com.fincontrol.model.User;
import com.fincontrol.dao.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;

    private UserDAO userDAO;

    @FXML
    public void initialize() {
        userDAO = new UserDAO();
        errorLabel.setVisible(false);

        // Verifica se existe usuário, se não, cria um padrão
        createDefaultUserIfNotExists();

        loginButton.setOnAction(e -> handleLogin());

        // Enter key support
        usernameField.setOnAction(e -> handleLogin());
        passwordField.setOnAction(e -> handleLogin());
    }

    private void createDefaultUserIfNotExists() {
        User user = userDAO.loadUser();
        if (user == null) {
            // Cria usuário padrão: admin / admin
            String hashedPassword = BCrypt.hashpw("admin", BCrypt.gensalt());
            User defaultUser = new User("admin", hashedPassword);
            userDAO.saveUser(defaultUser);
        }
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Por favor, preencha todos os campos");
            return;
        }

        User user = userDAO.loadUser();
        if (user == null) {
            showError("Erro no sistema de usuários");
            return;
        }

        // Verifica usuário e senha
        if (username.equals(user.getUsername()) && BCrypt.checkpw(password, user.getPasswordHash())) {
            errorLabel.setVisible(false);
            Navigation.goToDashboard();
        } else {
            showError("Usuário ou senha incorretos");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}