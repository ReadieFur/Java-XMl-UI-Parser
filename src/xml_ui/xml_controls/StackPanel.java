package xml_ui.xml_controls;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import xml_ui.Helpers;
import xml_ui.UIBuilder;
import xml_ui.XMLUI;
import xml_ui.attributes.ChildBuilderAttribute;
import xml_ui.attributes.CreatorAttribute;
import xml_ui.attributes.SetterAttribute;

public class StackPanel
{
    private StackPanel(){}

    @CreatorAttribute
    public static JPanel Create()
    {
        JPanel panel = new JPanel();
        //Default to using a vertical layout.
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    @SetterAttribute("Orientation")
    public static JPanel SetOrientation(JPanel panel, String orientation) throws SAXException
    {
        if (orientation.equals("Horizontal"))
        {
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        }
        else if (orientation.equals("Vertical"))
        {
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        }
        else
        {
            throw new SAXException("Invalid orientation: " + orientation);
        }
        return panel;
    }

    @SetterAttribute("Background")
    public static void SetBackground(JPanel panel, String background)
    {
        panel.setOpaque(true);
        panel.setBackground(Helpers.ParseColour(background));
    }

    @ChildBuilderAttribute
    public static void AddChildren(JPanel panel, List<Node> children, XMLUI context)
        throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, DOMException, SAXException
    {
        for (Node child : children)
        {
            panel.add(UIBuilder.ParseXMLNode(child, context));
        }
    }
}
