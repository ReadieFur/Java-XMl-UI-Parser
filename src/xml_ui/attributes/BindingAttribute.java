package xml_ui.attributes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to indicate a variable that should be bound to.
 * Must be attached to a {@link xml_ui.Observable} field.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BindingAttribute
{
    /**
     * The default/initial value.
     */
    String DefaultValue();

    /**
     * The direction of the binding.
     * 0 = Two-way
     * 1 = One-way to UI
     * 2 = One-way to code
     */
    //Currently only One-way to UI is supported.
    // int Direction() default 0;
}
