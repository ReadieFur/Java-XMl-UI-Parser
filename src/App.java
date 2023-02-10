import javax.swing.*;

public class App
{
    public static void main(String[] args)
    {
        CustomFramework();
    }

    private static void Test()
    {
        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        JButton button = new JButton("Press");
        frame.getContentPane().add(button); // Adds Button to content pane of frame
        frame.setVisible(true);
    }

    private static void CustomFramework()
    {
        try
        {
            UI_xml ui = new UI_xml();
            ui.Show();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
