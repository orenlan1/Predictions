package app;

import components.details.DetailsController;
import components.main.PredictionsController;
import components.queue.management.QueueManagementController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
        BorderPane borderPane = loader.load();

        URL detailsFXML = getClass().getResource("/components/details/details.fxml");
        FXMLLoader detailsLoader = new FXMLLoader(detailsFXML);
        BorderPane details = detailsLoader.load();


        URL queueFXML = getClass().getResource("/components/queue/management/queueManagement.fxml");
        FXMLLoader queueLoader = new FXMLLoader(queueFXML);
        GridPane queue = queueLoader.load();

        PredictionsController predictionsController = loader.getController();
        PredictionsService predictionsService = new PredictionsServiceImpl();
        predictionsController.setPrimaryStage(primaryStage);
        predictionsController.setPredictionsService(predictionsService);
        String cssFileName = this.getClass().getResource("appMainSheet.css").toExternalForm();

        QueueManagementController queueManagementController = queueLoader.getController();
        queueManagementController.setPredictionsController(predictionsController);
        predictionsController.setQueueManagementController(queueManagementController);

        DetailsController detailsController = detailsLoader.getController();
        detailsController.setPredictionsController(predictionsController);
        predictionsController.setDetailsController(detailsController);

        primaryStage.setTitle("Predictions");
        Scene scene = new Scene(borderPane,1400,600);
        scene.getStylesheets().add(cssFileName);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
