//GUI CLASS

import javax.swing.*;
import java.awt.*;

public class SessionMenu extends JFrame {
    private JLabel labelOutput;

    public void moodAsker(){
        setTitle("Grindset: Mood");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // quit the app when we close the window
        setSize(500, 300);
        setLocation(600, 200);
        setLayout(null);
        labelOutput = new JLabel("Welcome to the Grindset");
        labelOutput.setBounds(50, 30, 6000, 30);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 20));

        add(labelOutput);
        setVisible(true);
        System.out.println("created");
    }
}
