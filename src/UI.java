import java.util.List;

import javax.swing.JFrame;

import xml_ui.Observable;
import xml_ui.XMLUI;
import xml_ui.attributes.BindingAttribute;
import xml_ui.attributes.EventCallbackAttribute;

public class UI extends XMLUI
{
    protected JFrame rootElement;

    @BindingAttribute(DefaultValue = "#FF0000")
    private Observable<String> backgroundColour;

    public UI() throws Exception
    {
        super();
        this.rootElement = (JFrame)super.rootElement;

        rootElement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void Show()
    {
        rootElement.setVisible(true);

        new Thread(() ->
        {
            try { Thread.sleep(2500); }
            catch (InterruptedException e) {}
            backgroundColour.Set("#8400FF");
        }).start();
    }

    @EventCallbackAttribute
    private void ButtonClickEvent(List<Object> args)
    {
        System.out.println("Button clicked!");
    }
}
