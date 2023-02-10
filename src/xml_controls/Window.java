package xml_controls;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.awt.Component;
import java.util.List;

import javax.swing.JFrame;

public class Window extends Base
{
    // public String Title;
    // public int Width;
    // public int Height;

    @Override
    public Component ParseXMLNode(Node xmlNode) throws SAXException
    {
        JFrame frame = new JFrame();

        //Set properties.
        Base.SetBaseProperties(frame, xmlNode);
        if (xmlNode.hasAttributes())
        {
            Node title = xmlNode.getAttributes().getNamedItem("Title");
            if (title != null)
                frame.setTitle(title.getNodeValue());
            Node width = xmlNode.getAttributes().getNamedItem("Width");
            Node height = xmlNode.getAttributes().getNamedItem("Height");
            if (width != null && height != null)
                frame.setSize(Integer.parseInt(width.getNodeValue()), Integer.parseInt(height.getNodeValue()));
        }

        //Parse child nodes.
        //For this element we will only allow a single child node, however if it contains a resources node, we will ignore that specific node.
        List<Node> childElements = Base.GetChildElements(xmlNode);
        int childCount = 0;
        Node childNode = null;
        for (int i = 0; i < childElements.size(); i++)
        {
            //Ignore the Resources node.
            if (childElements.get(i).getNodeName().equals("Resources"))
                continue;

            if (++childCount > 1)
                throw new SAXException("Window can only have a single child node.");

            childNode = childElements.get(i);
        }
        if (childNode != null)
            frame.add(Base.BaseParseXMLNode(childNode));

        return frame;
    }
}
