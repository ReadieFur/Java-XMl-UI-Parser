package xml_ui.attributes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to indicate the method used to create a control.
 * The method must be static and have no parameters.
 * The method must accept an array of Objects as a parameter.
*/
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatorAttribute {}
