package de.haw_hamburg.gka.gui.kruskal;

import de.haw_hamburg.gka.gui.AbstractController;
import de.haw_hamburg.gka.gui.UIModal;
import de.haw_hamburg.gka.gui.common.GraphFileChooser;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Objects;

public class KruskalControlsController extends AbstractController<KruskalModel> {

    final GraphFileChooser fileChooser = new GraphFileChooser();

    public void openFile() {
        model.getFileToLoad().setValue(fileChooser.selectFile(stage));
    }
}
