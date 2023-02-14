import java.io.IOException;
import java.util.function.Consumer;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml_ui.Observable;
import xml_ui.attributes.BindingAttribute;
import xml_ui.attributes.EventCallbackAttribute;
import xml_ui.attributes.NamedComponentAttribute;
import xml_ui.controls.StackPanel;
import xml_ui.controls.Window;
import xml_ui.exceptions.InvalidXMLException;

public class ChatAppMockUICustomFramework extends Window
{
    @BindingAttribute(DefaultValue = "#FFFFFF")
    private Observable<String> backgroundColourPrimary;

    @BindingAttribute(DefaultValue = "#DADADA")
    private Observable<String> backgroundColourSecondary;

    @BindingAttribute(DefaultValue = "#BDBDBD")
    private Observable<String> backgroundColourTertiary;

    @BindingAttribute(DefaultValue = "#000000")
    private Observable<String> foregroundColour;

    // @BindingAttribute(DefaultValue = "10px")
    // private Observable<String> fontSize;

    @NamedComponentAttribute
    private JPanel chatBox;

    @NamedComponentAttribute
    private JScrollPane chatBoxScroller;

    @NamedComponentAttribute
    private JTextField inputBox;

    public ChatAppMockUICustomFramework() throws IllegalArgumentException, IllegalAccessException, IOException, ParserConfigurationException, SAXException, InvalidXMLException
    {
        super();
        rootComponent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @EventCallbackAttribute
    private void SendButton_OnClick(Object[] args)
    {
        //Get and reset the text input field.
        String message = inputBox.getText();
        inputBox.setText("");

        //If the message is empty, don't do anything.
        if (message.isEmpty())
            return;

        //Create the message box that will contain the input from before.
        JTextArea messageBox = new JTextArea(message);

        //Bind the message box background colour.
        Consumer<String> backgroundColourConsumer = (value) -> messageBox.setBackground(Color.decode(value));
        backgroundColourTertiary.AddListener(backgroundColourConsumer);
        backgroundColourConsumer.accept(backgroundColourTertiary.Get());

        //Add the message box to the chat box.
        //To ease the addition of this component, we can use the AddChild method from the StackPanel class.
        StackPanel.AddChild(chatBox, messageBox);

        //It is also important to refresh the scroll pane so that if needed, the scroll bar will be updated. Then also scroll to the bottom.
        chatBoxScroller.revalidate();
        chatBoxScroller.getVerticalScrollBar().setValue(chatBoxScroller.getVerticalScrollBar().getMaximum());

        //Set focus back onto the input box.
        inputBox.requestFocus();
    }
}
