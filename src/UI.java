import javax.swing.JFrame;

import xml_ui.XMLUI;

public class UI extends XMLUI
{
    protected JFrame rootElement;

    public UI() throws Exception
    {
        super();
        this.rootElement = (JFrame)super.rootElement;

        rootElement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void Show()
    {
        rootElement.setVisible(true);
    }
}
