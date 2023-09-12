package components.details;

import components.details.entity.EntityCardController;
import components.details.environment.variable.EnvVariableCardController;
import components.details.grid.GridCardController;
import components.details.rules.manager.RulesManagerController;
import components.details.termination.TerminationCardController;
import components.main.PredictionsController;
import dto.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private Button rulesButton;

    @FXML
    private Button gridAndTerminationButton;

    @FXML
    private Button clearButton;

    @FXML
    private FlowPane detailsFlowPane;

    @FXML
    private ScrollPane detailsScrollPane;


    public void showDetailsMenu(BorderPane borderPane) {
        borderPane.setCenter(detailsBorderPane);
        String cssFileName = getClass().getResource("detailsSheet.css").toExternalForm();
        borderPane.getStylesheets().add(cssFileName);
    }


    private PredictionsController predictionsController;
    private Integer x;
    private Integer y;

    public void setGridSize(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getGridSize() { return x * y; }

    public void setPredictionsController(PredictionsController predictionsController) {
        this.predictionsController = predictionsController;
    }

    public void showEnvVariables(ActionEvent event) throws Exception {
        detailsFlowPane.getChildren().clear();
        detailsBorderPane.setCenter(detailsScrollPane);

        for (PropertyDTO dto : predictionsController.getEnvVariablesDTO()) {
            URL envVariableDetailsFXML = getClass().getResource("/components/details/environment/variable/environmentVariableDetails.fxml");
            FXMLLoader loader = new FXMLLoader(envVariableDetailsFXML);
            GridPane envVariableCard = loader.load();

            EnvVariableCardController envVariableCardController = loader.getController();
            envVariableCardController.setEnvVariableCard(dto);
            envVariableCard.getStylesheets().add("/components/details/environment/variable/envVariables.css");

            detailsFlowPane.getChildren().add(envVariableCard);
        }
    }

    public void showEntities(ActionEvent event) throws Exception {
        detailsFlowPane.getChildren().clear();
        detailsBorderPane.setCenter(detailsScrollPane);

        for (EntityDTO dto: predictionsController.getEntitiesDTO()) {
            URL entityDetailsFXML = getClass().getResource("/components/details/entity/entityDetails.fxml");
            FXMLLoader loader = new FXMLLoader(entityDetailsFXML);
            GridPane entityCard = loader.load();

            EntityCardController entityCardController = loader.getController();
            entityCardController.setName(dto.getEntityName());
            entityCardController.setProperties(dto.getPropertiesList());
            entityCard.getStylesheets().add("/components/details/entity/entity.css");

            detailsFlowPane.getChildren().add(entityCard);
        }
    }

    public void showGridAndTermination(ActionEvent event) throws Exception {
        detailsFlowPane.getChildren().clear();
        detailsBorderPane.setCenter(detailsScrollPane);
        showTermination();
        showGrid();
    }

    public void showTermination() throws Exception {
        URL terminationDetailsFXML = getClass().getResource("/components/details/termination/terminationDetails.fxml");
        FXMLLoader loader = new FXMLLoader(terminationDetailsFXML);
        GridPane terminationCard = loader.load();

        TerminationCardController terminationCardController = loader.getController();
        terminationCardController.setTermination(predictionsController.getTerminationDTO());
        terminationCard.getStylesheets().add("/components/details/termination/termination.css");

        detailsFlowPane.getChildren().add(terminationCard);
    }

    public void showGrid() throws Exception {
        URL gridDetailsFXML = getClass().getResource("/components/details/grid/gridDetails.fxml");
        FXMLLoader loader = new FXMLLoader(gridDetailsFXML);
        GridPane gridCard = loader.load();

        GridCardController gridCardController = loader.getController();
        gridCardController.setAxisLabels(x, y);
        gridCard.getStylesheets().add("/components/details/grid/grid.css");

        detailsFlowPane.getChildren().add(gridCard);
    }

    public void showRules() throws Exception {
        detailsFlowPane.getChildren().clear();

        URL rulesManagerDetailsFXML = getClass().getResource("/components/details/rules/manager/rulesManager.fxml");
        FXMLLoader loader = new FXMLLoader(rulesManagerDetailsFXML);
        BorderPane rulesManager = loader.load();

        RulesManagerController rulesManagerController = loader.getController();
        rulesManagerController.showRuleCards(predictionsController.getRulesDTO());
        detailsBorderPane.setCenter(rulesManager);
    }

    public void clearDetails(ActionEvent event) {
        detailsBorderPane.setCenter(detailsScrollPane);
        detailsFlowPane.getChildren().clear();
    }
}