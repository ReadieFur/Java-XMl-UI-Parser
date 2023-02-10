package xml_ui;

import org.w3c.dom.Node;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Helpers
{
    private Helpers(){}

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
}
