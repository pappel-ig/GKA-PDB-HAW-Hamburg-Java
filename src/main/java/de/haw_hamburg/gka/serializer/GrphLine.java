package de.haw_hamburg.gka.serializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrphLine {
    private String node1;
    private String attr1;
    private String node2;
    private String attr2;
    private String edge;
    private int weight;
}