//GUI CLASS
//gimem a sec
import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{    //card layout thing
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JPanel menuScreen;
    private JPanel sessionScreen;
    private JPanel bMoodScreen;
    private JPanel pMoodScreen;
    private JPanel graphMenu;
    private Menu menu = new Menu();
    private JLabel timerDisplay;
    private String value;

    SessionManager sessionManager = new SessionManager();
    GraphMaking graph = new GraphMaking();
    private SetTimer timer = new SetTimer();

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
        setBMoodScreen();
        setPMoodScreen();
        setGraphMenu();

        setVisible(true);

        cardLayout.show(cardPanel,"Menu");
        timerDisplay = new JLabel("00:00:00");
        value = "0";
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
            cardLayout.show(cardPanel, "bMood");
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
            cardLayout.show(cardPanel, "graphMenu");
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

        JLabel timerDisplay = new JLabel("00:00:00", SwingConstants.CENTER);
        timerDisplay.setBounds(150, 120, 500, 120);
        timerDisplay.setFont(new Font("Arial", Font.BOLD, 60));
        timerDisplay.setForeground(lightGreen);
        timerDisplay.setBackground(borderGreen);
        timerDisplay.setBorder(BorderFactory.createLineBorder(borderGreen, 2));

        JButton stopwatchButton = new JButton("Stopwatch");
        stopwatchButton.setBounds(220, 30, 150, 30);
        stopwatchButton.setBackground(darkGreen);
        stopwatchButton.setForeground(lightGreen);
        stopwatchButton.setFont(new Font("Arial", Font.BOLD, 14));
        stopwatchButton.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        stopwatchButton.setFocusPainted(false);
        stopwatchButton.addActionListener(e -> {
            timer.startStopwatch(timerDisplay);
        });


        JButton session = new JButton("NEW");
        session.setBounds(200, 300, 100, 40);
        session.setFont(new Font("Arial", Font.BOLD, 17));
        session.setBackground(darkGreen);
        session.setForeground(lightGreen);
        session.setFocusPainted(false);
        session.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        session.addActionListener(e -> {
            //changes the display on the gui
            timer.preTimer(timerDisplay);
        });

        JTextField timeInput = new JTextField(6);
        timeInput.setBounds(300, 300, 200, 40);
        timeInput.setFont(new Font("Arial", Font.BOLD, 17));
        timeInput.setBackground(lightGreen);
        timeInput.setForeground(borderGreen);
        timeInput.setBorder(BorderFactory.createLineBorder(borderGreen, 2));

        //confirmation check for inputting text
        JButton tick = new JButton("✓");
        tick.setBounds(500, 300, 40, 40);
        tick.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        tick.setBackground(darkGreen);
        tick.setForeground(lightGreen);
        tick.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        tick.addActionListener(e -> {
            value = timeInput.getText();
            //timer.setTime(String.valueOf(timer.setCountdownDuration(value)));
        });

        JButton pomo = new JButton("P");
        pomo.setBounds(560, 300, 40, 40);
        pomo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        pomo.setBackground(darkGreen);
        pomo.setForeground(lightGreen);
        pomo.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        pomo.addActionListener(e -> {
            System.out.println("here i can put pomo optoins ");
            //timer.setTime(String.valueOf(timer.setCountdownDuration(value)));
        });


        //countdown button
        JButton countdownButton = new JButton("Countdown");
        countdownButton.setBounds(50, 30, 150, 30);
        countdownButton.setBackground(darkGreen);
        countdownButton.setForeground(lightGreen);
        countdownButton.setFont(new Font("Arial", Font.BOLD, 14));
        countdownButton.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        countdownButton.setFocusPainted(false);
        countdownButton.addActionListener(e -> {
            timer.startCountdown(timer.formatTime(value), timerDisplay);
        });

        JButton play = new JButton("PLAY");
        play.setBounds(250, 250, 100, 40);
        play.setFont(new Font("Arial", Font.BOLD, 17));
        play.setBackground(darkGreen);
        play.setForeground(lightGreen);
        play.setFocusPainted(false);
        play.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        play.addActionListener(e -> {
            timer.resume();
        });

        JButton pause = new JButton("PAUSE");
        pause.setBounds(360, 250, 100, 40);
        pause.setFont(new Font("Arial", Font.BOLD, 17));
        pause.setBackground(darkGreen);
        pause.setForeground(lightGreen);
        pause.setFocusPainted(false);
        pause.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        pause.addActionListener(e -> {
            timer.pause();
        });

        JButton end = new JButton("END");
        end.setBounds(470, 250, 100, 40);
        end.setFont(new Font("Arial", Font.BOLD, 17));
        end.setBackground(darkGreen);
        end.setForeground(lightGreen);
        end.setFocusPainted(false);
        end.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        end.addActionListener(e -> {
            timer.endT(timerDisplay);

        });


        sessionScreen.add(tick);
        sessionScreen.add(end);
        sessionScreen.add(countdownButton);
        sessionScreen.add(stopwatchButton);
        sessionScreen.add(timerDisplay);
        sessionScreen.add(banner);
        sessionScreen.add(play);
        sessionScreen.add(pause);
        sessionScreen.add(session);
        sessionScreen.add(timeInput);
        sessionScreen.add(pomo);

        cardPanel.add(sessionScreen, "Session");
        System.out.println("session screen created");

    }

    public void setBMoodScreen(){
        bMoodScreen = new JPanel(null);

        Color darkGreen = new Color(27, 77, 62);
        Color lightGreen = new Color(200, 200, 200);
        Color borderGreen = new Color(15, 50, 40);
        Color backgroundGrey = new Color(46, 46, 46);
        bMoodScreen.setBackground(backgroundGrey);

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
        String[] moods = {"Happy", "Sad", "Tired", "Determined", "Anguished", "Skip"};
        String[] moodImages = {"😊", "😢", "😴", "💪", "😞", "⏭"};
        //colours for the buttons
        Color[] colours = {
                new Color(255, 255, 153),
                new Color(77, 148, 255),
                new Color(163, 163, 194),
                new Color(255, 51,51),
                new Color(194, 194, 214),
                new Color(17, 77, 55)
        };

        int width = 80;
        int height = 70;
        int startX = 80;
        int startY = 220;
        int gap = 20;

        for(int i = 0; i< moods.length; i++){
            JButton moodButton = new JButton(moodImages[i]);
            moodButton.setBounds(startX + (width + gap)*i, startY, width, height);
            moodButton.setBackground(colours[i]);
            moodButton.setForeground(Color.BLACK);
            moodButton.setFocusPainted(false);
            moodButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
            moodButton.setToolTipText(moods[i]);
            moodButton.addActionListener(e -> {
                System.out.println("Mood selected = " + moodButton.getText());
                //sessionManager.setMoodBefore(moodButton.getText());
                //input setter method for the mood towards sessionManager
                cardLayout.show(cardPanel, "Session");
            });
            bMoodScreen.add(moodButton);

        }

        bMoodScreen.add(labelOutput);
        bMoodScreen.add(banner);
        cardPanel.add(bMoodScreen, "bMood");
    }

    public void setPMoodScreen(){
        pMoodScreen = new JPanel(null);

        Color darkGreen = new Color(27, 77, 62);
        Color lightGreen = new Color(200, 200, 200);
        Color borderGreen = new Color(15, 50, 40);
        Color backgroundGrey = new Color(46, 46, 46);
        pMoodScreen.setBackground(backgroundGrey);

        //dark green banner at the top
        JPanel banner = new JPanel();
        banner.setBackground(new Color(27, 77, 62));
        banner.setBounds(0, 0, 800, 100);
        banner.setLayout(null);

        JLabel labelOutput = new JLabel("How are you feeling today?", SwingConstants.CENTER);
        labelOutput.setBounds(50, 150, 700, 40);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 24));
        labelOutput.setForeground(lightGreen);

        pMoodScreen.add(banner);
        pMoodScreen.add(labelOutput);
        cardPanel.add(pMoodScreen, "pMood");
    }

    public void setGraphMenu(){
        graphMenu = new JPanel(null);

        Color darkGreen = new Color(27, 77, 62);
        Color lightGreen = new Color(200, 200, 200);
        Color borderGreen = new Color(15, 50, 40);
        Color backgroundGrey = new Color(46, 46, 46);
        graphMenu.setBackground(backgroundGrey);

        //dark green banner at the top
        JPanel banner = new JPanel();
        banner.setBackground(new Color(27, 77, 62));
        banner.setBounds(0, 0, 800, 100);
        banner.setLayout(null);

        //boxing out shapes for sutff
        JPanel tabBar = new JPanel();
        tabBar.setBackground(new Color(27, 77, 62));
        tabBar.setBounds(0, 0, 200, 500);
        tabBar.setLayout(null);

        //set points
        int mStartX = 0;
        int mStartY = 100;
        int mainTabX = 200;
        int mainTabY = 40;
        int amtMainTabs = 0;
        int amtSideTabs = 0;

        int sideTabY = 30;
        int sStartX = 20;
        int sideTabX = 200 - sStartX;
        int sStartY = (mStartX * amtMainTabs) + (sideTabY * amtSideTabs) + mainTabY + 100;

        JButton overviewButton = new JButton();
        overviewButton.setBounds(mStartX, mStartY, mainTabX, mainTabY);
        overviewButton.setBackground(darkGreen);
        overviewButton.setForeground(Color.BLACK);
        overviewButton.setFocusPainted(false);
        overviewButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));

        //have a method that whenever a new side tab is added, it adds to the tab list on the left
        //whenever a new side button is added, have an arraylist that takes the text of what they clicked
        //add the actionlistneer through siwtch and case thing

        JButton sideButton = new JButton();
        sideButton.setBounds(sStartX, sStartY, sideTabX, sideTabY);
        sideButton.setBackground(darkGreen);
        sideButton.setForeground(Color.BLACK);
        sideButton.setFocusPainted(false);
        sideButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));


        GraphMaking graph = new GraphMaking();
        graph.setSize(500,300);
        graph.setLocation(250, 120);


        graphMenu.add(banner);
        graphMenu.add(tabBar);
        graphMenu.add(graph);
        graphMenu.add(overviewButton);
        graphMenu.add(sideButton);
        cardPanel.add(graphMenu, "graphMenu");
    }


}
