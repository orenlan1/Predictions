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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;

public class SimulationInfoController {

    @FXML
    private Button analysisButton;

    @FXML
    private TitledPane titledPane;

    private Integer id;
    private BooleanProperty enableAnalysis;
    private Thread backgroundThread;
    private boolean isThreadRunning;
    private BorderPane progressionScreen;
    private ResultsController resultsController;
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
                clearPreviousSimulationInfo();
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
                    Platform.runLater(() -> {
                        progressionController.setDto(dto);
                        if (dto.isValid()) {
                            if (!dto.isRunning()) {
                                markSimulationFinished();
                                isThreadRunning = false;
                            }
                        } else {
                            markSimulationFailed();
                            isThreadRunning = false;
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Simulation " + dto.getId() + " had failed during its process.\n"
                                    + "Original error message: "+ dto.getMessage());
                            alert.setHeaderText(null);
                            alert.show();
                        }
                    });
                    try {
                        Thread.sleep(200);
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
        resultsController.getResultsBorderPane().setCenter(progressionScreen);
    }

    public void markSimulationFinished() {
        enableAnalysis.setValue(true);
        titledPane.lookup(".titled-pane > .title > .text").setStyle("-fx-font-weight: bold;");
        titledPane.textFillProperty().set(Color.GREEN);
    }

    public void markSimulationFailed() {
        titledPane.lookup(".titled-pane > .title > .text").setStyle("-fx-font-weight: bold;");
        titledPane.textFillProperty().set(Color.RED);
    }

    public void clearPreviousSimulationInfo() { resultsController.getResultsBorderPane().setCenter(null); }
}
