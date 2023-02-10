package xml_ui.attributes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The method must be static and accept two parameters: Component and String, where the first parameter is the control to set the property on and the second parameter is the value of the property.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SetterAttribute
{
    /**
     * The name of the XML property to set.
     */
    String value();
}
