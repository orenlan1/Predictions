package world;

import dto.FileReaderDTO;
import generated.PRDEvironment;
import generated.PRDProperty;
import generated.PRDWorld;
import predictions.api.PredictionsService;
import world.entity.api.EntityDefinition;
import world.property.api.PropertyDefinition;
import world.translator.EntityTranslator;
import world.translator.PropertyTranslator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

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

        EntityDefinition entityDefinition = EntityTranslator.TranslateEntityDefinition(prdWorld.getPRDEntities().getPRDEntity().get(0));

        //updating world

        return world;
    }




}




