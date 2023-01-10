package pl.przemyslaw.rewis.processingDataInCloudComputation.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import pl.przemyslaw.rewis.processingDataInCloudComputation.controllers.MovieController;
import pl.przemyslaw.rewis.processingDataInCloudComputation.controllers.PersonController;

import java.util.ArrayList;
import java.util.List;

@Route(layout = MainView.class)
@PageTitle("Add relationship")
public class AddRelationshipView extends VerticalLayout {
    private final PersonController personController;
    private final MovieController movieController;

    public AddRelationshipView(@Autowired PersonController personController, @Autowired MovieController movieController) {
        this.personController = personController;
        this.movieController = movieController;
        ComboBox<String> peopleComboBox = createPeopleComboBox();
        ComboBox<String> relationshipsComboBox = createRelationshipsComboBox();
        ComboBox<String> moviesComboBox = createMoviesComboBox();
        Button addRelationship = new Button("Add Relationship");
        FormLayout formLayout = new FormLayout();
        formLayout.add(peopleComboBox, relationshipsComboBox, moviesComboBox, addRelationship);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP), new FormLayout.ResponsiveStep("600px", 1, FormLayout.ResponsiveStep.LabelsPosition.ASIDE));
        add(formLayout);
        addRelationship.addClickListener(e -> {
            try {
                addRelationship(relationshipsComboBox.getValue(), peopleComboBox.getValue(), moviesComboBox.getValue());
                Notification.show("Relationship successfully created");
            } catch (DataIntegrityViolationException exception) {
                Notification.show("Relationship already exists");
            }

        });

    }

    private ComboBox<String> createRelationshipsComboBox() {
        ComboBox<String> comboBox = new ComboBox<>("Relationship");
        comboBox.setItems("ACTED_IN", "WROTE", "PRODUCED", "DIRECTED");
        return comboBox;
    }

    private void addRelationship(String relationshipType, String personName, String movieTitle) {
        if (fieldsAreNotEmpty(relationshipType, personName, movieTitle)) {
            addRelationshipToDatabase(relationshipType, personName, movieTitle);
        } else {
            Notification.show("All fields are required");
        }
    }

    private void addRelationshipToDatabase(String relationshipType, String personName, String movieTitle) {
        switch (relationshipType) {
            case "ACTED_IN":
                createActedInRelationship(personName, movieTitle);
                break;
            case "WROTE":
                createWroteRelationship(personName, movieTitle);
                break;
            case "PRODUCED":
                createProducedRelationship(personName, movieTitle);
                break;
            case "DIRECTED":
                createDirectedRelationship(personName, movieTitle);
                break;
        }
    }

    private boolean fieldsAreNotEmpty(String relationshipType, String personName, String movieTitle) {
        return relationshipType != null && personName != null && movieTitle != null;
    }


    private ComboBox<String> createPeopleComboBox() {
        ComboBox<String> comboBox = new ComboBox<>("Person");
        comboBox.setItems(getPeopleNamesList());
        return comboBox;
    }

    private ComboBox<String> createMoviesComboBox() {
        ComboBox<String> comboBox = new ComboBox<>("Movie");
        comboBox.setItems(getMoviesNamesList());
        return comboBox;
    }

    private List<String> getPeopleNamesList() {
        List<String> people = new ArrayList<>();
        personController.findAll().forEach(person -> people.add(person.getName()));
        return people;
    }

    private List<String> getMoviesNamesList() {
        List<String> movies = new ArrayList<>();
        movieController.findAll().forEach(movie -> movies.add(movie.getTitle()));
        return movies;
    }

    private void createActedInRelationship(String personName, String movieTitle) {
        personController.createActedInRelationship(personName, movieTitle);
    }

    private void createWroteRelationship(String personName, String movieTitle) {
        personController.createWroteRelationship(personName,movieTitle);
    }

    private void createProducedRelationship(String personName, String movieTitle) {
        personController.createProducedRelationship(personName,movieTitle);
    }

    private void createDirectedRelationship(String personName, String movieTitle) {
        personController.createDirectedRelationship(personName, movieTitle);
    }

}
