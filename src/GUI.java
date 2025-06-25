//GUI CLASS
//gimem a sec
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GUI extends JFrame{    //card layout thing
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JPanel menuScreen;
    private JPanel sessionScreen;
    private JPanel moodScreen;
    private JPanel remMenu;
    private Menu menu = new Menu();
    private SetTimer timer = new SetTimer();
    private JLabel timerDisplay;

    private Color darkGreen = new Color(27, 77, 62);
    private Color lightGreen = new Color(200, 200, 200);
    private Color borderGreen = new Color(15, 50, 40);
    private Color backgroundGrey = new Color(46, 46, 46);


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
        setMoodScreen();
        setRemMenu();

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
            cardLayout.show(cardPanel, "Mood");
            //has to call the start of menu, but it causes issues now
            /*SwingUtilities.invokeLater(() -> {
                menu.startMenu(1);
            });

             */
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
            cardLayout.show(cardPanel, "Reminder Menu");

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

        ImageIcon menuPic = new ImageIcon("OIP.jpg");
        JLabel imageLabel = new JLabel(menuPic);
        imageLabel.setBounds(400, 100, menuPic.getIconWidth(), menuPic.getIconHeight());
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
        sessionScreen.setBackground(backgroundGrey);

        //dark green banner at the top
        JPanel banner = new JPanel();
        banner.setBackground(new Color(27, 77, 62));
        banner.setBounds(0, 0, 800, 100);
        banner.setLayout(null);

        //countdown button
        JButton countdownButton = new JButton("Countdown");
        countdownButton.setBounds(50, 40, 150, 30);
        countdownButton.setBackground(darkGreen);
        countdownButton.setForeground(lightGreen);
        countdownButton.setFont(new Font("Arial", Font.BOLD, 14));
        countdownButton.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        countdownButton.setFocusPainted(false);

        JButton stopwatchButton = new JButton("Stopwatch");
        stopwatchButton.setBounds(220, 40, 150, 30);
        stopwatchButton.setBackground(darkGreen);
        stopwatchButton.setForeground(lightGreen);
        stopwatchButton.setFont(new Font("Arial", Font.BOLD, 14));
        stopwatchButton.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        stopwatchButton.setFocusPainted(false);

        //possibly refresh every time, with a method for the "" that constnatly changes
        JLabel timerDisplay = new JLabel("00:00:00", SwingConstants.CENTER);
        timerDisplay.setBounds(150, 120, 500, 120);
        timerDisplay.setFont(new Font("Arial", Font.BOLD, 60));
        timerDisplay.setForeground(lightGreen);
        timerDisplay.setBackground(borderGreen);
        timerDisplay.setBorder(BorderFactory.createLineBorder(borderGreen, 2));


        JButton play = new JButton("PLAY");
        play.setBounds(300, 250, 100, 40);
        play.setFont(new Font("Arial", Font.BOLD, 17));
        play.setBackground(darkGreen);
        play.setForeground(lightGreen);
        play.setFocusPainted(false);
        play.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        play.addActionListener(e -> {
            timer.resume();
        });

        JButton pause = new JButton("PAUSE");
        pause.setBounds(425, 250, 100, 40);
        pause.setFont(new Font("Arial", Font.BOLD, 17));
        pause.setBackground(darkGreen);
        pause.setForeground(lightGreen);
        pause.setFocusPainted(false);
        pause.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        pause.addActionListener(e -> {
            timer.pause();
        });

        JButton returnHome = new JButton("Home");
        returnHome.setBounds(700, 30, 30, 30);
        returnHome.setFont(new Font("Arial", Font.BOLD, 17));
        returnHome.setBackground(darkGreen);
        returnHome.setForeground(lightGreen);
        returnHome.setFocusPainted(false);
        returnHome.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        returnHome.addActionListener(e -> {
            // This code will run when the button is clicked
            cardLayout.show(cardPanel, "Menu");
        });

        JButton musicBut = new JButton("MUSIC");
        musicBut.setBounds(700, 400, 30, 30);
        musicBut.setFont(new Font("Arial", Font.BOLD, 17));
        musicBut.setBackground(darkGreen);
        musicBut.setForeground(lightGreen);
        musicBut.setFocusPainted(false);
        musicBut.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        musicBut.addActionListener(e -> {
            // This code will run when the button is clicked
            System.out.println("imagine this is playing music");
        });

        sessionScreen.add(musicBut);
        sessionScreen.add(returnHome);
        sessionScreen.add(countdownButton);
        sessionScreen.add(stopwatchButton);
        sessionScreen.add(timerDisplay);
        sessionScreen.add(banner);
        sessionScreen.add(play);
        sessionScreen.add(pause);

        cardPanel.add(sessionScreen, "Session");
        System.out.println("session screen created");

    }

    public void setMoodScreen(){
        moodScreen = new JPanel(null);
        moodScreen.setBackground(backgroundGrey);

        //dark green banner at the top
        JPanel banner = new JPanel();
        banner.setBackground(new Color(27, 77, 62));
        banner.setBounds(0, 0, 800, 100);
        banner.setLayout(null);

        JLabel labelOutput = new JLabel("How are you feeling today?", SwingConstants.CENTER);
        labelOutput.setBounds(50, 150, 700, 40);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 24));
        labelOutput.setForeground(lightGreen);

        //making all the mood buttons
        String[] moods = {"Happy", "Sad", "Tired", "Determined", "Demotivated"};
        //colours for the buttons
        Color[] colours = {
                new Color(255, 255, 153),
                new Color(77, 148, 255),
                new Color(163, 163, 194),
                new Color(255, 51,51),
                new Color(194, 194, 214)
        };

        int width = 120;
        int height = 50;
        int startX = 50;
        int startY = 220;
        int gap = 20;

        for(int i = 0; i< moods.length; i++){
            JButton moodButton = new JButton((i+1) + ". " + moods[i]);
            moodButton.setBounds(startX + (width + gap)*i, startY, width, height);
            moodButton.setBackground(colours[i]);
            moodButton.setForeground(Color.BLACK);
            moodButton.setFocusPainted(false);
            moodButton.setFont(new Font("Arial", Font.BOLD, 14));

            moodButton.addActionListener(e -> {
                System.out.println("Mood selected = " + moodButton.getText());
                cardLayout.show(cardPanel, "Session");
            });
            moodScreen.add(moodButton);

        }

        moodScreen.add(labelOutput);
        moodScreen.add(banner);
        cardPanel.add(moodScreen, "Mood");

    }

    public void setRemMenu(){
        remMenu = new JPanel(null);
        remMenu.setBackground(backgroundGrey);

        //dark green banner at the top
        JPanel banner = new JPanel();
        banner.setBackground(new Color(27, 77, 62));
        banner.setBounds(0, 0, 800, 100);
        banner.setLayout(null);

        JLabel labelOutput = new JLabel("Reminder Menu", SwingConstants.CENTER);
        labelOutput.setBounds(50, 60, 700, 40);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 24));
        labelOutput.setForeground(lightGreen);

        String[] remFunc = {"Display all study reminders", "Add study reminder", "Replace reminder",
                "Delete study reminder", "Delete ALL study reminders", "Return"};

        int width = 250;
        int height = 30;
        int startX = 70;
        int startY = 130;
        int gap = 2 ;

        for(int i = 0; i< 6; i++) {
            JButton moodButton = new JButton((i + 1) + ". " + remFunc[i]);
            moodButton.setBounds(startX, startY + (50*i), width, height);
            //moodButton.setBackground();
            moodButton.setForeground(Color.BLACK);
            moodButton.setFocusPainted(false);
            moodButton.setFont(new Font("Arial", Font.BOLD, 14));

            moodButton.addActionListener(e -> {
                System.out.println("Mood selected = " + moodButton.getText());
                cardLayout.show(cardPanel, "Menu");
            });
            remMenu.add(moodButton);

            ImageIcon menuPic = new ImageIcon("image.png");
            JLabel imageLabel = new JLabel(menuPic);
            imageLabel.setBounds(400, 150, menuPic.getIconWidth(), menuPic.getIconHeight());

            remMenu.add(imageLabel);
            remMenu.add(labelOutput);
            remMenu.add(banner);
            cardPanel.add(remMenu, "Reminder Menu");
        }
    }
}
