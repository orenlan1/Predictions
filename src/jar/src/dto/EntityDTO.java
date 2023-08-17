package dto;

import java.util.List;

public class EntityDTO {
    private final String entityName;
    private final Integer population;
    private final List<PropertyDTO> propertiesList;

    public EntityDTO(String entityName, Integer population, List<PropertyDTO> propertiesList) {
        this.entityName = entityName;
        this.population = population;
        this.propertiesList = propertiesList;
    }

    public String getEntityName() {
        return entityName;
    }

    public Integer getPopulation() {
        return population;
    }

    public List<PropertyDTO> getPropertiesList() {
        return propertiesList;
    }
}
