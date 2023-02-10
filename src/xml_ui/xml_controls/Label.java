package xml_ui.xml_controls;

import javax.swing.JLabel;

import xml_ui.attributes.CreatorAttribute;
import xml_ui.attributes.SetterAttribute;

public class Label
{
    private Label(){}

    @CreatorAttribute
    public static JLabel Create()
    {
        return new JLabel();
    }

    @SetterAttribute("Text")
    public static void SetText(JLabel label, String text)
    {
        label.setText(text);
    }

    @SetterAttribute("X")
    public static void SetX(JLabel label, String x)
    {
        label.setLocation(Integer.parseInt(x), label.getY());
    }

    @SetterAttribute("Y")
    public static void SetY(JLabel label, String y)
    {
        label.setLocation(label.getX(), Integer.parseInt(y));
    }
}
