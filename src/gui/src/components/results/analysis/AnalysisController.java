package components.results.analysis;

import components.execution.NewExecutionController;
import components.results.analysis.property.PropertyChooserController;
import dto.PastEntityDTO;
import dto.PastSimulationDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnalysisController {

    @FXML
    private BorderPane analysisScreen;

    @FXML
    private Button graphButton;

    @FXML
    private Label infoLabel;

    @FXML
    private Button propertyAnalysisButton;

    private PastSimulationDTO dto;
    private FlowPane resultsFlowPane;
    private BorderPane resultsBorderPane;
    private NewExecutionController newExecutionController;

    public void setDto(PastSimulationDTO dto) {
        this.dto = dto;
    }

    public void setResultsFlowPane(FlowPane resultsFlowPane) {
        this.resultsFlowPane = resultsFlowPane;
    }

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public void setResultsBorderPane(BorderPane resultsBorderPane) { this.resultsBorderPane = resultsBorderPane; }


    @FXML
    public void showGraph(ActionEvent event) {
        Map<String, Map<Integer, Integer>> map = dto.getEntityToPopulation();

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Population throughout time:");

        for (String key : map.keySet()) {
            XYChart.Series series = new XYChart.Series();
            series.setName(key);
            Map<Integer, Integer> population = map.get(key);

            for (Integer tick : population.keySet()) {
                series.getData().add(new XYChart.Data(tick, population.get(tick)));
            }
            lineChart.getData().add(series);
        }

        BorderPane chartContainer = new BorderPane(lineChart);
        chartContainer.setPadding(new Insets(20));
        lineChart.getStylesheets().add("/components/results/analysis/graph.css");

        analysisScreen.setCenter(chartContainer);
    }

    @FXML
    public void showPropertyAnalysis(ActionEvent event) throws Exception {
        URL propertyAnalysisFXML = getClass().getResource("/components/results/analysis/property/propertyAnalysisChooser.fxml");
        FXMLLoader propertyAnalysisLoader = new FXMLLoader(propertyAnalysisFXML);
        VBox propertyAnalysisSelectors = propertyAnalysisLoader.load();
        PropertyChooserController propertyChooserController = propertyAnalysisLoader.getController();

        List<String> entityNames = new ArrayList<>();
        for (PastEntityDTO entity : dto.getEntitiesDTO()) {
            entityNames.add(entity.getEntityName());
        }
        propertyChooserController.setDto(dto);
        propertyChooserController.setEntitySelectorItems(entityNames);
        propertyChooserController.setNewExecutionController(newExecutionController);
        analysisScreen.setTop(propertyAnalysisSelectors);
        analysisScreen.setCenter(null);
    }


    @FXML
    public void rerunSimulation(ActionEvent event) throws Exception {
        newExecutionController.getPredictionsController().viewNewExecution(event);
        newExecutionController.setSimulationVariables(dto);
    }

}
