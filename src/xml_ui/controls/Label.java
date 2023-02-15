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
}
