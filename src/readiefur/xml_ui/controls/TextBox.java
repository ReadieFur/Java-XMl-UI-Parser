package readiefur.xml_ui.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import readiefur.xml_ui.attributes.SetterAttribute;

public class TextBox extends JTextField
{
    public TextBox()
    {
        super();

        addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) { OnKeyTyped(e); }

            @Override
            public void keyPressed(KeyEvent e) { OnKeyPressed(e); }

            @Override
            public void keyReleased(KeyEvent e) { OnKeyReleased(e); }
        });
    }

    @SetterAttribute("Value")
    public void SetValue(String value)
    {
        setText(value);
    }

    @SetterAttribute("Enabled")
    public void SetEnabled(String value)
    {
        setEnabled(Boolean.parseBoolean(value));
    }

    protected void OnKeyTyped(KeyEvent e) {}

    protected void OnKeyPressed(KeyEvent e) {}

    protected void OnKeyReleased(KeyEvent e) {}
}
