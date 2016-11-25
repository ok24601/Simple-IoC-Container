package oleg.work;

import oleg.work.context.xml.validator.XmlValidator;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

public class XmlValidatorTest {

    @Test
    public void validContextTest() throws IOException, SAXException {
        new XmlValidator("beans-schema.xsd", "context.xml").validate();
    }

    @Test(expected = Exception.class)
    public void invalidContextTest() {
        new XmlValidator("invalid-context.xml").validate();
    }

}
