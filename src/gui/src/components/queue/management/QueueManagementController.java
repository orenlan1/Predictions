package components.queue.management;

import components.main.PredictionsController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
    private ThreadPoolDelegate threadPoolDelegate;

    public QueueManagementController() {
        threadPoolDelegate = new ThreadPoolDelegate();
    }

    @FXML
    public void initialize() {
        currentlyRunningCounter.textProperty().bind(Bindings.concat("", threadPoolDelegate.runningSimulationsProperty()));
        inQueueCounter.textProperty().bind(Bindings.concat("", threadPoolDelegate.simulationsInQueueProperty()));
        finishedCounter.textProperty().bind(Bindings.concat("", threadPoolDelegate.finishedSimulationsProperty()));
    }

    public void setPredictionsController(PredictionsController predictionsController) {
        this.predictionsController = predictionsController;
    }

    public void setPrimaryStage(Stage primaryStage) { threadPoolDelegate.setPrimaryStage(primaryStage); }

    public void showQueueManagement(BorderPane pane) {
        pane.setCenter(queueGridPane);
    }

    public ThreadPoolDelegate getThreadPoolDelegate() { return threadPoolDelegate; }

    public void resetThreadCount() {
        threadPoolDelegate.simulationsInQueueProperty().set(0);
        threadPoolDelegate.runningSimulationsProperty().set(0);
        threadPoolDelegate.finishedSimulationsProperty().set(0);
    }
}
