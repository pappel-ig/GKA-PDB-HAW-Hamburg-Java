package de.haw_hamburg.gka.gui;

import javafx.stage.Stage;

public class AbstractController<T> {

    protected T model;
    protected Stage stage;

    public void setModel(T model, Stage stage) {
        this.model = model;
        this.stage = stage;
    }

    public void reset() {

    }
}
