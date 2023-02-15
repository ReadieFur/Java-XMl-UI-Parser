package xml_ui.controls;

import java.awt.Color;

import javax.swing.JTextField;

import xml_ui.attributes.SetterAttribute;

public class TextBox extends JTextField
{
    public TextBox()
    {
        super();
    }

    @SetterAttribute("Background")
    public void SetBackground(String colour)
    {
        setBackground(Color.decode(colour));
    }

    @SetterAttribute("Foreground")
    public void SetForeground(String colour)
    {
        setForeground(Color.decode(colour));
    }

    @SetterAttribute("Value")
    public void SetValue(String value)
    {
        setText(value);
    }

    @SetterAttribute("Enabled")
    public void SetEnabled(String value)
    {
        setEnabled(Boolean.parseBoolean(value));
    }
}
