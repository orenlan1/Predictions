package world;

import dto.FileReaderDTO;
import generated.PRDEvironment;
import generated.PRDProperty;
import generated.PRDWorld;
import world.property.api.PropertyDefinition;
import world.translator.PropertyTranslator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

public class EngineFileReader {

    private final static String JAXB_XML_PACKAGE_NAME = "generated";
    public static FileReaderDTO checkFileValidation(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return new FileReaderDTO(Boolean.FALSE, "The xml file does not exist.");
            }
            InputStream inputStream = new FileInputStream(file);
            JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
            Unmarshaller u = jc.createUnmarshaller();
            PRDWorld prdWorld = (PRDWorld) u.unmarshal(inputStream);
            PropertyDefinition propertyDefinition = PropertyTranslator.TranslatePropertyDefinition(prdWorld.getPRDEntities().getPRDEntity().get(0).getPRDProperties().getPRDProperty().get(0));
            int x = 5;
        } catch (Exception e) {
            return new FileReaderDTO(Boolean.FALSE, e.getMessage());
        }
        return null;
    }




}




