package components.details.termination;

import dto.TerminationDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TerminationCardController {

    @FXML
    private GridPane terminationCardGridPane;

    @FXML
    private Label seconds;

    @FXML
    private Label ticks;

    public void setTermination(TerminationDTO dto) {
        setSeconds(dto.getSecondCount());
        setTicks(dto.getTicksCount());
    }

    public void setSeconds(Integer seconds) {
        if (seconds != null)
            this.seconds.textProperty().set("Seconds: " + seconds.toString());
        else
            this.seconds.textProperty().set("No time limit");
    }

    public void setTicks(Integer ticks) {
        if (ticks != null)
            this.ticks.textProperty().set("Ticks: " + ticks.toString());
        else
            this.ticks.textProperty().set("No ticks limit");
    }
}
