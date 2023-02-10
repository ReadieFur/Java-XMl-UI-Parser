import javax.swing.JFrame;

public class UI_xml extends Form
{
    protected JFrame rootElement;

    public UI_xml() throws Exception
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
