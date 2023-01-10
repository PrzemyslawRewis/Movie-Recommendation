package pl.przemyslaw.rewis.processingDataInCloudComputation.repostories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.przemyslaw.rewis.processingDataInCloudComputation.models.Movie;

import java.util.List;

public interface MovieRepository extends Neo4jRepository<Movie, Long> {

    @Query("MATCH (p:Person)-[r:ACTED_IN]->(m:Movie) WHERE p.name = $actorName RETURN m")
    List<Movie> findMoviesByActorName(String actorName);



}
