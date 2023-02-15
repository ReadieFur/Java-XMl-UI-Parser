package xml_ui.controls;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import xml_ui.attributes.SetterAttribute;
import xml_ui.exceptions.InvalidXMLException;

public class Label extends JLabel
{
    public Label()
    {
        super();
    }

    @SetterAttribute("Text")
    public void SetText(String text)
    {
        setText(text);
    }

    @SetterAttribute("Background")
    public void SetBackground(String colour)
    {
        setOpaque(true);
        setBackground(Color.decode(colour));
    }

    @SetterAttribute("Foreground")
    public void SetForeground(String colour)
    {
        setForeground(Color.decode(colour));
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
    public void SetSize(String size) throws InvalidXMLException
    {
        setFont(getFont().deriveFont(Float.parseFloat(size)));
    }

    @SetterAttribute("Font")
    public void SetFont(String font) throws InvalidXMLException
    {
        setFont(new Font(font, getFont().getStyle(), getFont().getSize()));
    }
}
