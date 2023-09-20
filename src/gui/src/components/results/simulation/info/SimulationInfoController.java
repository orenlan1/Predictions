package components.results.simulation.info;

import components.results.ResultsController;
import components.results.simulation.info.analysis.AnalysisController;
import components.results.simulation.info.progression.ProgressionController;
import dto.PastSimulationDTO;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class SimulationInfoController {

    @FXML
    private Button analysisButton;

    @FXML
    private TitledPane titledPane;

    private ResultsController resultsController;
    private Integer id;
    private BooleanProperty enableAnalysis;
    private Thread backgroundThread;
    private boolean isThreadRunning;
    private BorderPane progressionScreen;
    private ProgressionController progressionController;

    public SimulationInfoController() {
        enableAnalysis = new SimpleBooleanProperty(false);
        isThreadRunning = false;
    }

    @FXML
    public void initialize() {
        analysisButton.disableProperty().bind(enableAnalysis.not());
        titledPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                startBackgroundThread();
            } else {
                stopBackgroundThread();
            }
        });

        URL progressionFXML = getClass().getResource("/components/results/simulation/info/progression/progression.fxml");
        FXMLLoader progressionLoader = new FXMLLoader(progressionFXML);
        try {
            progressionScreen = progressionLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        progressionController = progressionLoader.getController();
        progressionScreen.getStylesheets().add("/components/results/simulation/info/progression/progression.css");
    }

    private void startBackgroundThread() {
        if (!isThreadRunning) {
            isThreadRunning = true;
            backgroundThread = new Thread(() -> {
                while (isThreadRunning) {
                    PastSimulationDTO dto = resultsController.getPastSimulationDTO(id);
                    // Push the data to the GUI using Platform.runLater
                    Platform.runLater(() -> {
                        progressionController.setDto(dto);
                        if (!dto.isRunning()) {
                            markSimulationFinished();
                            isThreadRunning = false;
                        }
                    });

                    try {
                        Thread.sleep(200); // Sleep for 200ms
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            backgroundThread.start();
        }
    }

    private void stopBackgroundThread() {
        isThreadRunning = false;
        if (backgroundThread != null) {
            backgroundThread.interrupt();
        }
    }

    public Integer getId() { return id; }

    public void setIdAndName(Integer id) {
        this.id = id;
        titledPane.setText("Simulation " + id);
    }

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
        progressionController.setResultsController(resultsController);
    }

    @FXML
    void showAnalysis(ActionEvent event) {
        URL analysisFXML = getClass().getResource("/components/results/simulation/info/analysis/analysis.fxml");
        FXMLLoader analysisLoader = new FXMLLoader(analysisFXML);
        BorderPane analysisScreen;
        try {
            analysisScreen = analysisLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AnalysisController analysisController = analysisLoader.getController();
        analysisController.setDto(resultsController.getPastSimulation(id));
        analysisController.setNewExecutionController(resultsController.getNewExecutionController());

        resultsController.getResultsBorderPane().setCenter(analysisScreen);
    }

    @FXML
    void showProgressAndEntities(ActionEvent event) {
        //TODO


        resultsController.getResultsBorderPane().setCenter(progressionScreen);
    }

    public void markSimulationFinished() { enableAnalysis.setValue(true); }
}
