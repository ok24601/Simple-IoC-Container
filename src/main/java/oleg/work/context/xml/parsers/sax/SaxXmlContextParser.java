package oleg.work.context.xml.parsers.sax;

import oleg.work.context.BeanDefinition;
import oleg.work.context.BeanProperty;
import oleg.work.context.xml.parsers.XmlContextParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SaxXmlContextParser implements XmlContextParser {


    @Override
    public Map<String, BeanDefinition> parseContext(String context) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SaxHandler saxContextHandler = new SaxHandler();
            saxParser.parse(getClass().getClassLoader().getResourceAsStream(context), saxContextHandler);
            return saxContextHandler.getBeanDefs();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    static class SaxHandler extends DefaultHandler {

        private Map<String, BeanDefinition> beanDefs;
        private BeanDefinition currentBean;
        private Map<String, BeanProperty> properties;

        public SaxHandler() {
            beanDefs = new HashMap<>();
            properties = new HashMap<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if(qName.equalsIgnoreCase("bean")) {
                currentBean = new BeanDefinition();
                String id = attributes.getValue("id");
                String clazz = attributes.getValue("class");
                currentBean.setId(id);
                currentBean.setClazz(clazz);
            }
            else if(qName.equalsIgnoreCase("property")) {
                String name = attributes.getValue("name");
                String value = attributes.getValue("value");
                String ref = attributes.getValue("ref");
                properties.put(name, new BeanProperty(name, value, ref));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equalsIgnoreCase("bean")) {
                currentBean.setProperties(properties);
                beanDefs.put(currentBean.getId(), currentBean);
                properties = new HashMap<>();
            }

        }

        public Map<String, BeanDefinition> getBeanDefs() {
            return beanDefs;
        }
    }
}
