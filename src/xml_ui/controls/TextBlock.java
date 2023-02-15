package xml_ui.controls;

import java.awt.Color;

import javax.swing.JTextArea;

import xml_ui.attributes.SetterAttribute;

public class TextBlock extends JTextArea
{
    public TextBlock()
    {
        super();
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
