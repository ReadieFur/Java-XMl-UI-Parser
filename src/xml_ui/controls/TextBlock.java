package xml_ui.controls;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

import xml_ui.attributes.SetterAttribute;
import xml_ui.exceptions.InvalidXMLException;

public class TextBlock extends JTextArea
{
    public TextBlock()
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

    @SetterAttribute("Content")
    public void SetContent(String value)
    {
        //Replace all newline breaks with new lines.
        value = value.replace("\\n", "\n");
        setText(value);
    }

    @SetterAttribute("Enabled")
    public void SetEnabled(String value)
    {
        setEnabled(Boolean.parseBoolean(value));
    }

    @SetterAttribute("Wrap")
    public void SetWrap(String value)
    {
        setLineWrap(Boolean.parseBoolean(value));
    }

    @SetterAttribute("IsReadOnly")
    public void SetIsReadOnly(String value)
    {
        setEditable(!Boolean.parseBoolean(value));
    }
}
