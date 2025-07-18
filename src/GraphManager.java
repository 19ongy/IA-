import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphManager extends JFrame {
    private JPanel tabPanel;
    private JPanel mainDisplay;
    private CardLayout cardLayout;

    public GraphManager(JPanel tabPanel, JPanel mainDisplay){
        this.tabPanel = tabPanel;
        this.mainDisplay = mainDisplay;
        this.cardLayout = (CardLayout) mainDisplay.getLayout();

        setVisible(true);


    }

    public void addMTab(String label, String cardName){
        JPanel cardContent = new JPanel();
        cardContent.add(new JLabel("content for " + label));
        //adds the thing
        mainDisplay.add(cardConetnt, cardName);
        JButton tabButton = new JButton(label);
        tabButton.setSize(150, 40);
        tabButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        tabButton.setFocusPainted(false);

        tabButton.addActionListener(e -> {
            cardLayout.show(mainDisplay, cardName);
        });

        tabPanel.add(tabButton);
        tabPanel.revalidate();
        tabPanel.repaint();

        tabPanel.add(cardConetnt, cardName);


    }
}



