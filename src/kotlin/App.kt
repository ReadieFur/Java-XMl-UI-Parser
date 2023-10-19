import java.awt.Color
import javax.swing.JButton
import javax.swing.JFrame

fun main()
{
    // Test();
    CustomFramework()
}

private fun Test()
{
    val frame = JFrame("My First GUI")
    frame.contentPane.setBackground(Color.RED)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setSize(300, 300)
    val button = JButton("Press")
    // frame.getContentPane().add(button); // Adds Button to content pane of frame
    frame.isVisible = true
}

private fun CustomFramework()
{
    try
    {
        val ui = UI()
        ui.Show()
    } catch (ex: Exception)
    {
        ex.printStackTrace()
    }
}
