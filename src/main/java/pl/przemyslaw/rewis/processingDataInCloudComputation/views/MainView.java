package pl.przemyslaw.rewis.processingDataInCloudComputation.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("")
@PageTitle("Project")
public class MainView extends AppLayout {

    public MainView() {
        this.getElement().setAttribute("theme", Lumo.DARK);
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Movie recommendation");
        logo.addClassNames("text-l", "m-m");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");
        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink homeListLink = new RouterLink("Home", MainView.class);
        RouterLink movieListLink = new RouterLink("Movies", MovieListView.class);
        RouterLink peopleListLink = new RouterLink("People", PeopleListView.class);
        RouterLink addMovieListLink = new RouterLink("Add movie", AddMovieView.class);
        RouterLink addRelationshipListLink = new RouterLink("Add relationship", AddRelationshipView.class);
        RouterLink addPersonListLink = new RouterLink("Add person", AddPersonView.class);
        RouterLink movieRecommendationListLink = new RouterLink("Recommend movie", MovieRecommendationView.class);
        addToDrawer(new VerticalLayout(homeListLink, movieListLink, peopleListLink, addMovieListLink, addRelationshipListLink, addPersonListLink, movieRecommendationListLink));
    }
}