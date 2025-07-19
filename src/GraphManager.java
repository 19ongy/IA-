import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphManager{
    private JPanel tabPanel;
    private JPanel mainDisplay;
    private CardLayout cardLayout;

    public GraphManager(JPanel tabPanel, JPanel mainDisplay){
        this.tabPanel = tabPanel;
        tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.Y_AXIS));
        this.mainDisplay = mainDisplay;
        this.cardLayout = (CardLayout) mainDisplay.getLayout();

    }

    public void addMTab(String label, String cardName){
        JPanel cardContent = new JPanel();
        cardContent.add(new JLabel("content for " + label));
        //adds the thing
        mainDisplay.add(cardContent, cardName);

        JButton tabButton = new JButton(label);
        tabButton.setLocation(0,0);
        tabButton.setPreferredSize(new Dimension(150, 40));
        tabButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        tabButton.setFocusPainted(false);
        tabButton.setForeground(Color.BLACK);
        tabButton.setBackground(Color.LIGHT_GRAY);
        tabButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        tabButton.addActionListener(e -> {
            cardLayout.show(mainDisplay, cardName);
        });

        tabPanel.add(tabButton);
        System.out.println("Tab count: " + tabPanel.getComponentCount());
        tabPanel.revalidate();
        tabPanel.repaint();


    }
}



