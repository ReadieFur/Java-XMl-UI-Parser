package readiefur.xml_ui.attributes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to indicate that a method is for subscribing events.
 * <br><br/>
 * <b>Constraints:</b>
 * <ul>
 *  <li>Can only be attached to a {@code method}</li>
 *  <li>Must be {@code public}</li>
 *  <li>Must take one parameter:
 *  <ul>
 *      <li>Callback: {@link java.util.function.Consumer}<{@link java.lang.Object}[]></li>
 *  </ul>
 *  </li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.METHOD)
public @interface EventAttribute
{
    /**
     * The name of the XML property.
     */
    String value();
}
