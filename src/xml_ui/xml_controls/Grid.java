package xml_ui.xml_controls;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import xml_ui.UIBuilder;
import xml_ui.attributes.ChildBuilderAttribute;
import xml_ui.attributes.CreatorAttribute;
import xml_ui.attributes.SetterAttribute;

public class Grid
{
    private static final String NOT_GRID_BAG_LAYOUT_MESSAGE = "The layout of the container is not a GridBagLayout.";

    private Grid(){}

    @CreatorAttribute
    public static Container Create()
    {
        Container container = new Container();
        container.setLayout(new GridBagLayout());
        return container;
    }

    @SetterAttribute("Rows")
    public static void SetRows(Container container, String rows)
    {
        LayoutManager layout = container.getLayout();
        if (!(layout instanceof GridBagLayout))
            throw new IllegalArgumentException(NOT_GRID_BAG_LAYOUT_MESSAGE);
        GridBagLayout gridBagLayout = (GridBagLayout)layout;

        //Returns a copy of the existing constraints.
        GridBagConstraints constraints = gridBagLayout.getConstraints(container);

        constraints.gridy = Integer.parseInt(rows);

        gridBagLayout.setConstraints(container, constraints);
    }

    @SetterAttribute("Columns")
    public static void SetColumns(Container container, String columns)
    {
        LayoutManager layout = container.getLayout();
        if (!(layout instanceof GridBagLayout))
            throw new IllegalArgumentException(NOT_GRID_BAG_LAYOUT_MESSAGE);
        GridBagLayout gridBagLayout = (GridBagLayout)layout;

        //Returns a copy of the existing constraints.
        GridBagConstraints constraints = gridBagLayout.getConstraints(container);

        constraints.gridx = Integer.parseInt(columns);

        gridBagLayout.setConstraints(container, constraints);
    }

    @SetterAttribute("Width")
    public static void SetWidth(Container container, String width)
    {
        LayoutManager layout = container.getLayout();
        if (!(layout instanceof GridBagLayout))
            throw new IllegalArgumentException(NOT_GRID_BAG_LAYOUT_MESSAGE);
        GridBagLayout gridBagLayout = (GridBagLayout)layout;

        //Returns a copy of the existing constraints.
        GridBagConstraints constraints = gridBagLayout.getConstraints(container);

        constraints.gridwidth = Integer.parseInt(width);

        gridBagLayout.setConstraints(container, constraints);
    }

    @SetterAttribute("Height")
    public static void SetHeight(Container container, String height)
    {
        LayoutManager layout = container.getLayout();
        if (!(layout instanceof GridBagLayout))
            throw new IllegalArgumentException(NOT_GRID_BAG_LAYOUT_MESSAGE);
        GridBagLayout gridBagLayout = (GridBagLayout)layout;

        //Returns a copy of the existing constraints.
        GridBagConstraints constraints = gridBagLayout.getConstraints(container);

        constraints.gridheight = Integer.parseInt(height);

        gridBagLayout.setConstraints(container, constraints);
    }

    @ChildBuilderAttribute
    public static void AddChildren(Container container, List<Node> children)
        throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DOMException, SAXException
    {
        LayoutManager layout = container.getLayout();
        if (!(layout instanceof GridBagLayout))
            throw new IllegalArgumentException(NOT_GRID_BAG_LAYOUT_MESSAGE);
        GridBagLayout gridBagLayout = (GridBagLayout)layout;

        GridBagConstraints baseConstraints = gridBagLayout.getConstraints(container);

        for (Node child : children)
        {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;

            //Get the desired constraints for the child.
            if (child.hasAttributes())
            {
                Node rows = child.getAttributes().getNamedItem("Row");
                if (rows != null)
                {
                    int row = Integer.parseInt(rows.getNodeValue());
                    if (row < 0)
                        constraints.gridy = 0;
                    else if (row > baseConstraints.gridy)
                        constraints.gridy = baseConstraints.gridy;
                    else
                        constraints.gridy = row;
                }

                Node columns = child.getAttributes().getNamedItem("Column");
                if (columns != null)
                {
                    int column = Integer.parseInt(columns.getNodeValue());
                    if (column < 0)
                        constraints.gridx = 0;
                    else if (column > baseConstraints.gridx)
                        constraints.gridx = baseConstraints.gridx;
                    else
                        constraints.gridx = column;
                }

                Node rowSpan = child.getAttributes().getNamedItem("RowSpan");
                if (rowSpan != null)
                {
                    int span = Integer.parseInt(rowSpan.getNodeValue()) + 1;
                    if (span < 1)
                        constraints.gridheight = 1;
                    else if (span > baseConstraints.gridy)
                        constraints.gridheight = baseConstraints.gridy;
                    else
                        constraints.gridheight = span;
                }

                Node columnSpan = child.getAttributes().getNamedItem("ColumnSpan");
                if (columnSpan != null)
                {
                    int span = Integer.parseInt(columnSpan.getNodeValue()) + 1;
                    if (span < 1)
                        constraints.gridwidth = 1;
                    else if (span > baseConstraints.gridx)
                        constraints.gridwidth = baseConstraints.gridx;
                    else
                        constraints.gridwidth = span;
                }
            }

            container.add(UIBuilder.ParseXMLNode(child), constraints);
        }
    }
}
