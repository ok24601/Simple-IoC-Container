package oleg.work;

import oleg.work.context.BeanDefinition;
import oleg.work.context.xml.parsers.XmlContextParser;
import oleg.work.context.xml.parsers.dom.DomXmlContextParser;
import oleg.work.context.xml.parsers.jaxb.JaxbXmlContextParser;
import oleg.work.context.xml.parsers.sax.SaxXmlContextParser;
import org.junit.Test;

import java.util.Map;
import java.util.logging.Logger;

public class XmlParserTest {

    Logger log = Logger.getLogger(XmlParserTest.class.getName());

    public static final String CONTEXT_PATH = "context.xml";
    XmlContextParser xmlParser;

    @Test
    public void SAX_ParseContextTest() {
        xmlParser = new SaxXmlContextParser();
        Map<String, BeanDefinition> parsedBeanDefinitions = xmlParser.parseContext(CONTEXT_PATH);
        parsedBeanDefinitions.forEach((id, def) -> {
            log.info("SAX Parsed BeanDefinition: " + id + " : " + def.getClazz());
        });
    }

    @Test
    public void DOM_ParserContextTest() {
        xmlParser = new DomXmlContextParser();
        Map<String, BeanDefinition> parsedBeanDefinitions = xmlParser.parseContext(CONTEXT_PATH);
        parsedBeanDefinitions.forEach((id, def) -> {
            log.info("DOM Parsed BeanDefinition: " + id + " : " + def.getClazz());
        });
    }
    @Test
    public void JAXB_ParserContextTest() {
        xmlParser = new JaxbXmlContextParser();
        Map<String, BeanDefinition> parsedBeanDefinitions = xmlParser.parseContext(CONTEXT_PATH);
        parsedBeanDefinitions.forEach((id, def) -> {
            log.info("DOM Parsed BeanDefinition: " + id + " : " + def.getClazz());
        });
    }




}
