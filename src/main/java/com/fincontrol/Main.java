package com.fincontrol;

import javafx.application.Application;
import javafx.stage.Stage;
import com.fincontrol.util.Navigation;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Navigation.setPrimaryStage(primaryStage);
            Navigation.goToLogin();
            primaryStage.setTitle("FinControl - Controle Financeiro Pessoal");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}