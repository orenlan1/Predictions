package components.results.simulation.info;

import components.results.ResultsController;
import components.results.simulation.info.analysis.AnalysisController;
import dto.PastSimulationDTO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private TitledPane titledPaneName;

    private ResultsController resultsController;
    private Integer id;
    private BooleanProperty enableAnalysis;

    public SimulationInfoController() {
        enableAnalysis = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize() {
        //TODO analysisButton.disableProperty().bind(enableAnalysis.not());
    }

    public Integer getId() { return id; }

    public void setIdAndName(Integer id) {
        this.id = id;
        titledPaneName.setText("Simulation " + id);
    }

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
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

    }

    public void markSimulationFinished() { enableAnalysis.setValue(true); }

}
