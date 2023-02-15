package xml_ui.controls;

import javax.swing.JTextField;

import xml_ui.attributes.SetterAttribute;

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
