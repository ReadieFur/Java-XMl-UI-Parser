import readiefur.xml_ui.Observable
import readiefur.xml_ui.XMLUI
import readiefur.xml_ui.attributes.BindingAttribute
import readiefur.xml_ui.attributes.EventCallbackAttribute
import readiefur.xml_ui.controls.Window

class UI : XMLUI<Window?>()
{
    @BindingAttribute(DefaultValue = "#FF0000")
    private val backgroundColour: Observable<String>? = null
    fun Show()
    {
        rootComponent!!.Show()
        Thread {
            try
            {
                Thread.sleep(2500)
            } catch (e: InterruptedException)
            {
            }
            backgroundColour!!.Set("#8400FF")
        }.start()
    }

    @EventCallbackAttribute
    private fun Button_Click(args: Array<Any>)
    {
        println("Button clicked!")
    }
}
