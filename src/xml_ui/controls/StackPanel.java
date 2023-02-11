package xml_ui.controls;

import java.awt.Color;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.w3c.dom.Node;

import xml_ui.attributes.ChildBuilderAttribute;
import xml_ui.attributes.CreateComponentAttribute;
import xml_ui.attributes.SetterAttribute;
import xml_ui.exceptions.InvalidXMLException;
import xml_ui.factory.UIBuilderFactory;

public class StackPanel
{
    private StackPanel(){}

    @CreateComponentAttribute
    public static JPanel Create()
    {
        JPanel panel = new JPanel();
        //Default to using a vertical layout.
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    @SetterAttribute("Orientation")
    public static JPanel SetOrientation(JPanel panel, String orientation) throws InvalidXMLException
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
            throw new InvalidXMLException("Invalid orientation: " + orientation);
        }
        return panel;
    }

    @SetterAttribute("Background")
    public static void SetBackground(JPanel panel, String colour)
    {
        panel.setOpaque(true);
        panel.setBackground(Color.decode(colour));
    }

    @ChildBuilderAttribute
    public static void AddChildren(UIBuilderFactory builder, JPanel panel, List<Node> children) throws InvalidXMLException
    {
        for (Node child : children)
            panel.add(builder.ParseXMLNode(child));
    }
}
