package xml_ui.xml_controls;

import javax.swing.JTextField;

import xml_ui.Helpers;
import xml_ui.UIBuilder;
import xml_ui.attributes.CreatorAttribute;
import xml_ui.attributes.SetterAttribute;

public class TextBox
{
    private TextBox(){}

    @CreatorAttribute
    public static JTextField Create()
    {
        return new JTextField();
    }

    @SetterAttribute("Background")
    public static void SetBackground(JTextField textBox, String color)
    {
        textBox.setBackground(Helpers.ParseColour(color));
    }

    @SetterAttribute("Foreground")
    public static void SetForeground(JTextField textBox, String color)
    {
        textBox.setForeground(Helpers.ParseColour(color));
    }

    @SetterAttribute("Value")
    public static void SetValue(JTextField textBox, String value)
    {
        textBox.setText(value);
    }

    @SetterAttribute("Enabled")
    public static void SetEnabled(JTextField textBox, String value)
    {
        textBox.setEnabled(Boolean.parseBoolean(value));
    }
}
