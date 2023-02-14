import javax.swing.*;
import java.awt.*;

public class ChatAppMockUI
{
    public ChatAppMockUI()
    {
        JFrame frame = new JFrame("Vertical List Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300); // set the size of the window

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Create a list of items
        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

        // Add each item to the main panel
        for (int i = 0; i < items.length; i++) {
            JLabel label = new JLabel(items[i]);
            constraints.gridx = 0;
            constraints.gridy = i;
            constraints.insets = new Insets(5, 5, 5, 5);
            constraints.anchor = GridBagConstraints.NORTHWEST; // align to the top-left
            constraints.fill = GridBagConstraints.NONE; // don't expand
            mainPanel.add(label, constraints);
        }

        // Add an empty label to fill any extra space
        JLabel emptyLabel = new JLabel();
        constraints.gridx = 0;
        constraints.gridy = items.length;
        constraints.weighty = 1.0; // fill any extra space
        mainPanel.add(emptyLabel, constraints);

        // Add the main panel to the frame and display it
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
