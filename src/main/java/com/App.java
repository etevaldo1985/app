package com;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Inicia a tela de carregamento
        LoadingScreen loadingScreen = new LoadingScreen();
        loadingScreen.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
