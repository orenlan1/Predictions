package dto;

public class UserInputEnvironmentVariableDTO {

    private final String name;
    private final String value;

    public UserInputEnvironmentVariableDTO(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() { return name; }

    public String getValue() {
        return value;
    }
}
