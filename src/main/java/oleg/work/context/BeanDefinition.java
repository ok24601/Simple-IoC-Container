package oleg.work.context;

import javax.xml.bind.annotation.*;
import java.util.Map;

public class BeanDefinition {

    private String id;

    private String clazz;

    private Map<String, BeanProperty> properties;

    public BeanDefinition(){}

    public BeanDefinition(String id, String clazz, Map<String, BeanProperty> properties) {
        this.id = id;
        this.clazz = clazz;
        this.properties = properties;
    }

    @Override
    public String toString() {
        StringBuilder tostr = new StringBuilder();
        tostr.append(String.format("id: %s, class: %s, props: ", id, clazz));
        properties.forEach((k, v) -> tostr.append(v));
        return tostr.toString();
    }


    public String getId() {
        return id;
    }

    public String getClazz() {
        return clazz;
    }

    public Map<String, BeanProperty> getProperties() {
        return properties;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public void setProperties(Map<String, BeanProperty> properties) {
        this.properties = properties;
    }
}
