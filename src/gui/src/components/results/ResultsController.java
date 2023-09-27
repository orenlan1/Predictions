package components.results;

import components.execution.NewExecutionController;
import components.main.PredictionsController;
import components.results.simulation.info.SimulationInfoController;
import dto.PastSimulationDTO;
import dto.SimulationsStatusDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

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

        SimulationsStatusDTO allSimulationsStatus = predictionsController.getAllSimulationStatus();
        Map<Integer, Boolean> simulationsRunning = allSimulationsStatus.getRunningSimulations();
        Map<Integer, Boolean> simulationsValid = allSimulationsStatus.getValidSimulations();
        for (Integer key : idToSimulationInfoController.keySet()) {
            if (!simulationsRunning.get(key))
                if (simulationsValid.get(key))
                    idToSimulationInfoController.get(key).markSimulationFinished();
                else
                    idToSimulationInfoController.get(key).markSimulationFailed();
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
