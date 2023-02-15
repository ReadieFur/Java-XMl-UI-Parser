package xml_ui.controls;

import java.util.function.Consumer;

import javax.swing.JCheckBox;

import xml_ui.attributes.EventAttribute;
import xml_ui.attributes.SetterAttribute;

public class CheckBox extends JCheckBox
{
    public CheckBox()
    {
        super();
    }

    @SetterAttribute("Text")
    public void SetText(String text)
    {
        setText(text);
    }

    @SetterAttribute("ToolTip")
    public void SetToolTip(String toolTip)
    {
        setToolTipText(toolTip);
    }

    @SetterAttribute("Checked")
    public void SetChecked(String checked)
    {
        setSelected(Boolean.parseBoolean(checked));
    }

    @EventAttribute("Click")
    public void SetClick(Consumer<Object[]> callback)
    {
        addActionListener(e -> callback.accept(new Object[]{}));
    }
}
