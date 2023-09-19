package components.execution;

import components.execution.entity.count.EntityCountController;
import components.execution.environment.variable.EnvVariableCardController;
import components.main.PredictionsController;
import components.results.ResultsController;
import dto.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewExecutionController {

    @FXML
    private HBox newExecutionHBox;

    @FXML
    private Button clearButton;

    @FXML
    private FlowPane entitiesCountFlowPane;

    @FXML
    private FlowPane envVariablesFlowPane;

    @FXML
    private Label infoLabel;

    @FXML
    private BorderPane newExecutionScreen;

    @FXML
    private Button runButton;

    private final Map<String, EnvVariableCardController> envVariableCardControllers;
    private final Map<String, EntityCountController> entityCountControllers;
    private Integer maxPopulation;

    private PredictionsController predictionsController;
    private ResultsController resultsController;

    public NewExecutionController() {
        envVariableCardControllers = new HashMap<>();
        entityCountControllers = new HashMap<>();
    }

    @FXML
    public void initialize() {
        HBox.setHgrow(envVariablesFlowPane, Priority.ALWAYS);
        HBox.setHgrow(entitiesCountFlowPane, Priority.ALWAYS);
        infoLabel.textProperty().set("Enter values for the environment variables. Untouched variables will be set randomly");
        maxPopulation = Integer.MAX_VALUE;
    }

    public void setPredictionsController(PredictionsController predictionsController) {
        this.predictionsController = predictionsController;
    }

    public PredictionsController getPredictionsController() { return predictionsController; }

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
        resultsController.setNewExecutionController(this);
    }

    public void setMaxPopulation(Integer maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public void showNewExecution(BorderPane borderPane) throws Exception {
        borderPane.setCenter(newExecutionScreen);
        String cssFileName = getClass().getResource("newExecution.css").toExternalForm();
        borderPane.getStylesheets().add(cssFileName);
        showEnvVariables();
        showEntityCounts();
    }

    public void showEnvVariables() throws Exception {
        envVariablesFlowPane.getChildren().clear();
        envVariableCardControllers.clear();

        for (PropertyDTO dto : predictionsController.getEnvVariablesDTO()) {
            URL envVariableCardFXML = getClass().getResource("/components/execution/environment/variable/numerical/numericalEnvVariablesCard.fxml");
            FXMLLoader loader;
            GridPane envVariableCard;
            EnvVariableCardController controller;

            String type = dto.getPropertyType();
            switch (type) {
                case "boolean":
                    envVariableCardFXML = getClass().getResource("/components/execution/environment/variable/bool/booleanEnvVariablesCard.fxml");
                    break;
                case "string":
                    envVariableCardFXML = getClass().getResource("/components/execution/environment/variable/string/stringEnvVariablesCard.fxml");
                    break;
            }
            loader = new FXMLLoader(envVariableCardFXML);
            envVariableCard = loader.load();
            controller = loader.getController();
            envVariableCardControllers.put(dto.getPropertyName() ,controller);

            controller.setCard(dto);
            envVariableCard.getStylesheets().add("/components/execution/environment/variable/envVariableCard.css");
            envVariablesFlowPane.getChildren().add(envVariableCard);
        }
    }


    public void showEntityCounts() throws Exception {
        entitiesCountFlowPane.getChildren().clear();
        entityCountControllers.clear();

        for (EntityDTO dto : predictionsController.getEntitiesDTO()) {
            URL entityCountFXML = getClass().getResource("/components/execution/entity/count/entityCount.fxml");
            FXMLLoader loader = new FXMLLoader(entityCountFXML);
            GridPane entityCountCard = loader.load();
            EntityCountController controller = loader.getController();
            entityCountControllers.put(dto.getEntityName(), controller);

            controller.setCard(dto.getEntityName(), maxPopulation);

            entityCountCard.getStylesheets().add("/components/execution/entity/count/entityCount.css");

            entitiesCountFlowPane.getChildren().add(entityCountCard);
        }
    }

    public void runNewSimulation(ActionEvent event) throws Exception {
        predictionsController.randomizeEnvironmentVariables();

        List<EntityInitializationDTO> entityInitializationDTOs = new ArrayList<>();
        for (EntityCountController controller : entityCountControllers.values()) {
            entityInitializationDTOs.add(controller.getInfo());
        }

        Integer sumPopulation = 0;
        for (EntityInitializationDTO dto : entityInitializationDTOs) {
            sumPopulation += dto.getPopulation();
        }

        if (sumPopulation > maxPopulation) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.format("Input population of %d is bigger than the maximum population allowed - %d", sumPopulation, maxPopulation));
            alert.setHeaderText(null);
            alert.show();
            return;
        } else if (sumPopulation == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Can't start a simulation with 0 entities");
            alert.setHeaderText(null);
            alert.show();
            return;
        } else {
            predictionsController.setEntitiesPopulation(entityInitializationDTOs);
        }


        List<UserInputEnvironmentVariableDTO> envVariablesDTOs = new ArrayList<>();
        for (EnvVariableCardController controller : envVariableCardControllers.values()) {
            envVariablesDTOs.add(controller.getInput());
        }

        EnvVariableSetValidationDTO valid = predictionsController.setEnvVariables(envVariablesDTOs);
        if (valid.getValidation()) {
            Integer simulationID = predictionsController.runSimulation();
            clearNewExecution(event);
            resultsController.addPastSimulation(simulationID);
            predictionsController.viewResults(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, valid.getMessage());
            alert.setHeaderText(null);
            alert.show();
        }
    }

    public void clearNewExecution(ActionEvent event) throws Exception {
        showEnvVariables();
        showEntityCounts();
    }

    public void setSimulationVariables(PastSimulationDTO dto) {
        for (EnvVariablesDTO envVariablesDTO : dto.getEnvironmentVariables()) {
            String name = envVariablesDTO.getName();
            String value = envVariablesDTO.getValue();
            envVariableCardControllers.get(name).setValue(value);
        }

        for (String name : dto.getEntityToPopulation().keySet()) {
            Integer count = dto.getEntityToPopulation().get(name).get(0);
            entityCountControllers.get(name).setEntityCount(count);
        }
    }

    public HistogramDTO getHistogram(Integer id, String entity, String property) {
        return predictionsController.getHistogram(id, entity, property);
    }

}
