package xml_ui.attributes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to indicate the child builder for a control.
 * The method must be static and have two parameters: Control, List<Node>, where control is the element to add the children to and the list of child nodes.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ChildBuilderAttribute {}
