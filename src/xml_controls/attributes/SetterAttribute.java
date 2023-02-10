package xml_controls.attributes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SetterAttribute
{
    /**
     * The name of the XML property to set.
     */
    String value();
}
