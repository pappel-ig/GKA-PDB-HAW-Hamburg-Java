package de.haw_hamburg.gka.gui.model;

import javafx.stage.Stage;

public abstract class AbstractGraphController {

    protected GraphControlModel model;
    protected Stage stage;

    public void setModel(GraphControlModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
    }

}
