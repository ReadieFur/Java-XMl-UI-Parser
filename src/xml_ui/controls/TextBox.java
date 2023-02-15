package xml_ui.controls;

import java.awt.Font;

import javax.swing.JTextField;

import xml_ui.attributes.SetterAttribute;
import xml_ui.exceptions.InvalidXMLException;

public class TextBox extends JTextField
{
    public TextBox()
    {
        super();
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
