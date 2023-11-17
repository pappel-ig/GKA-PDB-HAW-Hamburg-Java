package de.haw_hamburg.gka.gui.dijkstra.model;

import javafx.stage.Stage;

public abstract class DijkstraAbstractController {

    protected DijkstraModel model;
    protected Stage stage;

    public void setModel(DijkstraModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
    }

    public void reset() {

    }

}
