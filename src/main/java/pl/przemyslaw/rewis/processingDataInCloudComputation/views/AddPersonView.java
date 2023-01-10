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
import pl.przemyslaw.rewis.processingDataInCloudComputation.controllers.PersonController;
import pl.przemyslaw.rewis.processingDataInCloudComputation.models.Person;

@Route(layout = MainView.class)
@PageTitle("Add person")
public class AddPersonView extends VerticalLayout {

    public AddPersonView(@Autowired PersonController personController) {
        TextField personName = createFormTextField("Name");
        IntegerField birthYear = createFormIntegerField("Birth year");
        Button addPerson = new Button("Add Person");
        FormLayout formLayout = new FormLayout();
        formLayout.add(personName, birthYear, addPerson);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP), new FormLayout.ResponsiveStep("600px", 1, FormLayout.ResponsiveStep.LabelsPosition.ASIDE));
        add(formLayout);
        addPerson.addClickListener(e -> {
            try {
                addPerson(personController, personName, birthYear);
            } catch (DataIntegrityViolationException exception) {
                Notification.show("Person already exists");
            }

        });

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


    private void addPerson(PersonController personController, TextField personName, IntegerField birthYear) {
        if (fieldsAreNotEmpty(personName, birthYear) && yearInRange(birthYear)) {
            addPersonToDatabase(personController, personName, birthYear);
            clearFormFields(personName, birthYear);
            Notification.show("Person successfully added");
        }

    }

    private void addPersonToDatabase(PersonController personController, TextField personName, IntegerField birthYear) {
        Person newPerson = new Person();
        newPerson.setName(personName.getValue());
        newPerson.setBirthYear(birthYear.getValue());
        personController.addPerson(newPerson);
    }

    private boolean fieldsAreNotEmpty(TextField personName, IntegerField birthYear) {
        if (!personName.isEmpty() && !birthYear.isEmpty()) {
            return true;
        } else {
            Notification.show("All fields are required");
            clearFormFields(personName, birthYear);
            return false;
        }
    }

    private boolean yearInRange(IntegerField birthYear) {
        if (birthYear.getValue() > 1900 && birthYear.getValue() <= 2022) {
            return true;
        } else {
            Notification.show("Year is out of range should be > 1900 and <=2022 ");
            birthYear.clear();
            return false;
        }

    }

    private void clearFormFields(TextField personName, IntegerField birthYear) {
        personName.clear();
        birthYear.clear();
    }
}
