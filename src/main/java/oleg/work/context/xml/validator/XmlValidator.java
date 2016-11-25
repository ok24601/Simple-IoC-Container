package oleg.work.context.xml.validator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XmlValidator {


    private Source documentSrc, schemaSrc;
    public static final String DEFAULT_SCHEMA = "beans-schema.xsd";

    public XmlValidator(String schema, String document) {
        this.schemaSrc = new StreamSource(getClass().getClassLoader().getResourceAsStream(schema));
        this.documentSrc = new StreamSource(getClass().getClassLoader().getResourceAsStream(document));
    }

    public XmlValidator(String document) {
        documentSrc = new StreamSource(getClass().getClassLoader().getResourceAsStream(document));
        schemaSrc = new StreamSource(getClass().getClassLoader().getResourceAsStream(DEFAULT_SCHEMA));
    }

    /** If document is invalid SAXParseException will be thrown
     *
     * @throws SAXException
     * @throws IOException
     */
    public void validate() {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(schemaSrc);
            Validator validator = schema.newValidator();
            validator.validate(documentSrc);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
