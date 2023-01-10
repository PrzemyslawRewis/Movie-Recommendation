package pl.przemyslaw.rewis.processingDataInCloudComputation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;

@Node
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private int released;
    @Property("tagline")
    private String description;

    @JsonIgnoreProperties({"actedIn", "directed", "wrote", "produced"})
    @Relationship(type = "DIRECTED", direction = INCOMING)

    private List<Person> directors;
    @JsonIgnoreProperties("movie")
    @Relationship(type = "ACTED_IN", direction = INCOMING)
    private List<Role> actors;
    @JsonIgnoreProperties({"actedIn", "directed", "wrote", "produced"})
    @Relationship(type = "WROTE", direction = INCOMING)
    private List<Person> writers;
    @JsonIgnoreProperties({"actedIn", "directed", "wrote", "produced"})
    @Relationship(type = "PRODUCED", direction = INCOMING)
    private List<Person> producers;

    @Override
    public String toString() {
        return title;
    }
}
