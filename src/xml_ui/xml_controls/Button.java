package xml_ui.xml_controls;

import javax.swing.JButton;

import xml_ui.Helpers;
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

    @SetterAttribute("Enabled")
    public static void SetEnabled(JButton button, String enabled)
    {
        button.setEnabled(Boolean.parseBoolean(enabled));
    }

    @SetterAttribute("ToolTip")
    public static void SetToolTip(JButton button, String toolTip)
    {
        button.setToolTipText(toolTip);
    }

    @SetterAttribute("Background")
    public static void SetBackground(JButton button, String background)
    {
        button.setBackground(Helpers.ParseColour(background));
    }

    @SetterAttribute("Foreground")
    public static void SetForeground(JButton button, String foreground)
    {
        button.setForeground(Helpers.ParseColour(foreground));
    }
}
