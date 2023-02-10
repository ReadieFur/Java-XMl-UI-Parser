package xml_ui.attributes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Internal attribute used to indicate that a method is for subscribing events.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EventAttribute
{
    String value();
}
