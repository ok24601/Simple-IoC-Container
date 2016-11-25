package oleg.work;

import oleg.work.context.Context;
import oleg.work.domain.Cat;
import oleg.work.domain.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

import static oleg.work.context.initializer.XmlContextInitializer.XmlParserType.*;

public class ContextTest {

    Context context;
    Logger log = Logger.getLogger(ContextTest.class.getName());

    @Before
    public void init() {
        context = new Context("context.xml", SAX);
    }

    @Test
    public void contextInitializedTest() {
        assert context != null;
        log.info("Context successfully created: " + context);
    }

    @Test
    public void getBeanTest() {
        Person p = (Person) context.getBean("person");
        Cat c = (Cat) context.getBean("cat");

        assert p.getName().equals("Oleh");
        assert c.getName().equals("Mewth");

        log.info("GetBean test: created bean of class Person: " + p);
    }

    @Test
    public void getInjectedBeanTest() {
        Person p = (Person) context.getBean("person");
        Cat c = p.getPet();

        assert c.getName().equals("Mewth");
        log.info("Injected bean of class Cat in Person bean: " + c);
    }
}
