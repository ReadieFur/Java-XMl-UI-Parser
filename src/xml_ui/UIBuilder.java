package xml_ui;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import xml_ui.attributes.ChildBuilderAttribute;
import xml_ui.attributes.CreatorAttribute;
import xml_ui.attributes.SetterAttribute;

import java.awt.Component;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class UIBuilder
{
    private static final String XML_CONTROLS_NAMESPACE = UIBuilder.class.getCanonicalName().substring(0,
        UIBuilder.class.getCanonicalName().length() - UIBuilder.class.getSimpleName().length() - 1) + ".xml_controls";

    private UIBuilder(){}

    public static List<Node> GetElementNodes(Node node)
    {
        List<Node> elementNodes = new ArrayList<>();
        for (int i = 0; i < node.getChildNodes().getLength(); i++)
        {
            Node childNode = node.getChildNodes().item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE)
                elementNodes.add(childNode);
        }
        return elementNodes;
    }

    public static Color ParseColour(String colour)
    {
        if (colour.startsWith("#"))
            return Color.decode(colour);
        else
            return Color.getColor(colour);
    }

    //This is the base method for parsing XML nodes. It will try to find a corresponding class to create a Component from.
    public static Component ParseXMLNode(Node xmlNode)
        throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DOMException, SAXException
    {
        //Check if an element exists for the control type.
        Class<?> controlType = Class.forName(XML_CONTROLS_NAMESPACE + "." + xmlNode.getNodeName());

        //Get the creator method.
        Method creatorMethod = null;
        int creatorMethodCount = 0;
        for (Method method : controlType.getMethods())
        {
            if (method.getAnnotation(CreatorAttribute.class) == null)
                continue;

            //Make sure that the method accepts no parameters.
            if (method.getParameterCount() != 0)
                throw new IllegalArgumentException(
                    "Creator methods must accept no parameters. (" + controlType.getSimpleName() + "::" + method.getName() + ")");

            if (++creatorMethodCount > 1)
                throw new IllegalArgumentException(
                    "Only one creator method is allowed per control type. (" + controlType.getSimpleName() + "::" + method.getName() + ")");

            creatorMethod = method;
        }
        if (creatorMethod == null)
            throw new IllegalArgumentException("No creator method was found for the control type '" + controlType.getSimpleName() + "'.");

        //Create the control.
        Component control = (Component)creatorMethod.invoke(null);

        //Get the class attribute setter methods.
        List<Method> attributeSetters = new ArrayList<>();
        for (Method method : controlType.getMethods())
        {
            SetterAttribute setterAttribute = method.getAnnotation(SetterAttribute.class);
            if (setterAttribute == null)
                continue;

            //Make sure that the method accepts (Component, String) as parameters.
            if (method.getParameterCount() != 2
                || method.getParameterTypes()[0] != control.getClass()
                || method.getParameterTypes()[1] != String.class)
                throw new IllegalArgumentException(
                    "Setter methods must accept (Component, String) as the parameters. (" + controlType.getSimpleName() + "::" + method.getName() + ")");

            attributeSetters.add(method);
        }

        //For each attribute on the XML node, we will try to find a corresponding setter method on the control type.
        for (int i = 0; i < xmlNode.getAttributes().getLength(); i++)
        {
            //Get the XML attribute information.
            Node xmlAttribute = xmlNode.getAttributes().item(i);
            String xmlAttributeName = xmlAttribute.getNodeName();

            //If the attribute is a "Name" attribute, we will set the name of the control as this is a global directive (i.e. it is available to on all controls).
            if (xmlAttributeName.equals("Name"))
            {
                control.setName(xmlAttribute.getNodeValue());
                continue;
            }

            //Try to find a setter method for the attribute.
            Method attributeSetter = null;
            for (Method method : attributeSetters)
            {
                SetterAttribute setterAttribute = method.getAnnotation(SetterAttribute.class);

                if (setterAttribute.value().equals(xmlAttributeName))
                {
                    attributeSetter = method;
                    break;
                }
            }
            if (attributeSetter == null)
            {
                //Some child attributes are to be used by other controls, so we will ignore them.
                continue;
            }

            //Invoke the setter method.
            attributeSetter.invoke(null, control, xmlAttribute.getNodeValue());
        }

        //Parse the child nodes (if the control type has a child builder).
        List<Node> children = GetElementNodes(xmlNode);
        if (!children.isEmpty())
        {
            Method childBuilder = null;
            int childBuilderCount = 0;
            for (Method method : controlType.getMethods())
            {
                if (method.getAnnotation(ChildBuilderAttribute.class) == null)
                    continue;

                //Make sure that the method accepts one parameter: List<Node>.
                if (method.getParameterCount() != 2
                    || method.getParameterTypes()[0] != control.getClass()
                    || method.getParameterTypes()[1] != List.class)
                    // || method.getParameterTypes()[1].getTypeParameters()[0].getGenericDeclaration() != Node.class)
                    throw new IllegalArgumentException("Child builder methods must accept (Control, List<Node>) as the parameters." +
                        " (" + controlType.getSimpleName() + "::" + method.getName() + ")");

                if (++childBuilderCount > 1)
                    throw new IllegalArgumentException(
                        "Only one child builder method is allowed per control type. (" + controlType.getSimpleName() + ")");

                childBuilder = method;
            }
            if (childBuilder == null)
                throw new SAXException("The control " + xmlNode.getNodeName() + " cannot have child nodes.");

            //Invoke the child builder method.
            childBuilder.invoke(null, control, children);
        }

        return control;
    }
}
