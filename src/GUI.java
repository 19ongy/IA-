//GUI CLASS
//gimem a sec
import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{    //card layout thing
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JPanel menuScreen;
    private JPanel sessionScreen;
    private Menu menu = new Menu();

    public GUI() {
        setTitle("Grindset");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // quit the app when we close the window
        setLocation(25, 25);
        setSize(800, 500);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel);

        setMenuScreen();
        setSessionScreen();

        setVisible(true);

        cardLayout.show(cardPanel,"Menu");
    }

    public void setMenuScreen(){
        menuScreen = new JPanel(null);
        JLabel labelOutput = new JLabel("Welcome to the Grindset");
        labelOutput = new JLabel("Welcome to the Grindset");
        labelOutput.setBounds(50, 110, 6000, 30);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 20));

        JButton setSesh = new JButton("Start new session");
        setSesh.setBounds(70, 150, 300, 30);
        setSesh.setFont(new Font("Arial", Font.BOLD, 17));
        setSesh.setForeground(Color.BLACK);
        // Add an ActionListener to the button
        //METHOD ARGUEMNT = E
        setSesh.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            cardLayout.show(cardPanel, "Session");
            SwingUtilities.invokeLater(() -> {
                menu.startMenu(1);
            });
        });

        JButton setRem = new JButton("Set Reminder");
        setRem.setBounds(70, 190, 300, 30);
        setRem.setFont(new Font("Arial", Font.BOLD, 17));
        setRem.setForeground(Color.BLACK);
        // Add an ActionListener to the button
        //METHOD ARGUEMNT = E
        setRem.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            menu.startMenu(2);
        });

        JButton studyStats = new JButton("View Study Stats");
        studyStats.setBounds(70, 230, 300, 30);
        studyStats.setFont(new Font("Arial", Font.BOLD, 17));
        studyStats.setForeground(Color.BLACK);
        // Add an ActionListener to the button
        //METHOD ARGUEMNT = E
        studyStats.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            menu.startMenu(3);
        });

        JButton settings = new JButton("Settings");
        settings.setBounds(70, 270, 300, 30);
        settings.setFont(new Font("Arial", Font.BOLD, 17));
        settings.setForeground(Color.BLACK);
        // Add an ActionListener to the button
        //METHOD ARGUEMNT = E
        settings.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            menu.startMenu(4);
        });

        ImageIcon menuPic = new ImageIcon("study.jpg");
        JLabel imageLabel = new JLabel(menuPic);
        imageLabel.setBounds(50, 50, menuPic.getIconWidth(), menuPic.getIconHeight());
        System.out.println("Image width: " + menuPic.getIconWidth());

        menuScreen.add(labelOutput);
        menuScreen.add(setSesh);
        menuScreen.add(setRem);
        menuScreen.add(studyStats);
        menuScreen.add(settings);
        menuScreen.add(imageLabel);

        cardPanel.add(menuScreen, "Menu");
        System.out.println("SEQUENCE: GUI_test created");
    }

    public void setSessionScreen(){
        sessionScreen = new JPanel(null);
        System.out.println("hi");

        JLabel labelOutput = new JLabel("Let's set a session!");
        labelOutput.setBounds(50, 30, 6000, 30);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 20));

        JButton setSesh = new JButton("Start new session");
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

        JButton setRem = new JButton("Set Reminder");
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

        JButton studyStats = new JButton("View Study Stats");
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

        JButton settings = new JButton("Settings");
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


        sessionScreen.add(labelOutput);
        sessionScreen.add(setSesh);
        sessionScreen.add(setRem);
        sessionScreen.add(studyStats);
        sessionScreen.add(settings);
        cardPanel.add(sessionScreen,"Session");
        System.out.println("SEQUENCE: GUI_test created");
    }
}
