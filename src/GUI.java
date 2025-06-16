import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{
    private JLabel labelOutput;
    private JButton setSesh;
    private JButton setRem;
    private JButton studyStats;
    private JButton settings;
    Menu menu = new Menu();

    public void startMenu() {
        setTitle("Grindset");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // quit the app when we close the window
        setSize(500, 300);
        setLayout(null);
        labelOutput = new JLabel("Welcome to the Grindset");
        labelOutput.setBounds(50, 30, 6000, 30);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 20));

        setSesh = new JButton("Start new session");
        setSesh.setBounds(70, 70, 300, 30);
        setSesh.setFont(new Font("Arial", Font.BOLD, 17));
        setSesh.setBackground(Color.GREEN);
        setSesh.setForeground(Color.BLACK);
        // Add an ActionListener to the button
        //METHOD ARGUEMNT = E
        setSesh.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            menu.startMenu(1);
            sessionScreen();
        });

        setRem = new JButton("Set Reminder");
        setRem.setBounds(70, 110, 300, 30);
        setRem.setFont(new Font("Arial", Font.BOLD, 17));
        setRem.setBackground(Color.GREEN);
        setRem.setForeground(Color.BLACK);
        // Add an ActionListener to the button
        //METHOD ARGUEMNT = E
        setRem.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            menu.startMenu(2);
        });

        studyStats = new JButton("View Study Stats");
        studyStats.setBounds(70, 150, 300, 30);
        studyStats.setFont(new Font("Arial", Font.BOLD, 17));
        studyStats.setBackground(Color.GREEN);
        studyStats.setForeground(Color.BLACK);
        // Add an ActionListener to the button
        //METHOD ARGUEMNT = E
        studyStats.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            menu.startMenu(3);
        });

        settings = new JButton("Settings");
        settings.setBounds(70, 190, 300, 30);
        settings.setFont(new Font("Arial", Font.BOLD, 17));
        settings.setBackground(Color.GREEN);
        settings.setForeground(Color.BLACK);
        // Add an ActionListener to the button
        //METHOD ARGUEMNT = E
        settings.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            menu.startMenu(4);
        });

        add(labelOutput);
        add(setSesh);
        add(setRem);
        add(studyStats);
        add(settings);
        setVisible(true);
        System.out.println("SEQUENCE: GUI_test created");
    }

    public void sessionScreen(){
        setTitle("Grindset: Session Screen");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  // quit the app when we close the window
        setSize(500, 300);
        setLocation(0,150);
        setLayout(null);
        labelOutput = new JLabel("Let's set a session ! ");
        labelOutput.setBounds(50, 30, 6000, 30);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 20));

        setSesh = new JButton("Start new session");
        setSesh.setBounds(70, 70, 300, 30);
        setSesh.setFont(new Font("Arial", Font.BOLD, 17));
        setSesh.setBackground(Color.GREEN);
        setSesh.setForeground(Color.BLACK);
        // Add an ActionListener to the button
        //METHOD ARGUEMNT = E
        setSesh.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            menu.startMenu(1);
        });

        setRem = new JButton("Set Reminder");
        setRem.setBounds(70, 110, 300, 30);
        setRem.setFont(new Font("Arial", Font.BOLD, 17));
        setRem.setBackground(Color.GREEN);
        setRem.setForeground(Color.BLACK);
        // Add an ActionListener to the button
        //METHOD ARGUEMNT = E
        setRem.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            menu.startMenu(2);
        });

        studyStats = new JButton("View Study Stats");
        studyStats.setBounds(70, 150, 300, 30);
        studyStats.setFont(new Font("Arial", Font.BOLD, 17));
        studyStats.setBackground(Color.GREEN);
        studyStats.setForeground(Color.BLACK);
        // Add an ActionListener to the button
        //METHOD ARGUEMNT = E
        studyStats.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            menu.startMenu(3);
        });

        settings = new JButton("Settings");
        settings.setBounds(70, 190, 300, 30);
        settings.setFont(new Font("Arial", Font.BOLD, 17));
        settings.setBackground(Color.GREEN);
        settings.setForeground(Color.BLACK);
        // Add an ActionListener to the button
        settings.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            menu.startMenu(4);
        });

        add(labelOutput);
        add(setSesh);
        add(setRem);
        add(studyStats);
        add(settings);
        setVisible(true);
        System.out.println("SEQUENCE: GUI_test created");
    }



}
