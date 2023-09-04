package dto;

import java.util.List;

public class EntityDTO {
    private final String entityName;
    private final List<PropertyDTO> propertiesList;

    public EntityDTO(String entityName, List<PropertyDTO> propertiesList) {
        this.entityName = entityName;
        this.propertiesList = propertiesList;
    }

    public String getEntityName() {
        return entityName;
    }


    public List<PropertyDTO> getPropertiesList() {
        return propertiesList;
    }
}
