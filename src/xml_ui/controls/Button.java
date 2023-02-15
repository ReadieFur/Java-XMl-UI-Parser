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

    @EventAttribute("Click")
    public void SetClick(Consumer<Object[]> callback)
    {
        addActionListener(e -> callback.accept(new Object[]{}));
    }
}
