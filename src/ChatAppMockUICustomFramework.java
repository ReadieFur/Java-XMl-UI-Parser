import java.io.IOException;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml_ui.Observable;
import xml_ui.attributes.BindingAttribute;
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

    public ChatAppMockUICustomFramework() throws IllegalArgumentException, IllegalAccessException, IOException, ParserConfigurationException, SAXException, InvalidXMLException
    {
        super();
        rootComponent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
