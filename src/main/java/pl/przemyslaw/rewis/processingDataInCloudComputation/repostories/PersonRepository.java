package pl.przemyslaw.rewis.processingDataInCloudComputation.repostories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.przemyslaw.rewis.processingDataInCloudComputation.models.Person;

import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    @Query("MATCH (am:Movie)<-[ai:ACTED_IN]-(p:Person) return p, collect(ai), collect(am)")
    List<Person> getActors();

    @Query("MATCH (am:Movie)<-[ai:DIRECTED]-(p:Person) return p, collect(ai), collect(am)")
    List<Person> getDirectors();

    @Query("MATCH (am:Movie)<-[ai:PRODUCED]-(p:Person) return p, collect(ai), collect(am)")
    List<Person> getProducers();

    @Query("MATCH (am:Movie)<-[ai:WROTE]-(p:Person) return p, collect(ai), collect(am)")
    List<Person> getWriters();

    @Query("MATCH (m:Movie) WHERE m.title = $movieTitle MATCH (p:Person) WHERE p.name = $personName MERGE (p)-[:ACTED_IN]->(m)")
    public void createActedInRelationship(String personName, String movieTitle);

    @Query("MATCH (m:Movie) WHERE m.title = $movieTitle MATCH (p:Person) WHERE p.name = $personName MERGE (p)-[:WROTE]->(m)")
    public void createWroteRelationship(String personName, String movieTitle);

    @Query("MATCH (m:Movie) WHERE m.title = $movieTitle MATCH (p:Person) WHERE p.name = $personName MERGE (p)-[:PRODUCED]->(m)")
    public void createProducedRelationship(String personName, String movieTitle);

    @Query("MATCH (m:Movie) WHERE m.title = $movieTitle MATCH (p:Person) WHERE p.name = $personName MERGE (p)-[:DIRECTED]->(m)")
    public void createDirectedRelationship(String personName, String movieTitle);


}
