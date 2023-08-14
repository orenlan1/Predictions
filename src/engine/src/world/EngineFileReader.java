package world;

import dto.FileReaderDTO;
import generated.PRDEvironment;
import generated.PRDProperty;
import generated.PRDWorld;
import predictions.api.PredictionsService;
import world.entity.api.EntityDefinition;
import world.environment.api.EnvironmentVariablesManager;
import world.property.api.PropertyDefinition;
import world.translator.EntityTranslator;
import world.translator.EnvironmentTranslator;
import world.translator.PropertyTranslator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class EngineFileReader {
    private final static String JAXB_XML_PACKAGE_NAME = "generated";
    public World checkFileValidation(String fileName) throws Exception {
        World world = new World();
        File file = new File(fileName);
        if (!file.exists()) {
            throw new Exception("The xml file does not exist.");
        }
        InputStream inputStream = new FileInputStream(file);
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        PRDWorld prdWorld = (PRDWorld) u.unmarshal(inputStream);

        //translations
        EnvironmentVariablesManager environmentVariablesManager = EnvironmentTranslator.translateEnvironment(prdWorld.getPRDEvironment());
        List<EntityDefinition> entityDefinitions = EntityTranslator.translateEntities(prdWorld.getPRDEntities());

        //updating world
        world.setEnvironmentVariablesManager(environmentVariablesManager);
        for (EntityDefinition entity : entityDefinitions) {
            world.addEntityInstanceList(entity.getName(), entity);
        }
        return world;
    }




}




