package xml_ui;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import xml_ui.attributes.BindingAttribute;
import xml_ui.attributes.ChildBuilderAttribute;
import xml_ui.attributes.CreatorAttribute;
import xml_ui.attributes.EventAttribute;
import xml_ui.attributes.EventCallbackAttribute;
import xml_ui.attributes.SetterAttribute;

import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class UIBuilder
{
    private static final String XML_CONTROLS_NAMESPACE = UIBuilder.class.getCanonicalName().substring(0,
        UIBuilder.class.getCanonicalName().length() - UIBuilder.class.getSimpleName().length() - 1) + ".xml_controls";

    private UIBuilder(){}

    //TODO: Optimize this method AND CLEAN IT UP! (Move to an instanced class).
    //This is the base method for parsing XML nodes. It will try to find a corresponding class to create a Component from.
    public static Component ParseXMLNode(Node xmlNode, XMLUI context)
        throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DOMException, SAXException
    {
        //Check if an element exists for the control type.
        Class<?> controlType = Class.forName(XML_CONTROLS_NAMESPACE + "." + xmlNode.getNodeName());

        //Get the creator method.
        Method creatorMethod = null;
        int creatorMethodCount = 0;
        for (Method method : controlType.getMethods())
        {
            if (method.getAnnotation(CreatorAttribute.class) == null)
                continue;

            //Make sure that the method accepts no parameters.
            if (method.getParameterCount() != 0)
                throw new IllegalArgumentException(
                    "Creator methods must accept no parameters. (" + controlType.getSimpleName() + "::" + method.getName() + ")");

            if (++creatorMethodCount > 1)
                throw new IllegalArgumentException(
                    "Only one creator method is allowed per control type. (" + controlType.getSimpleName() + "::" + method.getName() + ")");

            creatorMethod = method;
        }
        if (creatorMethod == null)
            throw new IllegalArgumentException("No creator method was found for the control type '" + controlType.getSimpleName() + "'.");

        //Create the control.
        Component control = (Component)creatorMethod.invoke(null);

        //Get the bindable members.
        HashMap<String, Observable<String>> bindableMembers = new HashMap<>();
        for (Field field : context.getClass().getDeclaredFields())
        {
            BindingAttribute bindingAttribute = field.getAnnotation(BindingAttribute.class);
            if (bindingAttribute == null)
                continue;

            //Make sure that the field is an Observable.
            if (field.getType() != Observable.class)
                // || !field.getGenericType().getTypeName().equals(Observable.class.getCanonicalName() + "<" + String.class.getCanonicalName() + ">"))
                throw new IllegalArgumentException(
                    "Binding fields must be of type Observable<String>. (" + context.getClass().getSimpleName() + "::" + field.getName() + ")");

            field.setAccessible(true);
            bindableMembers.put(field.getName(), (Observable<String>)field.get(context));
        }

        //Get event callback methods from the context.
        List<Method> eventCallbackMethods = new ArrayList<>();
        for (Method method : context.getClass().getDeclaredMethods())
        {
            EventCallbackAttribute eventCallbackAttribute = method.getAnnotation(EventCallbackAttribute.class);
            if (eventCallbackAttribute == null)
                continue;

            //Make sure that the method accepts (List<Object>) as the parameter.
            if (method.getParameterCount() != 1
                || method.getParameterTypes()[0] != List.class)
                // || method.getParameterTypes()[0].getTypeParameters()[0].getGenericDeclaration() != Object.class)
                throw new IllegalArgumentException(
                    "Event callback methods must accept (List<Object>) as the parameters. (" + context.getClass().getSimpleName() + "::" + method.getName() + ")");

            method.setAccessible(true);
            eventCallbackMethods.add(method);
        }

        //Get the class attribute setter methods.
        List<Method> attributeSetters = new ArrayList<>();
        for (Method method : controlType.getMethods())
        {
            SetterAttribute setterAttribute = method.getAnnotation(SetterAttribute.class);
            if (setterAttribute == null)
                continue;

            //Make sure that the method accepts (Component, String) as parameters.
            if (method.getParameterCount() != 2
                || method.getParameterTypes()[0] != control.getClass()
                || method.getParameterTypes()[1] != String.class)
                throw new IllegalArgumentException(
                    "Setter methods must accept (Component, String) as the parameters. (" + controlType.getSimpleName() + "::" + method.getName() + ")");

            attributeSetters.add(method);
        }

        //For each attribute on the XML node, we will try to find a corresponding class attribute on the control type.
        for (int i = 0; i < xmlNode.getAttributes().getLength(); i++)
        {
            //Get the XML attribute information.
            Node xmlAttribute = xmlNode.getAttributes().item(i);
            String xmlAttributeName = xmlAttribute.getNodeName();

            //If the attribute is a "Name" attribute, we will set the name of the control as this is a global directive (i.e. it is available to on all controls).
            if (xmlAttributeName.equals("Name"))
            {
                control.setName(xmlAttribute.getNodeValue());
                continue;
            }

            //#region SetterAttribute
            //Try to find a setter method for the attribute.
            Method attributeSetter = null;
            for (Method method : attributeSetters)
            {
                SetterAttribute setterAttribute = method.getAnnotation(SetterAttribute.class);

                if (setterAttribute.value().equals(xmlAttributeName))
                {
                    attributeSetter = method;
                    break;
                }
            }
            if (attributeSetter != null)
            {
                //Check if we should bind to a value or just set the value.
                if (xmlAttribute.getNodeValue().startsWith("{Binding ") && xmlAttribute.getNodeValue().endsWith("}"))
                {
                    //Look for a bindable member within the context.
                    String bindableMemberName = xmlAttribute.getNodeValue().substring(9, xmlAttribute.getNodeValue().length() - 1);
                    Field bindableMember = null;
                    for (Field field : context.getClass().getDeclaredFields())
                    {
                        if (field.getName().equals(bindableMemberName))
                        {
                            bindableMember = field;
                            break;
                        }
                    }
                    if (bindableMember == null)
                        throw new SAXException(
                            "No bindable member called '" + bindableMemberName + "' was found on the context '" + context.getClass().getSimpleName() + "'.");

                    //This value won't be null as we will have obtained it earlier.
                    Observable<String> bindableMemberValue = bindableMembers.get(bindableMemberName);

                    //Invoke the setter with the initial value.
                    attributeSetter.invoke(null, control, bindableMemberValue.Get());

                    //Add a listener to the bindable member.
                    final Method attributeSetterFinal = attributeSetter;
                    bindableMemberValue.AddListener(value ->
                    {
                        try { attributeSetterFinal.invoke(null, control, value); }
                        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                        {
                            //TODO: Handle this exception.
                            e.printStackTrace();
                        }
                    });
                }
                else
                {
                    //Invoke the setter method.
                    attributeSetter.invoke(null, control, xmlAttribute.getNodeValue());
                }

                continue;
            }
            //#endregion

            //#region EventAttribute
            //Try to find an event handler for the attribute.
            Method eventHandler = null;
            for (Method method : controlType.getMethods())
            {
                EventAttribute eventAttribute = method.getAnnotation(EventAttribute.class);
                if (eventAttribute == null)
                    continue;

                if (eventAttribute.value().equals(xmlAttributeName))
                {
                    eventHandler = method;
                    break;
                }
            }
            if (eventHandler != null)
            {
                //Find a matching event callback method.
                for (Method method : eventCallbackMethods)
                {
                    if (method.getName().equals(xmlAttribute.getNodeValue()))
                    {
                        //Wrap the callback method in a Consumer<List<Object>> to be passed to the eventHandler.
                        Consumer<List<Object>> callback = (List<Object> args) ->
                        {
                            try { method.invoke(context, args); }
                            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                            {
                                //TODO: Handle this exception.
                                e.printStackTrace();
                            }
                        };
                        eventHandler.invoke(null, control, callback);
                    }
                }
            }
            //#endregion
        }

        //Parse the child nodes (if the control type has a child builder).
        List<Node> children = Helpers.GetElementNodes(xmlNode);
        if (!children.isEmpty())
        {
            Method childBuilder = null;
            int childBuilderCount = 0;
            for (Method method : controlType.getMethods())
            {
                if (method.getAnnotation(ChildBuilderAttribute.class) == null)
                    continue;

                if (method.getParameterCount() != 3
                    || method.getParameterTypes()[0] != control.getClass()
                    || method.getParameterTypes()[1] != List.class
                    // || method.getParameterTypes()[1].getTypeParameters()[0].getGenericDeclaration() != Node.class
                    || method.getParameterTypes()[2] != XMLUI.class)
                    throw new IllegalArgumentException("Child builder methods must accept (Control, List<Node>, XMLUI) as the parameters." +
                        " (" + controlType.getSimpleName() + "::" + method.getName() + ")");

                if (++childBuilderCount > 1)
                    throw new IllegalArgumentException(
                        "Only one child builder method is allowed per control type. (" + controlType.getSimpleName() + ")");

                childBuilder = method;
            }
            if (childBuilder == null)
                throw new SAXException("The control " + xmlNode.getNodeName() + " cannot have child nodes.");

            //Invoke the child builder method.
            childBuilder.invoke(null, control, children, context);
        }

        return control;
    }
}
