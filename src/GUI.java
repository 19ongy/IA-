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

        //colours
        Color darkGreen = new Color(27, 77, 62);
        Color lightGreen = new Color(200, 200, 200);
        Color borderGreen = new Color(15, 50, 40);

        //dark green banner at the top
        JPanel banner = new JPanel();
        banner.setBackground(new Color(27, 77, 62));
        banner.setBounds(0, 0, 800, 100);
        banner.setLayout(null);

        JLabel labelOutput = new JLabel("Welcome to the Grindset", SwingConstants.CENTER);
        labelOutput.setBounds(50, 50, 700, 40);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 24));
        labelOutput.setForeground(new Color(200, 200, 200));

        //sets the background of the window to like grey
        menuScreen.setBackground(new Color(46, 46, 46));

        JButton setSesh = new JButton("Start new session");
        setSesh.setBounds(70, 150, 300, 35);
        setSesh.setFont(new Font("Arial", Font.BOLD, 17));
        setSesh.setBackground(darkGreen);
        setSesh.setForeground(lightGreen);
        setSesh.setFocusPainted(false);
        setSesh.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        setSesh.addActionListener(e -> {
            System.out.println("nice");
            cardLayout.show(cardPanel, "Session");
            SwingUtilities.invokeLater(() -> {
                menu.startMenu(1);
            });
        });

        JButton setRem = new JButton("Set Reminder");
        setRem.setBounds(70, 190, 300, 35);
        setRem.setFont(new Font("Arial", Font.BOLD, 17));
        setRem.setBackground(darkGreen);
        setRem.setForeground(lightGreen);
        setRem.setFocusPainted(false);
        setRem.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        setRem.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            menu.startMenu(2);
        });

        JButton studyStats = new JButton("View Study Stats");
        studyStats.setBounds(70, 230, 300, 35);
        studyStats.setFont(new Font("Arial", Font.BOLD, 17));
        studyStats.setBackground(darkGreen);
        studyStats.setForeground(lightGreen);
        studyStats.setFocusPainted(false);
        studyStats.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        studyStats.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("nice");
            menu.startMenu(3);
        });

        JButton settings = new JButton("Settings");
        settings.setBounds(70, 270, 300, 35);
        settings.setFont(new Font("Arial", Font.BOLD, 17));
        settings.setBackground(darkGreen);
        settings.setForeground(lightGreen);
        settings.setFocusPainted(false);
        settings.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
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
        menuScreen.add(banner);

        cardPanel.add(menuScreen, "Menu");
        System.out.println("SEQUENCE: GUI_test created");
    }

    public void setSessionScreen() {
        sessionScreen = new JPanel(null);
        System.out.println("hi");

        Color darkGreen = new Color(27, 77, 62);
        Color lightGreen = new Color(200, 200, 200);
        Color borderGreen = new Color(15, 50, 40);
        Color backgroundGrey = new Color(46, 46, 46);
        sessionScreen.setBackground(backgroundGrey);

        //dark green banner at the top
        JPanel banner = new JPanel();
        banner.setBackground(new Color(27, 77, 62));
        banner.setBounds(0, 0, 800, 100);
        banner.setLayout(null);

        //countdown button
        JButton countdownButton = new JButton("Countdown");
        countdownButton.setBounds(50, 30, 150, 30);
        countdownButton.setBackground(darkGreen);
        countdownButton.setForeground(lightGreen);
        countdownButton.setFont(new Font("Arial", Font.BOLD, 14));
        countdownButton.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        countdownButton.setFocusPainted(false);

        JButton stopwatchButton = new JButton("Stopwatch");
        stopwatchButton.setBounds(220, 30, 150, 30);
        stopwatchButton.setBackground(darkGreen);
        stopwatchButton.setForeground(lightGreen);
        stopwatchButton.setFont(new Font("Arial", Font.BOLD, 14));
        stopwatchButton.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        stopwatchButton.setFocusPainted(false);

        JLabel timerDisplay = new JLabel("00:00:00", SwingConstants.CENTER);
        timerDisplay.setBounds(150, 120, 500, 120);
        timerDisplay.setFont(new Font("Arial", Font.BOLD, 60));
        timerDisplay.setForeground(lightGreen);
        timerDisplay.setBackground(borderGreen);
        timerDisplay.setBorder(BorderFactory.createLineBorder(borderGreen, 2));



        sessionScreen.add(countdownButton);
        sessionScreen.add(stopwatchButton);
        sessionScreen.add(timerDisplay);
        sessionScreen.add(banner);

        cardPanel.add(sessionScreen, "Session");
        System.out.println("session screen created");

    }
}
