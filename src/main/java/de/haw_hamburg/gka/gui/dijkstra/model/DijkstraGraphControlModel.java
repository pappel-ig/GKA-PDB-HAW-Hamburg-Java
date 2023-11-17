package de.haw_hamburg.gka.gui.dijkstra.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.graphstream.graph.Node;

import java.io.File;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DijkstraGraphControlModel {

    private ObjectProperty<File> file = new SimpleObjectProperty<>();
    private ObjectProperty<File> saveTo = new SimpleObjectProperty<>();
    private ObservableList<Node> nodes = FXCollections.observableArrayList();
    private ObjectProperty<Node> source = new SimpleObjectProperty<>();
    private ObjectProperty<Node> target = new SimpleObjectProperty<>();
    private IntegerProperty length = new SimpleIntegerProperty();

    public void reset() {
        file.set(null);
        saveTo.set(null);
        source.set(null);
        target.set(null);
        length.set(0);
        nodes.clear();
    }

}
