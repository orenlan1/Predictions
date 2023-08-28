package components.details.entity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class EntityCardController {

    @FXML
    private Label name;

    @FXML
    private Label population;

    @FXML
    private VBox propertiesList;

    @FXML
    private GridPane entityCardGridPane;

    public void setName(String entityName) {
        name.textProperty().set("Name: " + entityName);
    }

    public void setPopulation(String population) {
        this.population.textProperty().set("Population: " + population);
    }
}
