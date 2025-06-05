import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class GUI extends JFrame {
    private JLabel labelOutput;
    private JLabel askMenu;
    private JButton setSession;
    private JButton setReminder;
    private JButton studyStats;
    private JButton settings;


    public GUI() {

        setTitle("Starting Menu ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // quit the app when we close the window
        setSize(550, 350);
        setLocation(50,50);
        setLayout(null);

        labelOutput = new JLabel("Welcome to the Grindset");
        labelOutput.setBounds(50,50, 6000,30);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 20));


        setSession = new JButton("1. Timer");
        setSession.setBounds(70, 100, 180, 30);
        setSession.setFont(new Font("Arial", Font.BOLD, 20));
        setSession.setBackground(Color.GREEN);
        setSession.setForeground(Color.BLACK);
        // Add an ActionListener to the button
        setSession.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
        });

        add(labelOutput);
        add(setSession);
        setVisible(true);
        System.out.println("SEQUENCE: GUI_test created");
    }
}