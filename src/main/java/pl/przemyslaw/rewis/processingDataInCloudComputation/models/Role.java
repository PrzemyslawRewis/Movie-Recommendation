package pl.przemyslaw.rewis.processingDataInCloudComputation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RelationshipProperties
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    private List<String> roles = new ArrayList<>();

    @TargetNode
    @JsonIgnoreProperties({"actedIn", "directed"})
    private Person person;


}
