package xml_ui.xml_controls;

import javax.swing.JButton;

import xml_ui.UIBuilder;
import xml_ui.attributes.CreatorAttribute;
import xml_ui.attributes.SetterAttribute;

public class Button
{
    private Button(){}

    @CreatorAttribute
    public static JButton Create()
    {
        return new JButton();
    }

    @SetterAttribute("Content")
    public static void SetText(JButton button, String text)
    {
        button.setText(text);
    }

    @SetterAttribute("Width")
    public static void SetWidth(JButton button, String width)
    {
        button.setSize(Integer.parseInt(width), button.getHeight());
    }

    @SetterAttribute("Height")
    public static void SetHeight(JButton button, String height)
    {
        button.setSize(button.getWidth(), Integer.parseInt(height));
    }

    @SetterAttribute("X")
    public static void SetX(JButton button, String x)
    {
        button.setLocation(Integer.parseInt(x), button.getY());
    }

    @SetterAttribute("Y")
    public static void SetY(JButton button, String y)
    {
        button.setLocation(button.getX(), Integer.parseInt(y));
    }

    @SetterAttribute("Enabled")
    public static void SetEnabled(JButton button, String enabled)
    {
        button.setEnabled(Boolean.parseBoolean(enabled));
    }

    @SetterAttribute("Visible")
    public static void SetVisible(JButton button, String visible)
    {
        button.setVisible(Boolean.parseBoolean(visible));
    }

    @SetterAttribute("ToolTip")
    public static void SetToolTip(JButton button, String toolTip)
    {
        button.setToolTipText(toolTip);
    }

    @SetterAttribute("Background")
    public static void SetBackground(JButton button, String background)
    {
        button.setBackground(UIBuilder.ParseColour(background));
    }

    @SetterAttribute("Foreground")
    public static void SetForeground(JButton button, String foreground)
    {
        button.setForeground(UIBuilder.ParseColour(foreground));
    }
}
