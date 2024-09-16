package com;

import com.login.LoginWindow;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingScreen extends Application {

    @Override
    public void start(Stage primaryStage) {

        Image image = new Image(getClass().getResourceAsStream("/images/loading.jpeg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);
        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Loading Screen");
        primaryStage.show();

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            primaryStage.close();
            Platform.runLater(() -> {
                try {

                    LoginWindow loginWindow = new LoginWindow();
                    loginWindow.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        pause.play();
    }
}
