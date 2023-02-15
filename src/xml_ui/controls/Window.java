package xml_ui.controls;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.w3c.dom.Node;

import xml_ui.ManualResetEvent;
import xml_ui.attributes.ChildBuilderAttribute;
import xml_ui.attributes.SetterAttribute;
import xml_ui.exceptions.InvalidXMLException;
import xml_ui.factory.UIBuilderFactory;
import xml_ui.interfaces.IRootComponent;

public class Window extends JFrame implements IRootComponent
{
    private Boolean addedChild = false;
    private ManualResetEvent dialogueResetEvent = new ManualResetEvent(false);

    public Window()
    {
        super();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //https://docs.oracle.com/javase/7/docs/api/java/awt/event/WindowListener.html
        addWindowListener(new WindowListener()
        {
            @Override
            public void windowClosed(WindowEvent e) { WindowClosed(e); }

            @Override
            public void windowClosing(WindowEvent e) { WindowClosing(e); }

            @Override
            public void windowOpened(WindowEvent e) { WindowOpened(e); }

            @Override
            public void windowIconified(WindowEvent e) { WindowIconified(e); }

            @Override
            public void windowDeiconified(WindowEvent e) { WindowDeiconified(e); }

            @Override
            public void windowActivated(WindowEvent e) { WindowActivated(e); }

            @Override
            public void windowDeactivated(WindowEvent e) { WindowDeactivated(e); }
        });
    }

    @SetterAttribute("Title")
    public void SetTitle(String title)
    {
        setTitle(title);
    }

    @SetterAttribute("Width")
    public void SetWidth(String width)
    {
        setSize(Integer.parseInt(width), getHeight());
    }

    @SetterAttribute("Height")
    public void SetHeight(String height)
    {
        setSize(getWidth(), Integer.parseInt(height));
    }

    @SetterAttribute("MinWidth")
    public void SetMinWidth(String minWidth)
    {
        setMinimumSize(new java.awt.Dimension(Integer.parseInt(minWidth), getMinimumSize().height));
    }

    @SetterAttribute("MinHeight")
    public void SetMinHeight(String minHeight)
    {
        setMinimumSize(new java.awt.Dimension(getMinimumSize().width, Integer.parseInt(minHeight)));
    }

    @SetterAttribute("MaxWidth")
    public void SetMaxWidth(String maxWidth)
    {
        setMaximumSize(new java.awt.Dimension(Integer.parseInt(maxWidth), getMaximumSize().height));
    }

    @SetterAttribute("MaxHeight")
    public void SetMaxHeight(String maxHeight)
    {
        setMaximumSize(new java.awt.Dimension(getMaximumSize().width, Integer.parseInt(maxHeight)));
    }

    @SetterAttribute("Resizable")
    public void SetResizable(String resizable)
    {
        setResizable(Boolean.parseBoolean(resizable));
    }

    @SetterAttribute("Background")
    public void SetBackground(String colour)
    {
        getContentPane().setBackground(Color.decode(colour));
    }

    @ChildBuilderAttribute
    public void AddChild(UIBuilderFactory builder, List<Node> children) throws InvalidXMLException
    {
        for (Node child : children)
        {
            //A window can only have one child.
            if (addedChild)
                throw new InvalidXMLException("A '" + Window.class.getSimpleName() + "' can only have one child.");

            //If the child is a resource dictionary, skip it.
            if (child.getNodeName().equals(Window.class.getSimpleName() + ".Resources"))
                continue;

            //Add the child to the window.
            add(builder.ParseXMLNode(child));
            addedChild = true;
        }
    }

    public void RemoveChild()
    {
        removeAll();
        addedChild = false;
    }

    /**
     * Shows the window and does not wait for it to be closed.
     */
    public void Show()
    {
        setVisible(true);
    }

    /**
     * Shows the window and waits for it to be closed.
     */
    public void ShowDialog()
    {
        setVisible(true);
        dialogueResetEvent.WaitOne();
    }

    protected void WindowClosed(WindowEvent e)
    {
        dialogueResetEvent.Set();
    }

    protected void WindowClosing(WindowEvent e) {}

    protected void WindowOpened(WindowEvent e) {}

    protected void WindowIconified(WindowEvent e) {}

    protected void WindowDeiconified(WindowEvent e) {}

    protected void WindowActivated(WindowEvent e) {}

    protected void WindowDeactivated(WindowEvent e) {}
}
