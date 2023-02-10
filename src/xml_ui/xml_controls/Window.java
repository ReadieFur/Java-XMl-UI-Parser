package xml_ui.xml_controls;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JFrame;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import xml_ui.Helpers;
import xml_ui.UIBuilder;
import xml_ui.XMLUI;
import xml_ui.attributes.ChildBuilderAttribute;
import xml_ui.attributes.CreatorAttribute;
import xml_ui.attributes.SetterAttribute;

public class Window
{
    private Window(){}

    @CreatorAttribute
    public static JFrame Create()
    {
        return new JFrame();
    }

    @SetterAttribute("Title")
    public static void SetTitle(JFrame frame, String title)
    {
        frame.setTitle(title);
    }

    @SetterAttribute("Width")
    public static void SetWidth(JFrame frame, String width)
    {
        frame.setSize(Integer.parseInt(width), frame.getHeight());
    }

    @SetterAttribute("Height")
    public static void SetHeight(JFrame frame, String height)
    {
        frame.setSize(frame.getWidth(), Integer.parseInt(height));
    }

    @SetterAttribute("Background")
    public static void SetBackground(JFrame frame, String background)
    {
        frame.getContentPane().setBackground(Helpers.ParseColour(background));
    }

    @ChildBuilderAttribute
    public static void AddChildren(JFrame frame, List<Node> children, XMLUI context)
        throws SAXException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DOMException
    {
        //A window can only have one child.
        if (children.size() > 1)
            throw new SAXException("A window can only have one child.");
        else if (children.isEmpty())
            return;

        //Add the child to the window.
        frame.add(UIBuilder.ParseXMLNode(children.get(0), context));
    }
}
