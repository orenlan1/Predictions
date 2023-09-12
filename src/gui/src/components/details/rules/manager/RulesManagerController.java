package components.details.rules.manager;

import components.details.DetailsController;
import components.details.rules.manager.rule.RuleCardController;
import dto.RuleDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.List;


public class RulesManagerController {

    @FXML
    private BorderPane rulesAndActionsContainer;

    @FXML
    private FlowPane actionsContainer;

    @FXML
    private FlowPane rulesContainer;

    @FXML
    public void initialize() {
        HBox.setHgrow(rulesContainer, Priority.ALWAYS);
        HBox.setHgrow(actionsContainer, Priority.ALWAYS);
    }

    public void showRuleCards(List<RuleDTO> DTOs) throws Exception {
        rulesContainer.getChildren().clear();

        for (RuleDTO dto : DTOs) {
            URL ruleDetailsFXML = getClass().getResource("/components/details/rules/manager/rule/ruleDetails.fxml");
            FXMLLoader loader = new FXMLLoader(ruleDetailsFXML);
            GridPane ruleCard = loader.load();

            RuleCardController ruleCardController = loader.getController();
            ruleCardController.setRulesManagerController(this);
            ruleCardController.setRuleCard(dto);
            ruleCard.getStylesheets().add("/components/details/rules/manager/rule/rule.css");

            rulesContainer.getChildren().add(ruleCard);
        }
    }

    public void showActionCard(GridPane actionCard) {
        actionsContainer.getChildren().clear();
        actionsContainer.getChildren().add(actionCard);
    }
}
