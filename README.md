Applicaition should read xml configuration file (similar to Spring)

<?xml version="1.0" encoding="UTF-8"?>
<context>
	<bean id="bean_id" class="class_name">
		<property name="p_name1" value="p_value" />
		<property name="p_name2" ref="<bean_id>" />
	</bean>
	...............................
</context>

1. Based on SAX
2. Based on DOM
3. Based on Jaxb

How possible to validate the XML configuration?

General requirements:
Maven
Junit

 When you will finish with XSD file, pls. try to  generate Java classes with Jaxb tools.
