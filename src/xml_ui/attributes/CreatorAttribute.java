package xml_ui.attributes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to indicate the method used to create a control.
 * The method must be static and have no parameters.
*/
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatorAttribute {}
