package app;

import components.details.DetailsController;
import components.execution.NewExecutionController;
import components.main.PredictionsController;
import components.queue.management.QueueManagementController;
import components.results.ResultsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import predictions.api.PredictionsService;
import predictions.impl.PredictionsServiceImpl;

import java.net.URL;

public class PredictionsApp extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL mainFXML = getClass().getResource("/components/main/predictionsScene.fxml");
        FXMLLoader loader = new FXMLLoader(mainFXML);
        ScrollPane mainScrollPane = loader.load();

        URL detailsFXML = getClass().getResource("/components/details/details.fxml");
        FXMLLoader detailsLoader = new FXMLLoader(detailsFXML);
        BorderPane details = detailsLoader.load();

        URL queueFXML = getClass().getResource("/components/queue/management/queueManagement.fxml");
        FXMLLoader queueLoader = new FXMLLoader(queueFXML);
        Parent queue = queueLoader.load();

        URL newExecutionFXML = getClass().getResource("/components/execution/newExecution.fxml");
        FXMLLoader newExecutionLoader = new FXMLLoader(newExecutionFXML);
        BorderPane newExecution = newExecutionLoader.load();

        URL resultsFXML = getClass().getResource("/components/results/results.fxml");
        FXMLLoader resultsLoader = new FXMLLoader(resultsFXML);
        BorderPane results = resultsLoader.load();

        PredictionsController predictionsController = loader.getController();
        PredictionsService predictionsService = new PredictionsServiceImpl();
        predictionsController.setPrimaryStage(primaryStage);
        predictionsController.setPredictionsService(predictionsService);
        String cssFileName = this.getClass().getResource("appMainSheet.css").toExternalForm();

        QueueManagementController queueManagementController = queueLoader.getController();
        queueManagementController.setPredictionsController(predictionsController);
        predictionsController.setQueueManagementController(queueManagementController);
        queueManagementController.setPrimaryStage(primaryStage);

        DetailsController detailsController = detailsLoader.getController();
        detailsController.setPredictionsController(predictionsController);
        predictionsController.setDetailsController(detailsController);

        NewExecutionController newExecutionController = newExecutionLoader.getController();
        newExecutionController.setPredictionsController(predictionsController);
        predictionsController.setNewExecutionController(newExecutionController);
        newExecutionController.setThreadPoolDelegate(queueManagementController.getThreadPoolDelegate());

        ResultsController resultsController = resultsLoader.getController();
        predictionsController.setResultsController(resultsController);
        newExecutionController.setResultsController(resultsController);

        primaryStage.setTitle("Predictions");
        primaryStage.widthProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.intValue() < 700)
                mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            else
                mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }));

        primaryStage.heightProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.intValue() < 550)
                mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            else
                mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }));

        Scene scene = new Scene(mainScrollPane,1400,600);
        scene.getStylesheets().add(cssFileName);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
