package pl.przemyslaw.rewis.processingDataInCloudComputation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Getter
@Setter
@Node
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    @Property("born")
    private Integer birthYear;

    @JsonIgnoreProperties("person")
    @Relationship(type = "ACTED_IN")
    private List<Movie> actedIn;

    @JsonIgnoreProperties({"actors", "directors", "producers", "writers"})
    @Relationship(type = "DIRECTED")
    private List<Movie> directed;

    @JsonIgnoreProperties({"actors", "directors", "producers", "writers"})
    @Relationship(type = "WROTE")
    private List<Movie> wrote;

    @JsonIgnoreProperties({"actors", "directors", "producers", "writers"})
    @Relationship(type = "PRODUCED")
    private List<Movie> produced;

}
