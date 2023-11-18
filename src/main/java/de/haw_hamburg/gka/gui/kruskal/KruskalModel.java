package de.haw_hamburg.gka.gui.kruskal;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KruskalModel {

    private ObjectProperty<File> fileToLoad = new SimpleObjectProperty<>();

}
