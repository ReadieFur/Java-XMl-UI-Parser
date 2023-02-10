package xml_ui.xml_controls;

import javax.swing.JTextArea;

import xml_ui.Helpers;
import xml_ui.UIBuilder;
import xml_ui.attributes.CreatorAttribute;
import xml_ui.attributes.SetterAttribute;

public class TextBlock
{
    private TextBlock(){}

    @CreatorAttribute
    public static JTextArea Create()
    {
        return new JTextArea();
    }

    @SetterAttribute("Background")
    public static void SetBackground(JTextArea textBlock, String color)
    {
        textBlock.setBackground(Helpers.ParseColour(color));
    }

    @SetterAttribute("Foreground")
    public static void SetForeground(JTextArea textBlock, String color)
    {
        textBlock.setForeground(Helpers.ParseColour(color));
    }

    @SetterAttribute("Content")
    public static void SetContent(JTextArea textBlock, String value)
    {
        textBlock.setText(value);
    }

    @SetterAttribute("Enabled")
    public static void SetEnabled(JTextArea textBlock, String value)
    {
        textBlock.setEnabled(Boolean.parseBoolean(value));
    }

    @SetterAttribute("Wrap")
    public static void SetWrap(JTextArea textBlock, String value)
    {
        textBlock.setLineWrap(Boolean.parseBoolean(value));
    }

    @SetterAttribute("IsReadOnly")
    public static void SetIsReadOnly(JTextArea textBlock, String value)
    {
        textBlock.setEditable(!Boolean.parseBoolean(value));
    }
}
