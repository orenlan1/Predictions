package components.results;

import components.execution.NewExecutionController;
import components.main.PredictionsController;
import components.results.analysis.AnalysisController;
import dto.SimulationRunnerDTO;
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
    private BorderPane mainBorderPane;

    public void setPredictionsController(PredictionsController predictionsController) {
        this.predictionsController = predictionsController;
    }

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public void showResults(BorderPane borderPane) {
        borderPane.setCenter(resultsBorderPane);
        resultsBorderPane.setCenter(resultsScrollPane);
        mainBorderPane = borderPane;
    }

    public void addPastSimulation(SimulationRunnerDTO dto) {
        pastSimulations.getPanes().add(createTitledPane(dto.getId()));
    }

    public TitledPane createTitledPane(int id) {
        Button progressAndEntitiesButton = new Button("Progress and entities");
        Button analysisButton = new Button("Analysis");

        progressAndEntitiesButton.setPrefWidth(150);
        progressAndEntitiesButton.setPrefHeight(40);
        analysisButton.setPrefWidth(150);
        analysisButton.setPrefHeight(40);

        //TODO progressAndEntitiesButton.setOnAction(event -> );
        analysisButton.setOnAction(event -> showAnalysis(id));

        VBox vbox = new VBox(progressAndEntitiesButton, analysisButton);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10);

        return new TitledPane("Simulation " + id, vbox);
    }


    public void showAnalysis(int id) {
        clearResults();

        URL analysisFXML = getClass().getResource("/components/results/analysis/analysis.fxml");
        FXMLLoader analysisLoader = new FXMLLoader(analysisFXML);
        BorderPane analysisScreen;
        try {
            analysisScreen = analysisLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AnalysisController analysisController = analysisLoader.getController();
        analysisController.setDto(predictionsController.getPastSimulation(id));
        analysisController.setResultsFlowPane(resultsFlowPane);
        analysisController.setNewExecutionController(newExecutionController);
        analysisController.setMainBorderPane(mainBorderPane);

        resultsBorderPane.setCenter(analysisScreen);
    }

    public void clearResults() {
        resultsBorderPane.setCenter(resultsScrollPane);
        resultsFlowPane.getChildren().clear();
    }

    public void hardReset() {
        resultsBorderPane.setCenter(resultsScrollPane);
        resultsFlowPane.getChildren().clear();
        pastSimulations.getPanes().clear();
    }
}