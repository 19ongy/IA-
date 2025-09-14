//GUI CLASS
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUI extends JFrame{    //card layout thing
    public CardLayout cardLayout;
    public JPanel cardPanel;
    private JPanel menuScreen;
    private JPanel sessionScreen;
    private JPanel bMoodScreen;
    private JPanel pMoodScreen;
    private JPanel graphMenu;
    private JPanel settingMenu;
    private JPanel remMenu;
    private JPanel statScreen;
    private JPanel dailyOverview;
    private JPanel calendarAndTrends;

    public JLabel timerDisplay;
    private String value;
    private int optionChoice;

    //set colour theme
    private static final Color darkGreen = new Color(27, 77, 62);
    private static final Color lightGreen = new Color(200, 200, 200);
    private static final Color borderGreen = new Color(15, 50, 40);
    private static final Color backgroundGrey = new Color(46, 46, 46);
    public static HashMap<String, Color> subjectColors = new HashMap<>();

    //pomodoro stuff
    //25, 5, 25, 5, 25, 15 pomo session in seconds for default
    int[] defaultDurations = {5, 5, 1500, 300, 1500, 900};
    String[] defaultTypes = {"Study", "Break", "Study", "Break", "Study", "Break"};
    public boolean isPomodoro = false;

    //calling instances of other classes
    SessionManager sessionManager = new SessionManager();
    GraphMaking graph = new GraphMaking();
    ReminderManager reminder = new ReminderManager();
    private Timer timer = new Timer();
    private Menu menu = new Menu();
    ReminderManager rs = new ReminderManager();
    //ReminderGUI rg = new ReminderGUI();
    Pomodoro pomodoro;
    ArrayList<SessionManager> allSessions = SessionManager.readSessions();
    ArrayList<Break> allBreaks = BreakManager.readBreaks();

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
        setTotStatScreen();
        setDailyOverview();
        setCalendarAndTrends();

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

    //helper method for the graphing buttons
    private void graphButtons(JPanel panel){
        JButton totalStats = new JButton("Total Stats");
        totalStats.setBounds(50, 30, 150, 30);
        totalStats.setBackground(darkGreen);
        totalStats.setForeground(lightGreen);
        totalStats.setFont(new Font("Arial", Font.BOLD, 14));
        totalStats.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        totalStats.setFocusPainted(false);
        totalStats.addActionListener(e -> {
            cardLayout.show(cardPanel, "statScreen");
            System.out.println("no1");

        });

        JButton dailyOverview = new JButton("Daily Overview");
        dailyOverview.setBounds(220, 30, 150, 30);
        dailyOverview.setBackground(darkGreen);
        dailyOverview.setForeground(lightGreen);
        dailyOverview.setFont(new Font("Arial", Font.BOLD, 14));
        dailyOverview.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        dailyOverview.setFocusPainted(false);
        dailyOverview.addActionListener(e -> {
            LocalDate dateToShow = LocalDate.now();


            ArrayList<SessionManager> daySessions = SessionManager.getSessionsByDate(allSessions, dateToShow);
            ArrayList<Break> dayBreaks = Break.getBreaksByDate(allBreaks, dateToShow);

            //filters sessions and breaks only for the day dateToShow
            DailyOverviewPanel dayPanel = new DailyOverviewPanel(dateToShow, daySessions, dayBreaks);

            //making it scrollable
            JScrollPane scroll = new JScrollPane(dayPanel);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.getVerticalScrollBar().setUnitIncrement(20);

            cardPanel.add(scroll, "DailyOverview");
            cardLayout.show(cardPanel, "DailyOverview");
            //System.out.println("no2");

            //repaints and refreshes the GUI
            cardPanel.revalidate();
            cardPanel.repaint();
        });

        JButton calNTrends = new JButton("Calendar");
        calNTrends.setBounds(390, 30, 150, 30);
        calNTrends.setBackground(darkGreen);
        calNTrends.setForeground(lightGreen);
        calNTrends.setFont(new Font("Arial", Font.BOLD, 14));
        calNTrends.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        calNTrends.setFocusPainted(false);

        //creating the calendarpanel
        CalendarPanel calendarPanel = new CalendarPanel(allSessions, this);
        cardPanel.add(calendarPanel, "calendar");
        calNTrends.addActionListener(e -> {
            cardLayout.show(cardPanel, "calendar");
        });

        panel.add(dailyOverview);
        panel.add(calNTrends);
        panel.add(totalStats);
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
            cardLayout.show(cardPanel, "Session");
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

        pomodoro = new Pomodoro(this, timer, timerDisplay, defaultDurations, defaultTypes);

        JButton stopwatchButton = new JButton("Stopwatch");
        stopwatchButton.setBounds(220, 30, 150, 30);
        stopwatchButton.setBackground(darkGreen);
        stopwatchButton.setForeground(lightGreen);
        stopwatchButton.setFont(new Font("Arial", Font.BOLD, 14));
        stopwatchButton.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        stopwatchButton.setFocusPainted(false);
        stopwatchButton.addActionListener(e -> {
            isPomodoro = false;
            cardLayout.show(cardPanel, "bMood");
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
            System.out.println("break has started!");
            timer.startBreak(10, timerDisplay);
        });

        JTextField timeInput = new JTextField(6);
        timeInput.setBounds(280, 300, 150, 40);
        timeInput.setFont(new Font("Arial", Font.BOLD, 17));
        timeInput.setBackground(lightGreen);
        timeInput.setForeground(borderGreen);
        timeInput.setBorder(BorderFactory.createLineBorder(borderGreen, 2));

        JButton subjectButton = new JButton("S");
        subjectButton.setToolTipText("Set subject and colour");
        subjectButton.setBounds(230, 300, 40, 40);
        subjectButton.setFont(new Font("Arial", Font.BOLD, 14));
        subjectButton.setBackground(Color.GRAY);
        subjectButton.setForeground(lightGreen);
        subjectButton.setFocusPainted(false);
        subjectButton.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        subjectButton.addActionListener(e -> {
            JDialog dialog = new JDialog((Frame) null, "Choose Subject", true);
            dialog.setLayout(null);
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(null);
            dialog.getContentPane().setBackground(new Color(46, 46, 46));

            JLabel label = new JLabel("Enter subject: ");
            label.setBounds(20, 20, 100, 30);
            label.setForeground(Color.WHITE);
            JTextField subjectField = new JTextField();
            subjectField.setBounds(130, 20, 200, 30);

            Color[] presetColours = {
                    Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE,
                    Color.MAGENTA, Color.CYAN, Color.PINK, Color.YELLOW,
                    new Color(128, 0, 128), new Color(0, 128, 128),
                    new Color(255, 165, 0), new Color(75, 0, 130)
            };

            JButton[] colourButtons = new JButton[presetColours.length];
            Color[] selectedColour = {Color.GRAY}; //the default colour

            for(int i = 0; i< presetColours.length; i++){
                colourButtons[i] = new JButton();
                colourButtons[i].setBackground(presetColours[i]);
                colourButtons[i].setBounds(20 + (i % 6) * 60, 70 + (i / 6) * 50, 40, 40);
                colourButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                int finalI = i;

                colourButtons[i].addActionListener(k-> {
                    selectedColour[0] = presetColours[finalI];
                    for (JButton b : colourButtons) {
                        b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    }
                    colourButtons[finalI].setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

                    //checks whether the colour is already mapped to a subject
                    //makes sure there's no overlaps where there are multiple subjects assigned to one colour
                    for(String subj : subjectColors.keySet()){
                        if(subjectColors.get(subj).equals(selectedColour[0])){
                            subjectField.setText(subj);
                            break;
                        }
                    }
                });


                dialog.add(colourButtons[i]);
            }

            JButton confirm = new JButton("✓");
            confirm.setBounds(150, 200, 100, 40);
            confirm.setFont(new Font("Arial", Font.BOLD, 14));
            confirm.setBackground(selectedColour[0]);
            confirm.setForeground(lightGreen);
            confirm.addActionListener(j-> {
                String subjectName =  subjectField.getText().trim();
                if(!subjectName.isEmpty()){
                    sessionManager.setSubject(subjectName);
                    subjectColors.put(subjectName, selectedColour[0]);
                    subjectButton.setBackground(selectedColour[0]);
                    subjectButton.setText(subjectName);


                }
                dialog.dispose();
                //closes the popup
            });

            subjectField.addActionListener(z -> {
                confirm.doClick();
            });

            dialog.add(label);
            dialog.add(subjectField);
            dialog.add(confirm);
            dialog.setVisible(true);




        });


        //confirmation check for inputting text
        JButton tick = new JButton("✓");
        tick.setBounds(430, 300, 40, 40);
        tick.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        tick.setBackground(darkGreen);
        tick.setForeground(lightGreen);
        tick.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        tick.addActionListener(e -> {
            value = timeInput.getText();
            sessionManager.setSessionLength(timer.formatTime(value));
            //timer.setTime(String.valueOf(timer.setCountdownDuration(value)));
        });

        JButton pomo = new JButton("POMODORO");
        pomo.setBounds(480, 300, 100, 40);
        pomo.setFont(new Font("Arial", Font.BOLD, 14));
        pomo.setBackground(darkGreen);
        pomo.setForeground(lightGreen);
        pomo.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        pomo.addActionListener(e -> {
            isPomodoro = true;
            cardLayout.show(cardPanel, "bMood");
            //sets the mode as pomodoro, to make it easier to differentiate later
            if(!timer.isPaused && (timer.timeElapsed > 0) && !timer.isEnded){
                int pomocheck = JOptionPane.showConfirmDialog(null, "There's already a timer running. Do you want to start pomo?", "Confirm", JOptionPane.YES_NO_OPTION);
                if(pomocheck != JOptionPane.YES_OPTION){
                    pomodoro.index = 0;
                    return;
                }
            }
            //makes sure start time is set
            sessionManager.setStartDate();
            sessionManager.setStartTime();
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
            isPomodoro = false;
            cardLayout.show(cardPanel, "bMood");
            sessionManager.setStartDate();
            sessionManager.setStartTime();
            System.out.println("test thing: " + timer.formatTime(value));
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
        //sessionScreen.add(session);
        sessionScreen.add(timeInput);
        sessionScreen.add(pomo);
        sessionScreen.add(subjectButton);
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
                sessionManager.setMoodBefore(moodName.toUpperCase());
                System.out.println("Mood selected = " + moodButton.getText());

                if(isPomodoro){
                    pomodoro.startPomo();
                }

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
                sessionManager.setEndDate();
                sessionManager.setEndTime();
                sessionManager.saveSession();

                if(isPomodoro){
                    pomodoro.index = pomodoro.index +1;
                    pomodoro.startPomo();
                }

                cardLayout.show(cardPanel, "Session");
            });
            pMoodScreen.add(moodButton);

        }

        pMoodScreen.add(labelOutput);
        defaultLook(pMoodScreen, "pMood");
        cardPanel.add(pMoodScreen, "pMood");
    }

    public void setGraphMenu(){
        graphMenu = new JPanel(null);

        returnBut(graphMenu, "graphMenu");
        graphButtons(graphMenu);
        defaultLook(graphMenu, "graphMenu");

        cardPanel.add(graphMenu, "graphMenu");
    }


    //graphing methods
    public void setTotStatScreen(){
        TotalStatsPanel totals = new TotalStatsPanel();
        statScreen = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(darkGreen);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JButton totalStatsBut = new JButton("Total Stats");
        totalStatsBut.setBounds(50, 30, 150, 30);
        totalStatsBut.setBackground(darkGreen);
        totalStatsBut.setForeground(lightGreen);
        totalStatsBut.setFont(new Font("Arial", Font.BOLD, 14));
        totalStatsBut.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        totalStatsBut.setFocusPainted(false);
        totalStatsBut.addActionListener(e -> {
            cardLayout.show(cardPanel, "statScreen");

        });
        topPanel.add(totalStatsBut);

        statScreen.add(topPanel, BorderLayout.NORTH);

        //adds the total stats table
        statScreen.add(totals.getPanel(), BorderLayout.CENTER);

        JButton returnButton = new JButton("⏎");
        returnButton.setBackground(darkGreen);
        returnButton.setForeground(lightGreen);
        returnButton.setFocusPainted(false);
        returnButton.addActionListener(e ->
                cardLayout.show(cardPanel, "graphMenu"));

        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        returnPanel.setBackground(darkGreen);
        returnPanel.add(returnButton);
        statScreen.add(returnPanel, BorderLayout.SOUTH);

        cardPanel.add(statScreen, "statScreen");
    }

    public void setDailyOverview(){
        ArrayList<SessionManager> sessions = SessionManager.getSessionsByDate(SessionManager.readSessions(), LocalDate.now());
        ArrayList<Break> breaks = Break.getBreaksByDate(Break.readBreaks(), LocalDate.now());

        JScrollPane scrollPane = DailyOverviewPanel.createSingleDayScroll(LocalDate.now(), sessions, breaks);

        dailyOverview = new JPanel(new BorderLayout());
        dailyOverview.add(scrollPane, BorderLayout.CENTER);

        returnBut(dailyOverview, "graphMenu");
        defaultLook(dailyOverview, "dailyOverview");
        cardPanel.add(dailyOverview, "dailyOverview");
    }

    public void setCalendarAndTrends(){
        calendarAndTrends = new JPanel(null);
        graphButtons(calendarAndTrends);
        returnBut(calendarAndTrends, "calendarAndTrends");
        defaultLook(calendarAndTrends, "calendarAndTrends");
        cardPanel.add(calendarAndTrends, "calendarAndTrends");
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
