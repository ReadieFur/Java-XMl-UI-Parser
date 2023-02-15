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

    @SetterAttribute("Weight")
    public void SetWeight(String weight) throws InvalidXMLException
    {
        switch (weight)
        {
            case "BoldItalic":
                setFont(getFont().deriveFont(getFont().getStyle() | Font.BOLD | Font.ITALIC));
                break;
            case "Bold":
                setFont(getFont().deriveFont(getFont().getStyle() | Font.BOLD));
                break;
            case "Italic":
                setFont(getFont().deriveFont(getFont().getStyle() | Font.ITALIC));
                break;
            case "Normal":
                setFont(getFont().deriveFont(getFont().getStyle() & ~Font.BOLD & ~Font.ITALIC));
                break;
            default:
                throw new InvalidXMLException("Invalid weight '" + weight + "'.");
        }
    }

    @SetterAttribute("Size")
    public void SetSize(String size)
    {
        setFont(getFont().deriveFont(Float.parseFloat(size)));
    }

    @SetterAttribute("Font")
    public void SetFont(String font)
    {
        setFont(new Font(font, getFont().getStyle(), getFont().getSize()));
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
