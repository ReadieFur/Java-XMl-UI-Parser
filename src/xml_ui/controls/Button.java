package xml_ui.controls;

import java.awt.Color;
import java.awt.Font;
import java.util.function.Consumer;

import javax.swing.JButton;

import xml_ui.attributes.EventAttribute;
import xml_ui.attributes.SetterAttribute;
import xml_ui.exceptions.InvalidXMLException;

public class Button extends JButton
{
    public Button()
    {
        super();
    }

    @SetterAttribute("Content")
    public void SetText(String text)
    {
        setText(text);
    }

    @SetterAttribute("Enabled")
    public void SetEnabled(String enabled)
    {
        setEnabled(Boolean.parseBoolean(enabled));
    }

    @SetterAttribute("ToolTip")
    public void SetToolTip(String toolTip)
    {
        setToolTipText(toolTip);
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

    @EventAttribute("Click")
    public void SetClick(Consumer<Object[]> callback)
    {
        addActionListener(e -> callback.accept(new Object[]{}));
    }
}
