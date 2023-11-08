package de.haw_hamburg.gka.gui;

import javafx.scene.control.Alert;

public class ExceptionHelper {

    public static void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(message);

        alert.showAndWait();
    }

    public static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(message);

        alert.showAndWait();
    }

}
