package xml_controls;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.awt.Component;
import java.util.List;

import javax.swing.JButton;

public class Button extends Base
{
    // public String Content;

    @Override
    public Component ParseXMLNode(Node xmlNode) throws SAXException
    {
        JButton button = new JButton();

        //Set properties.
        Base.SetBaseProperties(button, xmlNode);
        if (xmlNode.hasAttributes())
        {
            if (xmlNode.getAttributes().getNamedItem("Content") != null)
                button.setText(xmlNode.getAttributes().getNamedItem("Content").getNodeValue());
        }

        //Parse child nodes.
        //For this element we will only allow a single child node.
        List<Node> childElements = Base.GetChildElements(xmlNode);
        if (childElements.size() > 1)
            throw new SAXException("Button can only have a single child node.");
        if (childElements.size() == 1)
            button.add(Base.BaseParseXMLNode(childElements.get(0)));

        return button;
    }
}
