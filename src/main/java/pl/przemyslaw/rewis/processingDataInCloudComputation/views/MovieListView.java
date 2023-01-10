package pl.przemyslaw.rewis.processingDataInCloudComputation.views;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.przemyslaw.rewis.processingDataInCloudComputation.controllers.MovieController;
import pl.przemyslaw.rewis.processingDataInCloudComputation.models.Movie;
import pl.przemyslaw.rewis.processingDataInCloudComputation.models.Person;
import pl.przemyslaw.rewis.processingDataInCloudComputation.models.Role;

import java.util.List;

@Route(layout = MainView.class)
@PageTitle("Movies list")
public class MovieListView extends VerticalLayout {

    private final MovieController movieController;

    public MovieListView(@Autowired MovieController movieController) {
        this.movieController = movieController;
        Grid<Movie> movieGrid = createGrid();
        add(movieGrid);
        handleSelection(selectionOnGrid(movieGrid));
    }

    private Grid<Movie> createGrid() {
        Grid<Movie> movieGrid = new Grid<>();
        fillGrid(movieGrid);
        movieGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        return movieGrid;
    }

    private void fillGrid(Grid<Movie> movieGrid) {
        movieGrid.setItems(movieController.findAll());
        movieGrid.addColumn(Movie::getTitle).setHeader("Title").setSortable(true);
        movieGrid.addColumn(Movie::getReleased).setHeader("Year released").setSortable(true);
        movieGrid.addColumn(Movie::getDescription).setHeader("Description");
    }

    private SingleSelect<Grid<Movie>, Movie> selectionOnGrid(Grid<Movie> movieGrid) {
        movieGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        return movieGrid.asSingleSelect();
    }

    private void handleSelection(SingleSelect<Grid<Movie>, Movie> movieSelect) {
        Details details = createDefaultDetails();
        add(details);
        movieSelect.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                updateDetailsContent(details, e.getValue());
            }
        });
    }

    private Details createDefaultDetails() {
        Details details = new Details("Movie details");
        details.setOpened(false);
        return details;
    }

    private void updateDetailsContent(Details details, Movie selectedMovie) {
        details.setSummaryText("\"" + selectedMovie.getTitle() + "\" details");
        details.setContent(createDetailsContent(selectedMovie));
        details.setOpened(true);
    }

    private VerticalLayout createDetailsContent(Movie selectedMovie) {
        VerticalLayout layout = new VerticalLayout();
        prepareActorsSpans(selectedMovie, layout);
        prepareDirectorsSpans(selectedMovie, layout);
        prepareProducersSpans(selectedMovie, layout);
        prepareWritersSpans(selectedMovie, layout);
        layout.setSpacing(false);
        layout.setPadding(false);
        return layout;
    }

    private void prepareActorsSpans(Movie selectedMovie, VerticalLayout layout) {
        if (!selectedMovie.getActors().isEmpty()) {
            List<Role> roles = selectedMovie.getActors();
            for (Role role : roles) {
                for (String roleName : role.getRoles()) {
                    layout.add(new Span(role.getPerson().getName() + " acted as " + roleName));
                }
            }
        }
    }

    private void prepareDirectorsSpans(Movie selectedMovie, VerticalLayout layout) {
        if (!selectedMovie.getDirectors().isEmpty()) {
            List<Person> directors = selectedMovie.getDirectors();
            for (Person director : directors) {
                layout.add(new Span("Directed by " + director.getName()));
            }
        }

    }

    private void prepareProducersSpans(Movie selectedMovie, VerticalLayout layout) {
        if (!selectedMovie.getProducers().isEmpty()) {
            List<Person> producers = selectedMovie.getProducers();
            for (Person producer : producers) {
                layout.add(new Span("Produced by " + producer.getName()));
            }
        }
    }

    private void prepareWritersSpans(Movie selectedMovie, VerticalLayout layout) {
        if (!selectedMovie.getWriters().isEmpty()) {
            List<Person> writers = selectedMovie.getWriters();
            for (Person writer : writers) {
                layout.add(new Span("Written by " + writer.getName()));
            }
        }
    }


}
