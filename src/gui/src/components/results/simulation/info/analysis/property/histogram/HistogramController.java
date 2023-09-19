package components.results.simulation.info.analysis.property.histogram;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HistogramController {

    @FXML
    private HBox histogramHBox;

    @FXML
    private VBox countContainer;

    @FXML
    private VBox valueContainer;

    @FXML

    public void addPair(Object value, Integer count) {
        addValue(value.toString());
        addCount(count);
    }

    public void addValue(String value) {
        Label valueLabel = new Label(value);
        valueContainer.getChildren().add(valueLabel);
    }

    public void addCount(Integer count) {
        Label countLabel = new Label(count.toString());
        countContainer.getChildren().add(countLabel);
    }
}
