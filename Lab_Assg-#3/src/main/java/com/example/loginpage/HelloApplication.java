package com.example.loginpage;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        File file = new File("data.txt");


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button exitButton = new Button("Exit");
        Label messageLabel = new Label();

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 0, 2);
        grid.add(exitButton, 1, 2);
        grid.add(messageLabel, 0, 3, 2, 1);


        loginButton.setOnAction(e -> {
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    boolean userFound = false;
                    while ((line = reader.readLine()) != null) {
                        String[] credentials = line.split(" ");
                        if (credentials.length == 2 &&
                                credentials[0].equals(usernameField.getText()) &&
                                credentials[1].equals(passwordField.getText())) {
                            userFound = true;
                            openNewStage("Welcome, " + usernameField.getText());
                            messageLabel.setText("");
                            break;
                        }
                    }
                    if (!userFound) {
                        messageLabel.setText("User not found");
                        messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                messageLabel.setText("Data file not found");
                messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            }
        });


        exitButton.setOnAction(e -> System.exit(0));


        stage.setScene(new Scene(grid, 400, 300));
        stage.setTitle("Login Application");
        stage.show();
    }

    private void openNewStage(String message) {
        Stage newStage = new Stage();
        Label label = new Label(message);
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.add(label, 0, 0);
        newStage.setScene(new Scene(layout, 300, 100));
        newStage.setTitle("Welcome");
        newStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
