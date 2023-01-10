package pl.przemyslaw.rewis.processingDataInCloudComputation.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import pl.przemyslaw.rewis.processingDataInCloudComputation.controllers.MovieController;
import pl.przemyslaw.rewis.processingDataInCloudComputation.models.Movie;


@Route(layout = MainView.class)
@PageTitle("Add movie")
public class AddMovieView extends VerticalLayout {

    public AddMovieView(@Autowired MovieController movieController) {
        TextField movieTitle = createFormTextField("Title");
        IntegerField yearReleased = createFormIntegerField("Year released");
        TextField movieDescription = createFormTextField("Description");
        Button addMovie = new Button("Add Movie");
        FormLayout formLayout = new FormLayout();
        formLayout.add(movieTitle, yearReleased, movieDescription, addMovie);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("600px", 1, FormLayout.ResponsiveStep.LabelsPosition.ASIDE));
        add(formLayout);
        addMovie.addClickListener(e -> {
            try {
                addMovie(movieController, movieTitle, yearReleased, movieDescription);
            } catch (DataIntegrityViolationException exception) {
                Notification.show("Movie already exists");
            }

        });
    }

    private void addMovie(MovieController movieController, TextField movieTitle, IntegerField yearReleased, TextField movieDescription) {
        if (fieldsAreNotEmpty(movieTitle, yearReleased, movieDescription) && yearInRange(yearReleased)) {
            addMovieToDatabase(movieController, movieTitle, yearReleased, movieDescription);
            clearFormFields(movieTitle, yearReleased, movieDescription);
            Notification.show("Movie successfully added");
        }

    }

    private void addMovieToDatabase(MovieController movieController, TextField movieTitle, IntegerField yearReleased, TextField movieDescription) {
        Movie newMovie = new Movie();
        newMovie.setTitle(movieTitle.getValue());
        newMovie.setReleased(yearReleased.getValue());
        newMovie.setDescription(movieDescription.getValue());
        movieController.addMovie(newMovie);
    }

    private TextField createFormTextField(String label) {
        TextField newTextField = new TextField(label);
        newTextField.setRequiredIndicatorVisible(true);
        return newTextField;
    }

    private IntegerField createFormIntegerField(String label) {
        IntegerField integerField = new IntegerField(label);
        integerField.setRequiredIndicatorVisible(true);
        integerField.setMin(1900);
        integerField.setMax(2022);
        return integerField;
    }

    private boolean fieldsAreNotEmpty(TextField movieTitle, IntegerField yearReleased, TextField movieDescription) {
        if (!movieTitle.isEmpty() && !movieDescription.isEmpty() && !yearReleased.isEmpty()) {
            return true;
        } else {
            Notification.show("All fields are required");
            clearFormFields(movieTitle, yearReleased, movieDescription);
            return false;
        }
    }

    private boolean yearInRange(IntegerField yearReleased) {
        if (yearReleased.getValue() > 1900 && yearReleased.getValue() <= 2022) {
            return true;
        } else {
            Notification.show("Year is out of range should be > 1900 and <=2022 ");
            yearReleased.clear();
            return false;
        }

    }

    private void clearFormFields(TextField movieTitle, IntegerField yearReleased, TextField movieDescription) {
        movieTitle.clear();
        yearReleased.clear();
        movieDescription.clear();
    }


}
