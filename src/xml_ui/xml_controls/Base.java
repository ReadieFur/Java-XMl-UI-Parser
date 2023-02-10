package xml_ui.xml_controls;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Base
{
    private static final String XML_CONTROLS_NAMESPACE = Base.class.getCanonicalName().substring(0,
        Base.class.getCanonicalName().length() - Base.class.getSimpleName().length() - 1);

    // public String Name;

    protected static void SetBaseProperties(Component component, Node xmlNode)
    {
        if (xmlNode.hasAttributes())
        {
            Node name = xmlNode.getAttributes().getNamedItem("Name");
            if (name != null)
                component.setName(name.getNodeValue());
        }
    }

    //Gets all nodes where the node type is an element.
    protected static List<Node> GetChildElements(Node xmlNode)
    {
        List<Node> children = new ArrayList<>();
        for (int i = 0; i < xmlNode.getChildNodes().getLength(); i++)
        {
            if (xmlNode.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
                children.add(xmlNode.getChildNodes().item(i));
        }
        return children;
    }

    //This is the base method for parsing XML nodes. It will try to find a corresponding class to create a Component from.
    public static Component BaseParseXMLNode(Node xmlNode) throws SAXException
    {
        //Check if an element exists for the control type.
        Class<?> controlType;
        try { controlType = Class.forName(XML_CONTROLS_NAMESPACE + "." + xmlNode.getNodeName()); }
        catch (ClassNotFoundException ex) { throw new SAXException("Unknown control type: " + xmlNode.getNodeName()); }

        //Call the Parse method on the control type (all controls should have this method under this namespace).
        try
        {
            Object instance = controlType.getDeclaredConstructor().newInstance();
            //nameof() would be useful here.
            Component element = (Component)controlType.getMethod("ParseXMLNode", Node.class).invoke(instance, xmlNode);
            instance = null; //Cause the GC to collect the instance.

            return element;
        }
        catch (IllegalAccessException| IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException | InstantiationException ex)
        {
            throw new SAXException("Unable to parse control: " + xmlNode.getNodeName(), ex);
        }
    }

    //This is the interface method for parsing XML nodes. It will be implemented by each control that will then return a Component.
    public abstract Component ParseXMLNode(Node xmlNode) throws SAXException;
}
