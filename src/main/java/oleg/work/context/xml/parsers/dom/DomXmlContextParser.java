package oleg.work.context.xml.parsers.dom;

import oleg.work.context.BeanDefinition;
import oleg.work.context.BeanProperty;
import oleg.work.context.xml.parsers.XmlContextParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DomXmlContextParser implements XmlContextParser {

    private DocumentBuilder builder;

    private Logger logger = Logger.getLogger(DomXmlContextParser.class.getName());

    public DomXmlContextParser() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Parser creating error!");
        }
    }

    @Override
    public Map<String, BeanDefinition> parseContext(String context) {
        Map<String, BeanDefinition> beanDefs = new HashMap<>();
        try {
            Document doc = builder.parse(getClass().getClassLoader().getResourceAsStream(context));
            Element root = doc.getDocumentElement();

            NodeList beanNodes = root.getElementsByTagName("bean");

            // Parse beans tags
            for (int i = 0; i < beanNodes.getLength(); i++) {
                Node beanNode = beanNodes.item(i);

                Node idAttr = beanNode.getAttributes().getNamedItem("id");
                Node clazzAttr = beanNode.getAttributes().getNamedItem("class");

                String id = (idAttr != null)? idAttr.getNodeValue() : null;
                String clazz = (clazzAttr != null)? clazzAttr.getNodeValue() : null;

                Map<String, BeanProperty> beanProperties = new HashMap<>();
                NodeList propertyNodes = beanNode.getChildNodes();

                // Parse properties tags
                for(int j = 0; j < propertyNodes.getLength(); j++) {
                    Node currentProp = propertyNodes.item(j);

                    if(currentProp.getNodeName().equals("property")){
                        Node nameAttr = currentProp.getAttributes().getNamedItem("name");
                        Node valueAttr = currentProp.getAttributes().getNamedItem("value");
                        Node refAttr = currentProp.getAttributes().getNamedItem("ref");

                        String name = (nameAttr != null)? nameAttr.getNodeValue() : null;
                        String value = (valueAttr != null)? valueAttr.getNodeValue() : null;
                        String ref = (refAttr != null)? refAttr.getNodeValue() : null;

                        beanProperties.put(nameAttr.getNodeValue(), new BeanProperty(name, value, ref));
                    }
                }
                BeanDefinition def = new BeanDefinition(id, clazz, beanProperties);
                beanDefs.put(idAttr.getNodeValue(), def);
                logger.info("Added bean definition: " + def);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanDefs;
    }
}
