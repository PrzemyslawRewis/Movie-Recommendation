package pl.przemyslaw.rewis.processingDataInCloudComputation.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.przemyslaw.rewis.processingDataInCloudComputation.controllers.MovieController;
import pl.przemyslaw.rewis.processingDataInCloudComputation.controllers.PersonController;
import pl.przemyslaw.rewis.processingDataInCloudComputation.models.Movie;

import java.util.ArrayList;
import java.util.List;

@Route(layout = MainView.class)
@PageTitle("Movie Recommendation")
public class MovieRecommendationView extends VerticalLayout {

    private final PersonController personController;
    private final MovieController movieController;

    public MovieRecommendationView(@Autowired PersonController personController, @Autowired MovieController movieController) {
        this.personController = personController;
        this.movieController = movieController;
        add(new Paragraph("Choose your favorite actor to get movies recommendation"));
        ComboBox<String> actorsComboBox = createComboBox();
        add(actorsComboBox);
        add(new Paragraph("Movies recommended for you:"));
        Grid<Movie> movieGrid = createGrid();
        add(movieGrid);
        actorsComboBox.addValueChangeListener(e -> handleComboBoxSelection(e.getValue(), movieGrid));
    }

    private ComboBox<String> createComboBox() {
        ComboBox<String> comboBox = new ComboBox<>("Actor");
        comboBox.setItems(getActorsNamesList());
        return comboBox;
    }

    private void handleComboBoxSelection(String actorName, Grid<Movie> movieGrid) {
        fillGrid(movieGrid, actorName);
    }

    private List<String> getActorsNamesList() {
        List<String> actors = new ArrayList<>();
        personController.findAllActors().forEach(person -> actors.add(person.getName()));
        return actors;
    }

    private Grid<Movie> createGrid() {
        Grid<Movie> movieGrid = new Grid<>();
        movieGrid.addColumn(Movie::getTitle).setHeader("Title").setSortable(true);
        movieGrid.addColumn(Movie::getReleased).setHeader("Year released").setSortable(true);
        movieGrid.addColumn(Movie::getDescription).setHeader("Description");
        movieGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        movieGrid.setHeightByRows(true);
        return movieGrid;
    }

    private void fillGrid(Grid<Movie> movieGrid, String actorName) {
        movieGrid.setItems(movieController.prepareMoviesRecommendation(actorName));
    }

}
