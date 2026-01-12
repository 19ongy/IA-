//GUI CLASS
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUI extends JFrame{
    //initialising all the JPanels
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
    public static HashMap<String, Color> subjectColors = loadSubjectColours();

    //setting default times for pomodoros
    //25, 5, 25, 5, 25, 15 pomo session in seconds for default
    int[] defaultDurations = {1500, 300, 1500, 300, 1500, 900};
    String[] defaultTypes = {"Study", "Break", "Study", "Break", "Study", "Break"};
    public boolean isPomodoro = false; //differentiate between normal and set timers

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
        //setting a fixed location on screen
        setLocation(25, 25);
        setSize(800, 500);

        //helps to manage and switch between all of the different card panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel);

        //Initialise and add all of the individual screens to the card panel
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

        //setting up the button for accessing the daily overview graph
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
            DailyOverviewPanel dayPanel = new DailyOverviewPanel(dateToShow, daySessions, dayBreaks, this);

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

        //acess button for calendar overview
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

    //main menu, first thing you see when you open the project
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
            //switches the current active screen to the session one where you set timers
        });

        JButton setRem = new JButton("Set Reminder");
        //changing its aesthetic features and positioning
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
            cardLayout.show(cardPanel,"settingMenu");
        });

        //loading an image in
        ImageIcon menuPic = new ImageIcon("OIP.jpg");
        JLabel imageLabel = new JLabel(menuPic);
        imageLabel.setBounds(400, 100, menuPic.getIconWidth(), menuPic.getIconHeight());

        //adding the individual components onto your card panel
        menuScreen.add(labelOutput);
        menuScreen.add(setSesh);
        menuScreen.add(setRem);
        menuScreen.add(studyStats);
        menuScreen.add(settings);
        menuScreen.add(imageLabel);
        //using helper methods to set a consistent image
        defaultLook(menuScreen, "Menu");
        cardPanel.add(menuScreen, "Menu");
    }

    //screen where you access all the timers
    public void setSessionScreen() {
        sessionScreen = new JPanel(null);
        JLabel timerDisplay = new JLabel("00:00:00", SwingConstants.CENTER);
        timerDisplay.setBounds(150, 120, 500, 120);
        timerDisplay.setFont(new Font("Arial", Font.BOLD, 60));
        timerDisplay.setForeground(lightGreen);
        timerDisplay.setBackground(borderGreen);
        timerDisplay.setBorder(BorderFactory.createLineBorder(borderGreen, 2));

        pomodoro = new Pomodoro(this, timer, timerDisplay, defaultDurations, defaultTypes);

        //setting stopwatch mode, counting up mode
        JButton stopwatchButton = new JButton("Stopwatch");
        stopwatchButton.setBounds(220, 30, 150, 30);
        stopwatchButton.setBackground(darkGreen);
        stopwatchButton.setForeground(lightGreen);
        stopwatchButton.setFont(new Font("Arial", Font.BOLD, 14));
        stopwatchButton.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        stopwatchButton.setFocusPainted(false);
        stopwatchButton.addActionListener(e -> {
            isPomodoro = false;
            //prompts the mood screen that asks you how you are before starting the stopwatch
            cardLayout.show(cardPanel, "bMood");
            //saving details about the session to be saved later on
            sessionManager.setStartDate();
            sessionManager.setStartTime();
            timer.startStopwatch(timerDisplay);
        });
//inputting the amount of time for a countdown
        JTextField timeInput = new JTextField(6);
        timeInput.setBounds(280, 300, 150, 40);
        timeInput.setFont(new Font("Arial", Font.BOLD, 17));
        timeInput.setBackground(lightGreen);
        timeInput.setForeground(borderGreen);
        timeInput.setBorder(BorderFactory.createLineBorder(borderGreen, 2));

        //break mode setting
        JButton breakButton = new JButton("Break");
        breakButton.setBounds(390, 30, 150, 30);
        breakButton.setBackground(darkGreen);
        breakButton.setForeground(lightGreen);
        breakButton.setFont(new Font("Arial", Font.BOLD, 14));
        breakButton.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        breakButton.setFocusPainted(false);
        breakButton.addActionListener(e -> {
            System.out.println("break has started!");

            //gets input from the time input , like a countdown
            String inputText = timeInput.getText().trim();

            try {
                int durationInSeconds;
                durationInSeconds = timer.formatTime(inputText);

                // start break with user-defined duration
                timer.startBreak(durationInSeconds, timerDisplay, () -> {});
            } catch (NumberFormatException ex) {
                // sends a option pane if it doesnt work
                JOptionPane.showMessageDialog(null,
                        "You haven't entered a valid form of break time! ",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        //opens up colour and subject selection
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

            dialog.setLocationRelativeTo(null); //centering
            //sets the background colour of the table background to grey
            dialog.getContentPane().setBackground(new Color(46, 46, 46));

            JLabel label = new JLabel("Enter subject: ");
            label.setBounds(20, 20, 100, 30);
            label.setForeground(Color.WHITE);
            JTextField subjectField = new JTextField();
            subjectField.setBounds(130, 20, 200, 30);

            //user can pick from these pre set colours
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
                    //update selected colour
                    selectedColour[0] = presetColours[finalI];

                    //reset all borders to default
                    for (JButton b : colourButtons) {
                        b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    }

                    //highlights and makes the colour easier to see for the user
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
                    //saving more details before being stored in the CSV
                    sessionManager.setSubject(subjectName);
                    subjectColors.put(subjectName, selectedColour[0]);
                    subjectButton.setBackground(selectedColour[0]);
                    subjectButton.setText(subjectName);

                    GUI.saveSubjectColours(subjectColors);
                    //saves it permanently so it will still be there if the user closes the app

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
            //sets the pomodoro pre session data
            pomodoro.sesh = new SessionManager();
            pomodoro.sesh.setSubject("Pomodoro");
            pomodoro.sesh.setStartDate();
            pomodoro.sesh.setStartTime();

            isPomodoro = true;
            cardLayout.show(cardPanel, "bMood");
            //sets the mode as pomodoro, to make it easier to differentiate later
            if(!timer.isPaused && (timer.timeElapsed > 0) && !timer.isEnded){
                //check
                int pomocheck = JOptionPane.showConfirmDialog(null, "There's already a timer running. Do you want to start pomo?", "Confirm", JOptionPane.YES_NO_OPTION);
                if(pomocheck != JOptionPane.YES_OPTION){
                    pomodoro.index = 0;
                    return;
                }
            }
            //makes sure start time is set
            sessionManager.setStartDate();
            sessionManager.setStartTime();
            cardLayout.show(cardPanel, "bMood");
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
            String valueStr = timeInput.getText().trim();

            //makes sure that you set a time before asking mood
            if(valueStr.isEmpty()){
                JOptionPane.showMessageDialog(null, "You haven't set a time yet! ");
                return;
            }

            int value;
            try{
                value = Integer.parseInt(valueStr);
                //makes sure that values being entered are valid
                if(value <= 0){
                    JOptionPane.showMessageDialog(null, "Please enter a time first");
                    return;
                }
            }catch(NumberFormatException j){ //safety net for different errors
                JOptionPane.showMessageDialog(null, "Please enter a time first");
                return;
            }

            cardLayout.show(cardPanel, "bMood");

            //record the session date and time
            sessionManager.setStartDate();
            sessionManager.setStartTime();
            System.out.println("test thing: " + timer.formatTime(String.valueOf(value)));
            timer.startCountdown(timer.formatTime(String.valueOf(value)), timerDisplay, () -> {});

        });

        //different timer manipulator buttons
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
            //prompts the app to ask for mood after the session ends
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

    //setting up of the mood screen that is called before the user starts a study period
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

        //drawing out the mood images and tool tips so they are easier to understand
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
                //triggers motivational reminders based on the moods that they are feeling
                //if the person is feeling sad or anguished or tired, negative feelings, they will get a motivational reminder

                if (List.of("TIRED", "SAD", "ANGUISHED").contains(moodName.toUpperCase())) {
                    int intensity = switch (moodName.toUpperCase()) {
                        //intensity of the different moods are rated so that motivation messages are tailored to their current mood

                        case "ANGUISHED" -> 8;
                        case "SAD" -> 6;
                        case "TIRED" -> 4;
                        default -> 3;
                    };
                    new ReminderGUI("motiv", null).sendMotivRem(intensity);
                }

                if(isPomodoro){
                    if(pomodoro.sesh == null){
                        pomodoro.sesh = new SessionManager();
                        //only sets the data after confirming there is nothing there already, and that it is a pmodoro timer
                        pomodoro.sesh.setStartDate();
                        pomodoro.sesh.setStartTime();
                    }

                    pomodoro.sesh.setMoodBefore(moodName.toUpperCase());
                    pomodoro.startStudySession();
                }else{
                    sessionManager.setMoodBefore(moodName.toUpperCase());
                    cardLayout.show(cardPanel, "Session");
                }

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
                String mood = moodName.toUpperCase();
                System.out.println("Mood selected = " + moodButton.getText());
                if (List.of("TIRED", "SAD", "ANGUISHED").contains(moodName.toUpperCase())) {
                    int intensity = switch (moodName.toUpperCase()) {
                        //increases the intensity based on what they feel like

                        case "ANGUISHED" -> 8;
                        case "SAD" -> 6;
                        case "TIRED" -> 4;
                        default -> 3;
                    };
                    new ReminderGUI("motiv", null).sendMotivRem(intensity);
                }

                if(isPomodoro && (pomodoro.sesh != null)){
                    //inputting all of the post session details so there are non missing
                    pomodoro.sesh.setMoodAfter(mood);
                    pomodoro.sesh.setEndDate();
                    pomodoro.sesh.setEndTime();
                    pomodoro.sesh.setSessionLength(timer.timeElapsed);
                    pomodoro.sesh.saveSession();

                    pomodoro.index++;
                    //starts the next round of the pomodoro timers
                    if(pomodoro.index < pomodoro.types.length) {
                        if (pomodoro.types[pomodoro.index].equals("Study")) {
                            //compares whether it's a study session
                            pomodoro.prepareNextSession();
                        } else {
                            pomodoro.startBreakSession();
                        }
                    }else{
                        cardLayout.show(cardPanel, "Session");
                    }
                    //forces ui to swtich out of pMood screen / card panel
                    cardPanel.revalidate();
                    cardPanel.repaint();
                }else{
                    sessionManager.setMoodAfter(mood);
                    sessionManager.setEndDate();
                    sessionManager.setEndTime();
                    sessionManager.setSessionLength(timer.timeElapsed);
                    sessionManager.saveSession();

                    cardLayout.show(cardPanel, "Session");
                }
            });
            pMoodScreen.add(moodButton);

        }

        pMoodScreen.add(labelOutput);
        defaultLook(pMoodScreen, "pMood");
        cardPanel.add(pMoodScreen, "pMood");
    }

    public void setGraphMenu(){
        graphMenu = new JPanel(null);

        ImageIcon icon = new ImageIcon("study.jpg");
        Image scaledImage = icon.getImage().getScaledInstance(400, 250, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setBounds(200, 120, 400, 300);
        graphMenu.add(imageLabel);

        returnBut(graphMenu, "graphMenu");
        graphButtons(graphMenu);
        defaultLook(graphMenu, "graphMenu");

        cardPanel.add(graphMenu, "graphMenu");
    }

    //using JSON
    //allows colours to subjects key to be saved even when they leave the app
    public static void saveSubjectColours(HashMap<String, Color> subjectColors){
        try(PrintWriter writer = new PrintWriter("subjects_colours.json")){
            writer.println("{");
            int count = 0;
            for(String subject : subjectColors.keySet()){
                Color c = subjectColors.get(subject);
                String entry = String.format("  \"%s\": [%d, %d, %d]", subject, c.getRed(), c.getGreen(), c.getBlue());

                writer.println(entry + (count < subjectColors.size() - 1 ? "," : ""));
                count = count + 1;
            }
            writer.println("}");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //reading from the json file
    //can be loaded up and stored on the daily overview tab
    public static HashMap<String, Color> loadSubjectColours(){
        HashMap<String, Color> map = new HashMap<>();
        File file = new File("subjects_colours.json");

        //cheking to see if the file exists, if not to create it
        if(!file.exists()){
            try{
                file.createNewFile();
                try(PrintWriter writer = new PrintWriter(file)){
                    writer.print("{}"); //empty JSON
                }
            }catch (IOException e){
                System.out.println("failed to create");
                e.printStackTrace();
            }
            return map;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                line = line.trim();

                //splitting up the json file into colours and subjects
                if(line.startsWith("\"")){
                    String[] parts = line.split(":");
                    String subject = parts[0].replace("\"", "").trim();
                    String[] rgb = parts[1].replace("[", "").replace("]", "").trim().split(",");

                    //makes sure there are no leading spaces
                    int r = Integer.parseInt(rgb[0].trim());
                    int g = Integer.parseInt(rgb[1].trim());
                    int b = Integer.parseInt(rgb[2].trim());

                    map.put(subject, new Color(r, g, b));
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return map;
    }

    //graphing methods
    public void setTotStatScreen(){
        TotalStatsPanel totals = new TotalStatsPanel();
        statScreen = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(darkGreen);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JPanel totalStatsBut = new JPanel();
        JLabel title = new JLabel("Total Stats");
        title.setForeground(Color.WHITE);
        totalStatsBut.setBackground(darkGreen);
        totalStatsBut.setForeground(lightGreen);
        totalStatsBut.setFont(new Font("Arial", Font.BOLD, 14));
        totalStatsBut.setBorder(BorderFactory.createLineBorder(borderGreen, 2));
        totalStatsBut.add(title);
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

        //allows user to access information not just smooched into a pay to read website
        JScrollPane scrollPane = DailyOverviewPanel.createSingleDayScroll(LocalDate.now(), sessions, breaks, this);

        cardPanel.add(scrollPane, "dailyOverview");
        cardLayout.show(cardPanel, "dailyOverview");
    }

    public void setCalendarAndTrends(){
        calendarAndTrends = new JPanel(null);
        graphButtons(calendarAndTrends);
        returnBut(calendarAndTrends, "calendarAndTrends");
        defaultLook(calendarAndTrends, "calendarAndTrends");
        cardPanel.add(calendarAndTrends, "calendarAndTrends");
    }

    public void setSettingMenu() {
        settingMenu = new JPanel(null);

        JLabel title = new JLabel("Settings", SwingConstants.CENTER);
        title.setBounds(50, 30, 700, 40);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(lightGreen);
        settingMenu.add(title);


        //pomo box to make things easy to read and separated
        JPanel pomoBox = new JPanel(null);
        pomoBox.setBounds(80, 100, 300, 300);
        pomoBox.setBackground(new Color(30, 30, 30));
        pomoBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightGreen, 2), "Pomodoro Settings", 0, 0, new Font("Arial", Font.BOLD, 16), lightGreen));

        //setting up all the labels and input fields, so that users can change their pomo times
        JLabel studyLabel = new JLabel("Study (min):");
        studyLabel.setBounds(20, 30, 120, 30);
        studyLabel.setForeground(Color.WHITE);
        JTextField studyField = new JTextField("25");
        studyField.setBounds(150, 30, 60, 30);

        JLabel shortBreakLabel = new JLabel("Short Break:");
        shortBreakLabel.setBounds(20, 70, 120, 30);
        shortBreakLabel.setForeground(Color.WHITE);
        JTextField shortBreakField = new JTextField("5");
        shortBreakField.setBounds(150, 70, 60, 30);

        JLabel longBreakLabel = new JLabel("Long Break:");
        longBreakLabel.setBounds(20, 110, 120, 30);
        longBreakLabel.setForeground(Color.WHITE);
        JTextField longBreakField = new JTextField("15");
        longBreakField.setBounds(150, 110, 60, 30);

        JLabel loopLabel = new JLabel("Loops:");
        loopLabel.setBounds(20, 150, 120, 30);
        loopLabel.setForeground(Color.WHITE);
        JTextField loopField = new JTextField("4");
        loopField.setBounds(150, 150, 60, 30);

        //button to confirm all of the options and apply them
        JButton applyButton = new JButton("Apply");
        applyButton.setBounds(90, 200, 100, 40);
        applyButton.setBackground(darkGreen);
        applyButton.setForeground(Color.WHITE);
        applyButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        applyButton.setFocusPainted(false);

        applyButton.addActionListener(e -> {
            try {
                //makes sure they're all in the same unit so that the website worked
                int study = Integer.parseInt(studyField.getText()) * 60;
                int shortBreak = Integer.parseInt(shortBreakField.getText());
                int longBreak = Integer.parseInt(longBreakField.getText());
                int loops = Integer.parseInt(loopField.getText());

                pomodoro.changePomo(study, shortBreak, longBreak, loops);
                JOptionPane.showMessageDialog(null, "Pomodoro settings updated!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
            }
        });

        //add the different componentsto the card panel
        pomoBox.add(studyLabel);
        pomoBox.add(studyField);
        pomoBox.add(shortBreakLabel);
        pomoBox.add(shortBreakField);
        pomoBox.add(longBreakLabel);
        pomoBox.add(longBreakField);
        pomoBox.add(loopLabel);
        pomoBox.add(loopField);
        pomoBox.add(applyButton);
        settingMenu.add(pomoBox);

        //box for reset buttons
        JPanel resetBox = new JPanel(null);
        resetBox.setBounds(420, 100, 300, 300);
        resetBox.setBackground(new Color(30, 30, 30));
        resetBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED, 2), "Reset Options", 0, 0, new Font("Arial", Font.BOLD, 16), Color.RED));

        //button to restart stat, by clearing the study and break text files
        JButton resetStatsButton = new JButton("Reset stats");
        resetStatsButton.setBounds(80, 40, 140, 40);
        resetStatsButton.setBackground(new Color(200, 50, 50));
        resetStatsButton.setForeground(Color.WHITE);
        resetStatsButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        resetStatsButton.setFocusPainted(false);

        resetStatsButton.addActionListener(e -> {
            //asks safety confusion questions
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset all stats?", "Confirm Reset", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                clearFile("session_data.txt");
                clearFile("break_data.txt");
                allSessions.clear();
                allBreaks.clear();
                JOptionPane.showMessageDialog(null, "All stats cleared.");
            }
        });

        JButton resetColorsButton = new JButton("Reset Colors");
        resetColorsButton.setBounds(80, 100, 140, 40);
        resetColorsButton.setBackground(new Color(100, 100, 255));
        resetColorsButton.setForeground(Color.WHITE);
        resetColorsButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        resetColorsButton.setFocusPainted(false);

        resetColorsButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Reset all color settings to default?", "Confirm Color Reset", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Placeholder: implement your color reset logic here
                JOptionPane.showMessageDialog(null, "Color settings reset.");
            }
        });
        resetBox.add(resetStatsButton);
        resetBox.add(resetColorsButton);
        settingMenu.add(resetBox);

        returnBut(settingMenu, "settingMenu");
        defaultLook(settingMenu, "settingMenu");
        cardPanel.add(settingMenu, "settingMenu");
    }

    //wipes it clean
    public void clearFile(String filename){
        try(PrintWriter writer = new PrintWriter(filename)){
            writer.print("");
            System.out.println(filename + "cleared.");
        }catch (IOException e){
            e.printStackTrace();
        }
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

        //displaying the list of reminders on the actual menu
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
                        //all the different optionson what culd hav h
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
