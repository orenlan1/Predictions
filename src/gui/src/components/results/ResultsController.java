package components.results;

import components.execution.NewExecutionController;
import components.main.PredictionsController;
import components.results.simulation.info.SimulationInfoController;
import components.results.simulation.info.analysis.AnalysisController;
import dto.PastSimulationDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResultsController {

    @FXML
    private BorderPane resultsBorderPane;

    @FXML
    private FlowPane resultsFlowPane;

    @FXML
    private ScrollPane resultsScrollPane;

    @FXML
    private Accordion pastSimulations;


    private PredictionsController predictionsController;
    private NewExecutionController newExecutionController;
    private Map<Integer, SimulationInfoController> idToSimulationInfoController;

    public ResultsController() {
        idToSimulationInfoController = new LinkedHashMap<>();
    }

    public void setPredictionsController(PredictionsController predictionsController) {
        this.predictionsController = predictionsController;
    }

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public NewExecutionController getNewExecutionController() {
        return newExecutionController;
    }

    public BorderPane getResultsBorderPane() {
        return resultsBorderPane;
    }

    public void showResults(BorderPane borderPane) {
        borderPane.setCenter(resultsBorderPane);
        resultsBorderPane.setCenter(resultsScrollPane);

        Map<Integer, Boolean> allSimulationsStatus = predictionsController.getAllSimulationStatus();
        for (Integer key : idToSimulationInfoController.keySet()) {
            if (!allSimulationsStatus.get(key))
                idToSimulationInfoController.get(key).markSimulationFinished();
        }
        pastSimulations.setExpandedPane(null);
    }

    public void addPastSimulation(Integer id) throws Exception {
        URL simulationInfoFXML = getClass().getResource("/components/results/simulation/info/simulationInfo.fxml");
        FXMLLoader simulationInfoLoader = new FXMLLoader(simulationInfoFXML);
        TitledPane simulationInfo = simulationInfoLoader.load();
        SimulationInfoController simulationInfoController = simulationInfoLoader.getController();
        simulationInfoController.setIdAndName(id);
        simulationInfoController.setResultsController(this);
        idToSimulationInfoController.put(id, simulationInfoController);

        pastSimulations.getPanes().add(simulationInfo);
    }

    public PastSimulationDTO getPastSimulation(Integer id) {
        return predictionsController.getPastSimulation(id);
    }

    public PastSimulationDTO getPastSimulationDTO(Integer id) { return predictionsController.getPastSimulation(id); }

    public void clearResults() {
        resultsBorderPane.setCenter(resultsScrollPane);
        resultsFlowPane.getChildren().clear();
    }

    public void hardReset() {
        resultsBorderPane.setCenter(resultsScrollPane);
        resultsFlowPane.getChildren().clear();
        pastSimulations.getPanes().clear();
    }

    public void pauseSimulation(Integer id) {
        predictionsController.pauseSimulation(id);
    }

    public void resumeSimulation(Integer id) {
        predictionsController.resumeSimulation(id);
    }

    public void stopSimulation(Integer id) {
        predictionsController.stopSimulation(id);
    }
}
