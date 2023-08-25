import components.main.PredictionsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import predictions.api.PredictionsService;
import predictions.impl.PredictionsServiceImpl;

import java.net.URL;

public class PredictionsApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        URL mainFXML = getClass().getResource("/components/main/predictionsScene.fxml");
        FXMLLoader loader = new FXMLLoader(mainFXML);
        Parent load = loader.load();
        //PredictionsController predictionsController = loader.getController();
        PredictionsService predictionsService = new PredictionsServiceImpl();
        //predictionsController.setPrimaryStage(primaryStage);
        //predictionsController.setPredictionsService(predictionsService);

        primaryStage.setTitle("Predictions");
        Scene scene = new Scene(load,1400,600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
