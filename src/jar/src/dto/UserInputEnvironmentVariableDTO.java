package dto;

public class UserInputEnvironmentVariableDTO {

    private final PropertyDTO propertyDTO;
    private final String value;

    public UserInputEnvironmentVariableDTO(PropertyDTO propertyDTO, String value) {
        this.propertyDTO = propertyDTO;
        this.value = value;
    }

    public PropertyDTO getPropertyDTO() {
        return propertyDTO;
    }

    public String getValue() {
        return value;
    }
}
