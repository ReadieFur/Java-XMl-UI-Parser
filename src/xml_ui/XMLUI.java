package xml_ui;
import java.awt.Component;
import java.io.*;
import java.net.URL;
import java.util.HashMap;

import javax.naming.NamingException;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import xml_ui.xml_controls.Base;

public abstract class XMLUI
{
    //TODO: Resource binding will be implemented later.
    // protected final HashMap<String, String> resources = new HashMap<String, String>();

    protected Component rootElement;

    protected XMLUI() throws NamingException, ParserConfigurationException, SAXException, IOException
    {
        //Gets the intermediate path to the class file which we will use to load the XML file.
        String className = this.getClass().getName();
        URL xmlPath = this.getClass().getResource(className.substring(0, className.length()) + ".xml");
        InputStream xmlFileStream = xmlPath.openStream();

        try
        {
            //Setup the XML parser.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Parse the XML file.
            Document document = builder.parse(xmlFileStream);

            //Get the root element.
            Element root = document.getDocumentElement();

            //Following a similar pattern to XAML (C#'s XML-based UI markup language), we will check if the root element contains a "Resources" element.
            //If it does, we will parse the resources and store them in a dictionary.
            // NodeList resources = root.getElementsByTagName("Resources");
            // if (resources.getLength() > 0)
            // {
            //     if (resources.getLength() > 1)
            //         throw new SAXException("Only one Resources element is allowed.");

            //     NodeList resourceList = resources.item(0).getChildNodes();
            //     for (int i = 0; i < resourceList.getLength(); i++)
            //     {
            //         Node resource = resourceList.item(i);
            //         if (resource.getNodeType() == Node.ELEMENT_NODE)
            //         {
            //         }
            //     }
            // }

            //Next we will build up the XML UI tree.
            rootElement = Base.BaseParseXMLNode(root);
            if (rootElement == null)
                throw new SAXException("The content element could not be parsed.");
        }
        finally
        {
            xmlFileStream.close();
        }
    }
}
