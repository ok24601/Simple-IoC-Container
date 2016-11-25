package oleg.work.context.xml.parsers.jaxb;

import oleg.work.context.BeanDefinition;
import oleg.work.context.BeanProperty;
import oleg.work.context.xml.parsers.XmlContextParser;
import oleg.work.context.xml.parsers.jaxb.generated.BeanType;
import oleg.work.context.xml.parsers.jaxb.generated.ObjectFactory;
import oleg.work.context.xml.parsers.jaxb.generated.SpringXmlContext;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JaxbXmlContextParser implements XmlContextParser {
    @Override
    public Map<String, BeanDefinition> parseContext(String context) {
        Map<String, BeanDefinition> beanDefs = new HashMap<>();
        try {
            JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement<SpringXmlContext> contextJAXBElement = (JAXBElement<SpringXmlContext>) unmarshaller
                    .unmarshal(getClass().getClassLoader().getResourceAsStream(context));

            SpringXmlContext xmlContext = contextJAXBElement.getValue();
            List<BeanType> beanTypes = xmlContext.getBean();

            beanTypes.forEach(b -> {
                Map<String, BeanProperty> beanProperties = new HashMap<>();
                b.getProperty().forEach(p -> {
                    beanProperties.put(p.getName(), new BeanProperty(p.getName(), p.getValue(), p.getRef()));
                });
                beanDefs.put(b.getId(), new BeanDefinition(b.getId(), b.getClazz(), beanProperties));
            });

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return beanDefs;

    }
}
