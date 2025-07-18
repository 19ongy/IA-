//GUI CLASS
//gimem a sec
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame{    //card layout thing
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel menuScreen;
    private JPanel sessionScreen;
    private JPanel bMoodScreen;
    private JPanel pMoodScreen;
    private JPanel graphMenu;
    private JPanel settingMenu;
    private JPanel remMenu;

    private JLabel timerDisplay;
    private String value;
    private int optionChoice;

    //set colour theme
    private static final Color darkGreen = new Color(27, 77, 62);
    private static final Color lightGreen = new Color(200, 200, 200);
    private static final Color borderGreen = new Color(15, 50, 40);
    private static final Color backgroundGrey = new Color(46, 46, 46);

    //calling instances of other classes
    SessionManager sessionManager = new SessionManager();
    GraphMaking graph = new GraphMaking();
    ReminderManager reminder = new ReminderManager();
    private Timer timer = new Timer();
    private Menu menu = new Menu();
    ReminderManager rs = new ReminderManager();
    //ReminderGUI rg = new ReminderGUI();

    //constructor
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
        setSettingMenu();
        setRemMenu();

        setVisible(true);

        cardLayout.show(cardPanel,"Menu");
        timerDisplay = new JLabel("00:00:00");
        value = "0";
        optionChoice = 0;
    }

    //helper method for default card looks
    private void defaultLook(JPanel panel, String returnTo){
        //default colour
        panel.setBackground(backgroundGrey);
        panel.setLayout(null);

        //dark green panel at the top
        JPanel banner = new JPanel();
        banner.setBackground(darkGreen);
        banner.setBounds(0, 0, 800, 100);
        banner.setLayout(null);

        panel.add(banner);
    }

    //helper method for adding the return button in the top right corner
    private void returnBut(JPanel panel, String returnTo){
        //return button
        JButton returnBut = new JButton("⏎");
        returnBut.setBounds(700, 20, 40,40);
        returnBut.setBackground(darkGreen);
        returnBut.setForeground(Color.BLACK);
        returnBut.setFocusPainted(false);
        returnBut.setFont(new Font("Segoe UI Emoji", Font.BOLD, 9));
        returnBut.addActionListener(e -> {
                    cardLayout.show(cardPanel, "Menu");
                }
        );

        panel.add(returnBut);
    }

    public void setMenuScreen(){
        menuScreen = new JPanel(null);

        JLabel labelOutput = new JLabel("Welcome to the Grindset", SwingConstants.CENTER);
        labelOutput.setBounds(50, 50, 700, 40);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 24));
        labelOutput.setForeground(new Color(200, 200, 200));

        JButton setSesh = new JButton("Start new session");
        setSesh.setBounds(70, 150, 300, 35);
        setSesh.setFont(new Font("Arial", Font.BOLD, 17));
        setSesh.setBackground(darkGreen);
        setSesh.setForeground(lightGreen);
        setSesh.setFocusPainted(false);
        setSesh.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        setSesh.addActionListener(e -> {
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
            cardLayout.show(cardPanel, "reminders");
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
            cardLayout.show(cardPanel, "graphMenu");
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
            cardLayout.show(cardPanel,"setting menu");
        });

        ImageIcon menuPic = new ImageIcon("OIP.jpg");
        JLabel imageLabel = new JLabel(menuPic);
        imageLabel.setBounds(400, 100, menuPic.getIconWidth(), menuPic.getIconHeight());

        menuScreen.add(labelOutput);
        menuScreen.add(setSesh);
        menuScreen.add(setRem);
        menuScreen.add(studyStats);
        menuScreen.add(settings);
        menuScreen.add(imageLabel);
        defaultLook(menuScreen, "Menu");
        cardPanel.add(menuScreen, "Menu");
    }

    public void setSessionScreen() {
        sessionScreen = new JPanel(null);

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
            /*
            SwingUtilities.invokeLater(() -> {
                timer.preTimer(timerDisplay);
            });
             */
            sessionManager.setStartDate();
            sessionManager.setStartTime();
            timer.startStopwatch(timerDisplay);
        });

        JButton breakButton = new JButton("Break");
        breakButton.setBounds(390, 30, 150, 30);
        breakButton.setBackground(darkGreen);
        breakButton.setForeground(lightGreen);
        breakButton.setFont(new Font("Arial", Font.BOLD, 14));
        breakButton.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        breakButton.setFocusPainted(false);
        breakButton.addActionListener(e -> {
            System.out.println("break time! ");
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
            sessionManager.setSessionLength(timer.formatTime(value));
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
            sessionManager.setStartDate();
            sessionManager.setStartTime();
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
            cardLayout.show(cardPanel, "pMood");
        });

        returnBut(sessionScreen, "Session");
        sessionScreen.add(tick);
        sessionScreen.add(breakButton);
        sessionScreen.add(end);
        sessionScreen.add(countdownButton);
        sessionScreen.add(stopwatchButton);
        sessionScreen.add(timerDisplay);
        sessionScreen.add(play);
        sessionScreen.add(pause);
        sessionScreen.add(session);
        sessionScreen.add(timeInput);
        sessionScreen.add(pomo);
        defaultLook(sessionScreen, "Session");

        cardPanel.add(sessionScreen, "Session");
    }

    public void setBMoodScreen(){
        //button numbers
        int width = 80;
        int height = 70;
        int startX = 80;
        int startY = 220;
        int gap = 20;

        bMoodScreen = new JPanel(null);

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

        for(int i = 0; i< moods.length; i++){
            String moodName = moods[i];
            String butText = moodImages[i];

            JButton moodButton = new JButton(butText);
            moodButton.setBounds(startX + (width + gap)*i, startY, width, height);
            moodButton.setBackground(colours[i]);
            moodButton.setForeground(Color.BLACK);
            moodButton.setFocusPainted(false);
            moodButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
            moodButton.setToolTipText(moodName);
            moodButton.addActionListener(e -> {
                System.out.println("Mood selected = " + moodButton.getText());
                sessionManager.setMoodBefore(moodName.toUpperCase());
                cardLayout.show(cardPanel, "Session");
            });
            bMoodScreen.add(moodButton);

        }

        bMoodScreen.add(labelOutput);
        defaultLook(bMoodScreen, "bMood");
        cardPanel.add(bMoodScreen, "bMood");
    }

    public void setPMoodScreen(){
        pMoodScreen = new JPanel(null);

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
            String moodName = moods[i];
            String butText = moodImages[i];

            JButton moodButton = new JButton(butText);
            moodButton.setBounds(startX + (width + gap)*i, startY, width, height);
            moodButton.setBackground(colours[i]);
            moodButton.setForeground(Color.BLACK);
            moodButton.setFocusPainted(false);
            moodButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
            moodButton.setToolTipText(moodName);
            moodButton.addActionListener(e -> {
                System.out.println("Mood selected = " + moodButton.getText());
                sessionManager.setMoodAfter(moodName.toUpperCase());
                cardLayout.show(cardPanel, "Session");

                //sets the new session
                sessionManager.setSessionLength(timer.getTimeElapsed());
                sessionManager.setEndDate();
                sessionManager.setEndTime();
                sessionManager.setSubject("Maths");
                sessionManager.saveSession(null);
            });
            pMoodScreen.add(moodButton);

        }

        pMoodScreen.add(labelOutput);
        defaultLook(pMoodScreen, "pMood");
        cardPanel.add(pMoodScreen, "pMood");
    }

    public void setGraphMenu(){
        graphMenu = new JPanel(new BorderLayout());
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(tabPanel);
        scrollPane.setPreferredSize(new Dimension(250, 600));

        JPanel mainPanel = new JPanel(new CardLayout());
        System.out.println("working 1");
        GraphManager graphManager = new GraphManager(tabPanel, mainPanel);
        graphManager.addMTab("Total Overview", "totalOverviewCard");
        graphManager.addMTab("Date overview", "dateOverviewCard");
        graphManager.addMTab("Subject overview", "subjectOverviewCard");

        graphMenu.add(scrollPane, BorderLayout.WEST);
        graphMenu.add(mainPanel, BorderLayout.CENTER);

        GraphMaking graph = new GraphMaking();
        graph.setSize(500,300);
        graph.setLocation(250, 120);

        returnBut(graphMenu, "graphMenu");
        graphMenu.add(mainPanel, BorderLayout.CENTER);
        graphMenu.add(scrollPane, BorderLayout.WEST);
        //graphMenu.add(graph);

        returnBut(graphMenu, "graphmenu");
        defaultLook(graphMenu, "graphMenu");
        graphMenu.revalidate();
        graphMenu.repaint();
        cardPanel.add(graphMenu, "graphMenu");
    }

    public void setSettingMenu(){
        settingMenu = new JPanel(null);

        //setting buttons
        String[] settingOptTexts = {"User preference", "Timer settings", "Something settings"};
        for(int i = 0; i < 3; i++){
            JButton settingOpt = new JButton(settingOptTexts[i]);
            settingOpt.setBounds(130, (100 + (i*50)), 200,40);
            settingOpt.setBackground(darkGreen);
            settingOpt.setForeground(Color.BLACK);
            settingOpt.setFocusPainted(false);
            settingOpt.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
            settingMenu.add(settingOpt);
        }
        returnBut(settingMenu, "setting menu");
        defaultLook(settingMenu, "setting menu");
        cardPanel.add(settingMenu, "setting menu");
    }

    public void setRemMenu(){
        remMenu = new JPanel(null);

        JLabel labelOutput = new JLabel("SET REMINDERS", SwingConstants.CENTER);
        labelOutput.setBounds(50, 50, 700, 40);
        labelOutput.setFont(new Font("Arial", Font.BOLD, 24));
        labelOutput.setForeground(new Color(200, 200, 200));

        JPanel reminderPanel = new JPanel();
        reminderPanel.setLayout(new BoxLayout(reminderPanel, BoxLayout.Y_AXIS));
        reminderPanel.setBackground(new Color(35, 35, 35)); // actual scroll content background

        List<String> allReminders = reminder.loadReminders();
        for (int i = 0; i < allReminders.size(); i++) {
            String data = allReminders.get(i);
            JLabel reminderLabel = new JLabel("Reminder " + (i+1) + "  :  " + data);
            reminderLabel.setForeground(lightGreen);
            reminderLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            reminderLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            reminderPanel.add(reminderLabel);
        }

        int startX = 35;
        int startY = 180;
        int buttonX = 300;
        int buttonY = 40;
        int ySpace = 10;

        String[] buttonText = {"Add reminder", "Delete reminder", "Replace reminder", "Delete ALL reminders"};
        for(int i = 0; i< buttonText.length; i++){
            final int index = i;
            JButton remButton = new JButton(buttonText[i]);
            remButton.setBounds(startX, startY + (ySpace+buttonY)*i, buttonX, buttonY);
            remButton.setFont(new Font("Arial", Font.BOLD, 17));
            remButton.setBackground(darkGreen);
            remButton.setForeground(lightGreen);
            remButton.setFocusPainted(false);
            remButton.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
            remButton.addActionListener(e -> {
                optionChoice = index + 1;
                switch(optionChoice){
                    case 1:
                        System.out.println("adding..");
                        String timeInput = JOptionPane.showInputDialog(null, "Enter reminder time in format HHMM");
                        String messageInput = JOptionPane.showInputDialog(null, "Set reminder message");
                        if(messageInput==null || messageInput.trim().isEmpty()){
                            messageInput = "null";
                        }

                        if(timeInput!=null && !timeInput.trim().isEmpty() && !(timeInput.length()>4)){
                            reminder.addReminder(timeInput, messageInput);
                            List<String> reminders = reminder.loadReminders();
                            reminder.updateReminderList(reminderPanel, reminders);
                            rs.loadRemindersFromFile("reminder_data.txt");
                        }
                        break;
                    case 2:
                        System.out.println("deleting..");
                        String numDelete = JOptionPane.showInputDialog(null, "Which reminder would you like to delete");
                        if(numDelete != null && !numDelete.trim().isEmpty()){
                            int rIndex = Integer.parseInt(numDelete);
                            List<String> currentR = reminder.loadReminders();
                            reminder.deleteReminder(rIndex, reminderPanel, currentR);
                        }
                        break;
                    case 3:
                        System.out.println("replacing...");
                        String strReplace = JOptionPane.showInputDialog(null, "Which reminder would you like to replace?");
                        if(strReplace != null && !strReplace.trim().isEmpty()){
                            int numReplace = Integer.parseInt(strReplace);
                            String timeReplace = JOptionPane.showInputDialog(null, "What time HHMM");
                            String messageReplace = JOptionPane.showInputDialog(null, "What message");

                            reminder.replaceReminder(numReplace, timeReplace, messageReplace);
                            List<String> reminders = reminder.loadReminders();
                            reminder.updateReminderList(reminderPanel, reminders);

                        }
                        break;
                    case 4:
                        System.out.println("deleting all..");
                        String confirmation = JOptionPane.showInputDialog(null, "Are you sure about this?");
                        if(confirmation.equals("y") || confirmation.equals("yes")){
                            List<String> reminders = reminder.loadReminders();
                            reminder.deleteAll(reminderPanel, reminders);
                        }
                        break;
                }
            });
            remMenu.add(remButton);
        }



        JScrollPane scrollPane = new JScrollPane(reminderPanel);
        scrollPane.setBounds(360, 140, 400, 300);
        scrollPane.setBorder(BorderFactory.createLineBorder(borderGreen,2));
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        remMenu.add(labelOutput);
        returnBut(remMenu, "reminders");
        remMenu.add(scrollPane);
        defaultLook(remMenu, "reminders");
        cardPanel.add(remMenu, "reminders");
    }



}
