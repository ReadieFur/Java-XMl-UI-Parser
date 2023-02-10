package xml_ui;

import java.awt.Component;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.naming.NamingException;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import xml_ui.attributes.BindingAttribute;

public abstract class XMLUI
{
    protected Component rootElement;

    protected XMLUI() throws NamingException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DOMException
    {
        //Gets the intermediate path to the class file which we will use to load the XML file.
        String className = this.getClass().getName();
        URL xmlPath = this.getClass().getResource(className.substring(0, className.length()) + ".xml");
        InputStream xmlFileStream = xmlPath.openStream();

        /*For any bindable attributes, we need to assign them now.
         *This is because in Java, fields get initialized after the constructor is called (C# is the opposite).*/
        for (Field field : this.getClass().getDeclaredFields())
        {
            BindingAttribute bindingAttribute = field.getAnnotation(BindingAttribute.class);
            if (bindingAttribute == null)
                continue;

            //Make sure that the field is an Observable.
            if (field.getType() != Observable.class)
                // || !field.getGenericType().getTypeName().equals(Observable.class.getCanonicalName() + "<" + String.class.getCanonicalName() + ">"))
                throw new IllegalArgumentException(
                    "Binding fields must be of type Observable<String>. (" + this.getClass().getSimpleName() + "::" + field.getName() + ")");

            field.setAccessible(true);
            field.set(this, new Observable<String>(bindingAttribute.DefaultValue()));
        }

        try
        {
            //Setup the XML parser.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Parse the XML file.
            Document document = builder.parse(xmlFileStream);

            //Get the root element.
            Element root = document.getDocumentElement();

            //Next we will build up the XML UI tree.
            rootElement = UIBuilder.ParseXMLNode(root, this);
            if (rootElement == null)
                throw new SAXException("The content element could not be parsed.");
        }
        finally
        {
            xmlFileStream.close();
        }
    }
}
