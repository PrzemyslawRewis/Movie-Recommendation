package pl.przemyslaw.rewis.processingDataInCloudComputation.controllers;

import org.springframework.web.bind.annotation.RestController;
import pl.przemyslaw.rewis.processingDataInCloudComputation.models.Person;
import pl.przemyslaw.rewis.processingDataInCloudComputation.repostories.PersonRepository;

import java.util.List;

@RestController
public class PersonController {
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public List<Person> findAllProducers() {
        return personRepository.getProducers();
    }

    public List<Person> findAllActors() {
        return personRepository.getActors();
    }

    public List<Person> findAllWriters() {
        return personRepository.getWriters();
    }

    public List<Person> findAllDirectors() {
        return personRepository.getDirectors();
    }

    public void addPerson(Person person) {
        personRepository.save(person);
    }

    public void createActedInRelationship(String personName, String movieTitle) {
        personRepository.createActedInRelationship(personName, movieTitle);
    }
    public void createWroteRelationship(String personName, String movieTitle) {
        personRepository.createWroteRelationship(personName, movieTitle);
    }
    public void createProducedRelationship(String personName, String movieTitle) {
        personRepository.createProducedRelationship(personName,movieTitle);
    }
    public void createDirectedRelationship(String personName, String movieTitle) {
        personRepository.createDirectedRelationship(personName,movieTitle);
    }

}
