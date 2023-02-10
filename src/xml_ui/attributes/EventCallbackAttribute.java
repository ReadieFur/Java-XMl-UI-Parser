package xml_ui.attributes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to indicate a method that should be called when an event occurs.
 * The method must accept a list of objects as a parameter.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EventCallbackAttribute {}
