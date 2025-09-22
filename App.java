package com.mercatopokemon;

import com.mercatopokemon.Gestionale_DB.CardsController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {
    private CardsController controller;
    private static final String FXML_PATH = "/com/mercatopokemon/Gestionale_DB/Interfaccia/cards_layout.fxml";

    @Override
    public void start(Stage primaryStage) {
        try {
            URL fxmlUrl = getClass().getResource(FXML_PATH);
            if (fxmlUrl == null) {
                throw new RuntimeException("FXML non trovato: " + FXML_PATH);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            controller = loader.getController();

            Scene scene = new Scene(root, 900, 750);

            primaryStage.setTitle("Mercato Pokémon - Gestione Carte");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(400);

            primaryStage.setOnCloseRequest(evt -> {
                if (controller != null) controller.closeConnection();
                Platform.exit();
            });

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    @Override
    public void stop() throws Exception {
        if (controller != null) {
            controller.closeConnection();
            super.stop();
        }
    }

}
