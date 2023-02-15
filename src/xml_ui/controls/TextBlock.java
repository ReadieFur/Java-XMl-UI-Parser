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
