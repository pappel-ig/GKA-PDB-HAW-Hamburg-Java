package de.haw_hamburg.gka.gui;

import de.haw_hamburg.gka.gui.common.GraphFileChooser;
import de.haw_hamburg.gka.gui.common.GraphLoader;
import javafx.stage.Stage;

public class AbstractController<T> {

    protected T model;
    protected Stage stage;
    protected GraphLoader graphLoader;
    protected GraphFileChooser graphFileChooser;

    public void setModel(T model, Stage stage, GraphLoader graphLoader, GraphFileChooser graphFileChooser) {
        this.model = model;
        this.stage = stage;
        this.graphLoader = graphLoader;
        this.graphFileChooser = graphFileChooser;
    }

    public void preReset() {
        reset();
        graphLoader.reset();
    }

    public void reset() {

    }
}
