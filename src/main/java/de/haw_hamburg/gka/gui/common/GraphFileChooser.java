package de.haw_hamburg.gka.gui.common;

import de.haw_hamburg.gka.gui.UIModal;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class GraphFileChooser {

    public File selectFile(Stage stage) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Graph auswählen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Graph (.grph)", "*.grph"));
        final File chosen = fileChooser.showOpenDialog(stage);
        if (Objects.nonNull(chosen)) {
            return chosen;
        } else {
            UIModal.showInfoDialog("Es wurde keine Datei selektiert -> keine Datei geladen");
            return null;
        }
    }

}
