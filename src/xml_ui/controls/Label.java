package xml_ui.controls;

import java.awt.Color;

import javax.swing.JLabel;

import xml_ui.attributes.SetterAttribute;

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

    @SetterAttribute("Background")
    public void SetBackground(String colour)
    {
        setOpaque(true);
        setBackground(Color.decode(colour));
    }

    @SetterAttribute("Foreground")
    public void SetForeground(String colour)
    {
        setForeground(Color.decode(colour));
    }
}
