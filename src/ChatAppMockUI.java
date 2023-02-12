import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChatAppMockUI
{
    public ChatAppMockUI()
    {
        JFrame frame = new JFrame("Chat App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);

        //Panel with 2 columns
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        frame.add(panel);

        //Left column
        JPanel leftColumn = new JPanel();
        leftColumn.setBackground(Color.RED);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.3;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(leftColumn, constraints);

        //Right column
        JPanel rightColumn = new JPanel();
        rightColumn.setBackground(Color.BLUE);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.7;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(rightColumn, constraints);

        frame.setVisible(true);
    }
}
