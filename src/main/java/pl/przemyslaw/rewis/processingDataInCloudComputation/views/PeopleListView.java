package pl.przemyslaw.rewis.processingDataInCloudComputation.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.przemyslaw.rewis.processingDataInCloudComputation.controllers.PersonController;
import pl.przemyslaw.rewis.processingDataInCloudComputation.models.Person;

@Route(layout = MainView.class)
@PageTitle("People list")
public class PeopleListView extends VerticalLayout {

    private final PersonController personController;

    public PeopleListView(@Autowired PersonController personController) {
        this.personController = personController;
        ComboBox<String> typeOfPeopleComboBox = createComboBox();
        add(typeOfPeopleComboBox);
        Grid<Person> peopleGrid = createGrid();
        add(peopleGrid);
        typeOfPeopleComboBox.addValueChangeListener(e -> handleComboBoxSelection(e.getValue(), peopleGrid));

    }

    private ComboBox<String> createComboBox() {
        ComboBox<String> comboBox = new ComboBox<>("Choose which type of people you want to see");
        comboBox.setWidthFull();
        comboBox.setItems("Actors", "Directors", "Writers", "Producers");
        return comboBox;
    }

    private void handleComboBoxSelection(String value, Grid<Person> peopleGrid) {
        switch (value) {
            case "Actors":
                prepareGridColumns(peopleGrid);
                peopleGrid.addColumn(Person::getActedIn).setHeader("Acted in");
                peopleGrid.setItems(personController.findAllActors());
                break;
            case "Directors":
                prepareGridColumns(peopleGrid);
                peopleGrid.addColumn(Person::getDirected).setHeader("Directed");
                peopleGrid.setItems(personController.findAllDirectors());
                break;
            case "Writers":
                prepareGridColumns(peopleGrid);
                peopleGrid.addColumn(Person::getWrote).setHeader("Wrote");
                peopleGrid.setItems(personController.findAllWriters());
                break;
            case "Producers":
                prepareGridColumns(peopleGrid);
                peopleGrid.addColumn(Person::getProduced).setHeader("Produced");
                peopleGrid.setItems(personController.findAllProducers());
                break;
        }
    }

    private void prepareGridColumns(Grid<Person> peopleGrid) {
        peopleGrid.removeAllColumns();
        peopleGrid.addColumn(Person::getName).setHeader("Name").setSortable(true);
        peopleGrid.addColumn(Person::getBirthYear).setHeader("Birth year").setSortable(true);
    }

    private Grid<Person> createGrid() {
        Grid<Person> peopleGrid = new Grid<>();
        peopleGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        return peopleGrid;
    }

}