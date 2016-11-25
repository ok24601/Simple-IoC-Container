package oleg.work.context.initializer;

import oleg.work.context.BeanDefinition;
import oleg.work.context.BeanProperty;
import oleg.work.context.xml.parsers.XmlContextParser;
import oleg.work.context.xml.parsers.dom.DomXmlContextParser;
import oleg.work.context.xml.parsers.jaxb.JaxbXmlContextParser;
import oleg.work.context.xml.parsers.sax.SaxXmlContextParser;
import oleg.work.context.xml.validator.XmlValidator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class XmlContextInitializer implements ContextInitializer {

    private String contextRef;
    private XmlContextParser xmlParser;
    private XmlValidator validator;

    private Map<String, BeanDefinition> beanDefinitions;

    public XmlContextInitializer(String context, XmlParserType parserType) {
        contextRef = context;
        validator = new XmlValidator(context);
        validator.validate();
        switch (parserType) {
            case DOM:
                xmlParser = new DomXmlContextParser();
                break;
            case SAX:
                xmlParser = new SaxXmlContextParser();
                break;
            case JAXB:
                xmlParser = new JaxbXmlContextParser();
                break;
            default:
                xmlParser = new DomXmlContextParser();
        }

    }

    @Override
    public Map<String, Object> initContext() {
        beanDefinitions = xmlParser.parseContext(contextRef);
        Map<String, Object> beans = new HashMap<>();

        // Creating beans from bean definitions!
        beanDefinitions.forEach((id, def) -> {
            try {
                Class clazz = Class.forName(def.getClazz());
                Object bean = clazz.newInstance();
                Map<String, BeanProperty> properties = def.getProperties();

                // Set beans property values!
                if (properties != null)
                    properties.forEach((name, beanProp) -> {

                        try {
                            Field field = clazz.getDeclaredField(name);
                            field.setAccessible(true);
                            Class type = field.getType();
                            if (type.isPrimitive()) {
                                String typeName = type.getName();
                                // Set primitive types values
                                if (typeName.equals("int"))
                                    field.setInt(bean, Integer.parseInt(beanProp.getValue()));
                                else if (typeName.equals("short"))
                                    field.setShort(bean, Short.parseShort(beanProp.getValue()));
                                else if (typeName.equals("long"))
                                    field.setLong(bean, Long.parseLong(beanProp.getValue()));
                                else if (typeName.equals("double"))
                                    field.setDouble(bean, Double.parseDouble(beanProp.getValue()));
                                else if (typeName.equals("float"))
                                    field.setFloat(bean, Float.parseFloat(beanProp.getValue()));
                                else if (typeName.equals("char"))
                                    field.setChar(bean, beanProp.getValue().toCharArray()[0]);
                            } else {
                                // Set reference type values
                                field.set(bean, beanProp.getValue());
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    });

                beans.put(id, bean);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
        // Inject ref values into beans
        beanDefinitions.forEach((id, def) -> {
            Map<String, BeanProperty> properties = def.getProperties();
            Object bean = beans.get(id);

            properties.forEach((name, prop) -> {
                String ref = prop.getRef();
                if (ref != null) {
                    Object injectedBean = beans.get(ref);
                    Field fields[] = bean.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        String fieldName = field.getName();
                        if (fieldName.equals(name)) {
                            try {
                                field.setAccessible(true);
                                field.set(bean, injectedBean);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            });

        });
        return beans;
    }

    public enum XmlParserType {
        DOM, JAXB, SAX
    }
}
