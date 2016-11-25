package oleg.work.context.xml.parsers;

import oleg.work.context.BeanDefinition;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Map;

public interface XmlContextParser {

    public Map<String, BeanDefinition> parseContext(String context);
}
