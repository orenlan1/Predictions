package components.details;

import components.details.entity.EntityCardController;
import components.main.PredictionsController;
import dto.EntityDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.net.URL;

public class DetailsController {

    @FXML
    private BorderPane detailsBorderPane;

    @FXML
    private Button entitiesButton;

    @FXML
    private Button envVariablesButton;

    @FXML
    private Button gridButton;

    @FXML
    private Button rulesButton;

    @FXML
    private Button terminationButton;

    @FXML
    private FlowPane entitiesList;

    @FXML
    private ScrollPane entitiesListScrollPane;

    private PredictionsController predictionsController;


    public void setPredictionsController(PredictionsController predictionsController) {
        this.predictionsController = predictionsController;
    }

    public void showDetailsMenu(BorderPane borderPane) {
        borderPane.setCenter(detailsBorderPane);
        String cssFileName = getClass().getResource("detailsSheet.css").toExternalForm();
        borderPane.getStylesheets().add(cssFileName);
    }

    public void showEntities(ActionEvent event) throws Exception {
        entitiesList.getChildren().clear();

        for (EntityDTO dto: predictionsController.getEntitiesDTO()) {
            URL entityDetailsFXML = getClass().getResource("/components/details/entity/entityDetails.fxml");
            FXMLLoader loader = new FXMLLoader(entityDetailsFXML);
            GridPane entityCard = loader.load();

            EntityCardController entityCardController = loader.getController();
            entityCardController.setName(dto.getEntityName());
            entityCardController.setPopulation(dto.getPopulation().toString());
            entityCard.getStylesheets().add("/components/details/entity/entity.css");

            entityCardController.setProperties(dto.getPropertiesList());

            entitiesList.getChildren().add(entityCard);
        }
    }
}