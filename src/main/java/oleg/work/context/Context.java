package oleg.work.context;

import oleg.work.context.initializer.ContextInitializer;
import oleg.work.context.initializer.XmlContextInitializer;

import java.util.Map;


public class Context {

    private Map<String, Object> beansMap;
    private ContextInitializer contextInitializer;

    public Context(String context, XmlContextInitializer.XmlParserType parserType) {
        contextInitializer = new XmlContextInitializer(context, parserType);
        beansMap = contextInitializer.initContext();
    }

    public Object getBean(String id) {
        return beansMap.get(id);
    }

    @Override
    public String toString() {
        return "Simple Context with " + beansMap.size() + " beans.";
    }
}
