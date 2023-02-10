package xml_ui.xml_controls;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import xml_ui.Pair;
import xml_ui.UIBuilder;
import xml_ui.attributes.ChildBuilderAttribute;
import xml_ui.attributes.CreatorAttribute;
import xml_ui.attributes.SetterAttribute;

public class Grid
{
    private Grid(){}

    @CreatorAttribute
    public static Container Create()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        return panel;
    }

    @SetterAttribute("Background")
    public static void SetBackground(JPanel panel, String background)
    {
        panel.setOpaque(true);
        panel.setBackground(UIBuilder.ParseColour(background));
    }

    @ChildBuilderAttribute
    public static void AddChildren(JPanel panel, List<Node> children)
        throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DOMException, SAXException
    {
        //#region Get the grid property nodes and child nodes
        List<Pair<Integer, Float>> rowDefinitions = new ArrayList<>();
        List<Pair<Integer, Float>> columnDefinitions = new ArrayList<>();
        List<Node> childrenToAdd = new ArrayList<>();

        for (Node child : children)
        {
            if (child.getNodeName().equals("Grid.RowDefinitions"))
            {
                //I would normally make these one liners without braces but in this instance I have kept some to improve readability.
                for (Node node : UIBuilder.GetElementNodes(child))
                    rowDefinitions.add(GetWeightValue(node, "RowDefinition", "Height"));
            }
            else if (child.getNodeName().equals("Grid.ColumnDefinitions"))
            {
                for (Node columnDefinition : UIBuilder.GetElementNodes(child))
                    columnDefinitions.add(GetWeightValue(columnDefinition, "ColumnDefinition", "Width"));
            }
            else
            {
                childrenToAdd.add(child);
            }
        }

        //Correct the row and column definitions
        //Values are passed by reference so this operation is fine.
        CorrectAutoWeights(rowDefinitions);
        CorrectAutoWeights(columnDefinitions);
        //#endregion

        //#region Add the children to the grid
        for (Node child : childrenToAdd)
        {
            //Get/set the desired constraints for the child.
            GridBagConstraints constraints = new GridBagConstraints();

            constraints.anchor = GetAlignment(child);

            if (child.hasAttributes())
            {
                Node rowAttribute = child.getAttributes().getNamedItem("Grid.Row");
                if (rowAttribute != null)
                {
                    //Parse the row attribute and make sure it is in range.
                    int row = Integer.parseInt(rowAttribute.getNodeValue());
                    if (row < 0)
                        row = 0;
                    else if (row >= rowDefinitions.size())
                        row = rowDefinitions.size() - 1;

                    //Set the row.
                    constraints.gridy = row;

                    //Set the row weight.
                    Pair<Integer, Float> rowWeight = rowDefinitions.get(row);
                    switch (rowWeight.Item1)
                    {
                        case 1: //Pixels.
                            constraints.ipady = rowWeight.Item2.intValue();
                            break;
                        case 2: //Percentage.
                            constraints.weighty = rowWeight.Item2;
                            break;
                        default: //Shouldn't be reached.
                            break;
                    }
                }

                Node columnAttribute = child.getAttributes().getNamedItem("Grid.Column");
                if (columnAttribute != null)
                {
                    //Parse the column attribute and make sure it is in range.
                    int column = Integer.parseInt(columnAttribute.getNodeValue());
                    if (column < 0)
                        column = 0;
                    else if (column >= columnDefinitions.size())
                        column = columnDefinitions.size() - 1;

                    //Set the column.
                    constraints.gridx = column;

                    //Set the column weight.
                    Pair<Integer, Float> columnWeight = columnDefinitions.get(column);
                    switch (columnWeight.Item1)
                    {
                        case 1: //Pixels.
                            constraints.ipadx = columnWeight.Item2.intValue();
                            break;
                        case 2: //Percentage.
                            constraints.weightx = columnWeight.Item2;
                            break;
                        default: //Shouldn't be reached.
                            break;
                    }
                }

                Node rowSpanAttribute = child.getAttributes().getNamedItem("Grid.RowSpan");
                if (rowSpanAttribute != null)
                {
                    //Parse the row span attribute and make sure it is in range.
                    int rowSpan = Integer.parseInt(rowSpanAttribute.getNodeValue()) + 1;
                    if (rowSpan < 1)
                        rowSpan = 1;
                    else if (rowSpan > rowDefinitions.size())
                        rowSpan = rowDefinitions.size();

                    //Set the row span.
                    constraints.gridheight = rowSpan;
                }

                Node columnSpanAttribute = child.getAttributes().getNamedItem("Grid.ColumnSpan");
                if (columnSpanAttribute != null)
                {
                    //Parse the column span attribute and make sure it is in range.
                    int columnSpan = Integer.parseInt(columnSpanAttribute.getNodeValue()) + 1;
                    if (columnSpan < 1)
                        columnSpan = 1;
                    else if (columnSpan > columnDefinitions.size())
                        columnSpan = columnDefinitions.size();

                    //Set the column span.
                    constraints.gridwidth = columnSpan;
                }

                panel.add(UIBuilder.ParseXMLNode(child), constraints);
            }
        }
        //#endregion
    }

    /**
     * Gets the weight value of a node.
     * @return A pair containing the weight type and the weight value. The weight types are as follows:
     * 0: No weight (aka auto).
     * 1: Weight in pixels.
     * 2: Weight as a percentage (0-1).
     */
    private static Pair<Integer, Float> GetWeightValue(Node node, String subNodeKey, String attributeName)
    {
        if (!node.getNodeName().equals(subNodeKey))
            throw new IllegalArgumentException(subNodeKey + "s nodes must contain only " + subNodeKey + " nodes.");

        if (node.getAttributes() == null || node.getAttributes().getNamedItem(attributeName) == null)
        {
            return new Pair<>(0, null);
        }
        else
        {
            String value = node.getAttributes().getNamedItem(attributeName).getNodeValue();
            if (value == null)
            {
                return new Pair<>(0, null);
            }
            else if (value.endsWith("px"))
            {
                return new Pair<>(1, Float.parseFloat(value.substring(0, value.length() - 2)));
            }
            else
            {
                //If this fails to parse then an invalid value has been provided.
                try
                {
                    return new Pair<>(2, Float.parseFloat(value));
                }
                catch (NumberFormatException e)
                {
                    throw new IllegalArgumentException("Invalid value for " + attributeName + " attribute.");
                }
            }
        }
    }

    private static void CorrectAutoWeights(List<Pair<Integer, Float>> definitions)
    {
        //For the definitions that are not set (i.e. should be auto), we need to calculate their weight.
        //Pixel set definitions are ignored.
        int autoDefinitions = 0;
        float percentageUsed = 0;
        for (Pair<Integer, Float> definition : definitions)
        {
            switch (definition.Item1)
            {
                case 0: //Auto.
                    autoDefinitions++;
                    break;
                case 1: //Pixels.
                    break;
                case 2: //Percentage.
                    percentageUsed += definition.Item2;
                    break;
                default: //Shouldn't be reached.
                    break;
            }
        }

        if (autoDefinitions == 0)
            return;

        float autoWeight = (1 - percentageUsed) / autoDefinitions;

        //Update the auto definitions.
        for (int i = 0; i < definitions.size(); i++)
        {
            Pair<Integer, Float> definition = definitions.get(i);
            if (definition.Item1 == 0)
            {
                definitions.set(i, new Pair<>(2, autoWeight));
            }
        }
    }

    private static int GetAlignment(Node node)
    {
        if (!node.hasAttributes())
            return GridBagConstraints.CENTER;

        //Get the vertical alignment.
        int verticalAlignment = GridBagConstraints.CENTER;
        Node verticalAlignmentAttribute = node.getAttributes().getNamedItem("VerticalAlignment");
        if (verticalAlignmentAttribute != null)
        {
            switch (verticalAlignmentAttribute.getNodeValue())
            {
                case "Top":
                    verticalAlignment = GridBagConstraints.NORTH;
                    break;
                case "Center":
                    verticalAlignment = GridBagConstraints.CENTER;
                    break;
                case "Bottom":
                    verticalAlignment = GridBagConstraints.SOUTH;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid value for VerticalAlignment attribute.");
            }
        }

        //Get the horizontal alignment.
        int horizontalAlignment = GridBagConstraints.CENTER;
        Node horizontalAlignmentAttribute = node.getAttributes().getNamedItem("HorizontalAlignment");
        if (horizontalAlignmentAttribute != null)
        {
            switch (horizontalAlignmentAttribute.getNodeValue())
            {
                case "Left":
                    horizontalAlignment = GridBagConstraints.WEST;
                    break;
                case "Center":
                    horizontalAlignment = GridBagConstraints.CENTER;
                    break;
                case "Right":
                    horizontalAlignment = GridBagConstraints.EAST;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid value for HorizontalAlignment attribute.");
            }
        }

        //Combine the alignments.
        //This could've been so much easier if the constraints could've been OR'd together to get the correct value.
        switch (verticalAlignment)
        {
            case GridBagConstraints.NORTH:
                switch (horizontalAlignment)
                {
                    case GridBagConstraints.WEST:
                        return GridBagConstraints.NORTHWEST;
                    case GridBagConstraints.CENTER:
                        return GridBagConstraints.NORTH;
                    case GridBagConstraints.EAST:
                        return GridBagConstraints.NORTHEAST;
                    default:
                        throw new IllegalArgumentException("Invalid value for HorizontalAlignment attribute.");
                }
            case GridBagConstraints.CENTER:
                switch (horizontalAlignment)
                {
                    case GridBagConstraints.WEST:
                        return GridBagConstraints.WEST;
                    case GridBagConstraints.CENTER:
                        return GridBagConstraints.CENTER;
                    case GridBagConstraints.EAST:
                        return GridBagConstraints.EAST;
                    default:
                        throw new IllegalArgumentException("Invalid value for HorizontalAlignment attribute.");
                }
            case GridBagConstraints.SOUTH:
                switch (horizontalAlignment)
                {
                    case GridBagConstraints.WEST:
                        return GridBagConstraints.SOUTHWEST;
                    case GridBagConstraints.CENTER:
                        return GridBagConstraints.SOUTH;
                    case GridBagConstraints.EAST:
                        return GridBagConstraints.SOUTHEAST;
                    default:
                        throw new IllegalArgumentException("Invalid value for HorizontalAlignment attribute.");
                }
            default:
                throw new IllegalArgumentException("Invalid value for VerticalAlignment attribute.");
        }
    }
}
