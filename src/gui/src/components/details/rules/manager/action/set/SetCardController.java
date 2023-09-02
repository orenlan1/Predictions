package components.details.rules.manager.action.set;

import components.details.rules.manager.action.CardController;
import dto.ActionDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SetCardController implements CardController {

    @FXML
    private Label mainEntityLabel;

    @FXML
    private Label secondaryEntityLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label valueLabel;

    @Override
    public void setCard(ActionDTO dto) {
        setTypeLabel(dto.getType());
        setMainEntityLabel(dto.getPrimaryEntity());
        setSecondaryEntityLabel(dto.getSecondaryEntity());
        setValueLabel(dto.getArgs().get(0));
    }

    public void setValueLabel(String value) {
        valueLabel.textProperty().set("Value: " + value);
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
