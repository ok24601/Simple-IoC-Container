# Part 1

Applicaition should read xml configuration file (similar to Spring)


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
 _______________________________________________________________________________________
 
# Part 2
 
 Pls. use configuration and Jaxb reader from #INTEDU-15 task and develop framework which provides initialization of context according to read config file.
Test should cover next cases:
1. 
<bean id="bean_id" class="class_name">
<property name="p_name1" value="p_value1" />
<property name="p_name2" value="p_value2" />
</bean>
2.
<bean id="bean_id" class="class_name">
<property name="p_name1" value="p_value1" />
<property name="p_name2" ref="bean_id" />
</bean>
Requirements:
Maven
JUnit
