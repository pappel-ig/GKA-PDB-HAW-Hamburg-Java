package de.haw_hamburg.gka.gui.kruskal;

import de.haw_hamburg.gka.gui.AbstractController;
import de.haw_hamburg.gka.gui.common.GraphFileChooser;
import de.haw_hamburg.gka.gui.common.GraphLoader;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

import java.io.File;

public class KruskalVisualizationController extends AbstractController<KruskalModel> {

    @Override
    public void setModel(KruskalModel model, Stage stage, GraphLoader graphLoader, GraphFileChooser graphFileChooser) {
        model.getFileToLoad().addListener(this::newFileChosen);
    }

    private void newFileChosen(ObservableValue<? extends File> File, File old, File now) {

    }
}
