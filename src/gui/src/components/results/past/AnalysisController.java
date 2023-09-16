package components.results.past;

import dto.PastSimulationDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

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

    public void setDto(PastSimulationDTO dto) {
        this.dto = dto;
    }

    public void setResultsFlowPane(FlowPane resultsFlowPane) {
        this.resultsFlowPane = resultsFlowPane;
    }

    public void setResultsBorderPane(BorderPane resultsBorderPane) { this.resultsBorderPane = resultsBorderPane; }

    @FXML
    public void showGraph(ActionEvent event) {
        //Map<String, Map<Integer, Integer>> map = dto.getEntityToPopulation();

        Map<String, Map<Integer, Integer>> map = new HashMap<>();
        map.put("sick", new LinkedHashMap<>());
        map.put("healthy", new LinkedHashMap<>());

        Map<Integer, Integer> sickPop =  map.get("sick");
        Map<Integer, Integer> healthyPop =  map.get("healthy");

        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            sickPop.put(i, rand.nextInt(1000) + 1);
            healthyPop.put(i, rand.nextInt(1000) + 1);
        }


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
        lineChart.getStylesheets().add("/components/results/past/graph.css");

        analysisScreen.setCenter(chartContainer);
    }

    @FXML
    public void showPropertyAnalysis(ActionEvent event) {



    }

}
