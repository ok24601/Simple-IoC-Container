package oleg.work.context;


public class BeanProperty {

    private String name, value, ref;

    public BeanProperty(){}

    public BeanProperty(String name, String value, String ref) {
        this.name = name;
        this.value = value;
        this.ref = ref;
    }

    @Override
    public String toString() {
        return String.format("[name = %s, value = %s, ref = %s]", name, value, ref);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getRef() {
        return ref;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
