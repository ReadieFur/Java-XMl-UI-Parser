package xml_ui.controls;

import java.awt.Color;
import java.util.function.Consumer;

import javax.swing.JButton;

import xml_ui.attributes.EventAttribute;
import xml_ui.attributes.SetterAttribute;

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

    @SetterAttribute("Background")
    public void SetBackground(String colour)
    {
        setBackground(Color.decode(colour));
    }

    @SetterAttribute("Foreground")
    public void SetForeground(String colour)
    {
        setForeground(Color.decode(colour));
    }

    @EventAttribute("Click")
    public void SetClick(Consumer<Object[]> callback)
    {
        addActionListener(e -> callback.accept(new Object[]{}));
    }
}
