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

    /**
     * If the element is inside of a grid then the default alignment will be the HorizontalAlignment of the grid.
     */
    @SetterAttribute("Alignment")
    public void SetAlignment(String alignment) throws InvalidXMLException
    {
        switch (alignment)
        {
            case "Left":
                setHorizontalAlignment(LEFT);
                break;
            case "Center":
                setHorizontalAlignment(CENTER);
                break;
            case "Right":
                setHorizontalAlignment(RIGHT);
                break;
            default:
                throw new InvalidXMLException("Invalid alignment '" + alignment + "'.");
        }
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
}
