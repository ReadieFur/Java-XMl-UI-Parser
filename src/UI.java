import readiefur.xml_ui.Observable;
import readiefur.xml_ui.XMLUI;
import readiefur.xml_ui.attributes.BindingAttribute;
import readiefur.xml_ui.attributes.EventCallbackAttribute;
import readiefur.xml_ui.controls.Window;

public class UI extends XMLUI<Window>
{
    @BindingAttribute(DefaultValue = "#FF0000")
    private Observable<String> backgroundColour;

    public UI() throws Exception
    {
        super();
    }

    public void Show()
    {
        rootComponent.Show();
        new Thread(() ->
        {
            try { Thread.sleep(2500); }
            catch (InterruptedException e) {}
            backgroundColour.Set("#8400FF");
        }).start();
    }

    @EventCallbackAttribute
    private void Button_Click(Object[] args)
    {
        System.out.println("Button clicked!");
    }
}
