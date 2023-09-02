package components.details.rules.manager.action.increase;

import components.details.rules.manager.action.CardController;
import dto.ActionDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class IncreaseDecreaseCardController implements CardController {

    @FXML
    private Label byLabel;

    @FXML
    private Label mainEntityLabel;

   /* @FXML
    private GridPane ruleCardGridPane;*/

    @FXML
    private Label secondaryEntityLabel;

    @FXML
    private Label typeLabel;

    @Override
    public void setCard(ActionDTO dto) {
        setTypeLabel(dto.getType());
        setMainEntityLabel(dto.getPrimaryEntity());
        setSecondaryEntityLabel(dto.getSecondaryEntity());
        setByLabel(dto.getArgs().get(0));
    }

    public void setByLabel(String by) {
        byLabel.textProperty().set("By: " + by);
    }

    public void setMainEntityLabel(String entity) {
        mainEntityLabel.textProperty().set("Main entity: " + entity);
    }

    public void setSecondaryEntityLabel(String entity) {
        secondaryEntityLabel.textProperty().set("Secondary entity: " + entity);
    }

    public void setTypeLabel(String type) {
        typeLabel.textProperty().set("Type: " + type);
    }

}
