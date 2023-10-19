package de.haw_hamburg.gka;

import de.haw_hamburg.gka.aufg1.storage.GrphLine;
import de.haw_hamburg.gka.aufg1.storage.GrphStructure;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GrphBuilder {

    private final String name;
    private final boolean directed;
    private final List<GrphLineBuilder> builders = new ArrayList<>();

    public static GrphBuilder builder(String name, boolean directed) {
        return new GrphBuilder(name, directed);
    }

    public GrphLineBuilder node1(String node1) {
        return new GrphLineBuilder(this, node1);
    }

    private void addNewLine(GrphLineBuilder lineBuilder) {
        builders.add(lineBuilder);
    }

    public GrphStructure grph() {
        return new GrphStructure(name, directed, builders.stream().map(GrphLineBuilder::toLine).toList());
    }

    @RequiredArgsConstructor
    public static class GrphLineBuilder {
        private final GrphBuilder grphBuilder;
        private final String node1;
        protected String attr1;
        protected String node2;
        protected String attr2;
        protected String edge;
        protected int weight = 1;

        public GrphLineBuilder attr1(String attr1) {
            this.attr1 = attr1;
            return this;
        }

        public GrphLineBuilder node2(String node2) {
            this.node2 = node2;
            return this;
        }

        public GrphLineBuilder attr2(String attr2) {
            this.attr2 = attr2;
            return this;
        }

        public GrphLineBuilder edge(String edge) {
            this.edge = edge;
            return this;
        }

        public GrphLineBuilder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public GrphBuilder next() {
            grphBuilder.addNewLine(this);
            return grphBuilder;
        }

        private GrphLine toLine() {
            return new GrphLine(node1, attr1, node2, attr2, edge, weight);
        }
    }
}
