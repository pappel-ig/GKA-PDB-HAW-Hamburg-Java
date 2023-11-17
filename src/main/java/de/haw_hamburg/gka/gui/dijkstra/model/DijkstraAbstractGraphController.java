package de.haw_hamburg.gka.gui.dijkstra.model;

import javafx.stage.Stage;

public abstract class DijkstraAbstractGraphController {

    protected DijkstraGraphControlModel model;
    protected Stage stage;

    public void setModel(DijkstraGraphControlModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
    }

}
