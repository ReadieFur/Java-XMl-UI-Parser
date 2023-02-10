package xml_ui.xml_controls;

import javax.swing.JLabel;

import xml_ui.UIBuilder;
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

    @SetterAttribute("Background")
    public static void SetBackground(JLabel label, String background)
    {
        label.setOpaque(true);
        label.setBackground(UIBuilder.ParseColour(background));
    }

    @SetterAttribute("Foreground")
    public static void SetForeground(JLabel label, String foreground)
    {
        label.setForeground(UIBuilder.ParseColour(foreground));
    }
}
