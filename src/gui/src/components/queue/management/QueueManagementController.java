package components.queue.management;

import components.main.PredictionsController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class QueueManagementController {

    @FXML
    private Label currentlyRunningCounter;

    @FXML
    private Label finishedCounter;

    @FXML
    private Label inQueueCounter;

    @FXML
    private GridPane queueGridPane;

    private PredictionsController predictionsController;


    public void setPredictionsController(PredictionsController predictionsController) {
        this.predictionsController = predictionsController;
    }

    public void showQueueManagement(BorderPane pane) {
        pane.setCenter(queueGridPane);
    }

}
