package world;

import dto.EntityDTO;
import dto.RuleDTO;
import dto.SimulationInfoDTO;
import world.entity.api.EntityDefinition;
import world.factory.DTOFactory;

import java.util.*;
import java.util.ArrayList;


public class SimulationInfoBuilder {

    public SimulationInfoDTO createSimulationInfo(World world) {
        List<EntityDTO> entityDTOList = createEntityDTOList(world.getNameToEntityDefinition());
        List<RuleDTO> ruleDTOList = new ArrayList<>();
        this.createEntityDTOList(world.getNameToEntityDefinition());
    }

    public List<EntityDTO> createEntityDTOList(Map<String, EntityDefinition> nameToEntity) {
        DTOFactory dtoFactory = new DTOFactory();
        List<EntityDTO> entityDTOList = new ArrayList<>();
        for ( EntityDefinition entityDefinition : nameToEntity.values()) {
            entityDTOList.add(dtoFactory.createEntityDTO(entityDefinition));
        }
        return entityDTOList;
    }
}
