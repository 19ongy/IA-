import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarPanel extends JPanel{
    private YearMonth currentMonth;
    private JPanel calendarGrid;
    private ArrayList<SessionManager> allSessions;
    private GUI gui;

    //the mood emojis and their meanings, to be displayed as heat map on calendar
    private static final HashMap<String, String> moodEmojis = new HashMap<>() {{
        put("HAPPY", "😊");
        put("SAD", "😢");
        put("TIRED", "😴");
        put("DETERMINED", "💪");
        put("ANGUISHED", "😞");
        put("SKIP", "⏭");
    }};

    public CalendarPanel(ArrayList<SessionManager> sessions, GUI gui){
        this.allSessions = sessions;
        this.gui = gui;
        this.currentMonth = YearMonth.now();

        setLayout(new BorderLayout());
        setBackground(new Color(46, 46, 46));

        //creates the header with the month in it
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(27,77,62));

        JButton prevMonth = new JButton("<");
        JButton nextMonth = new JButton(">");
        JLabel monthLabel = new JLabel(currentMonth.getMonth().toString() + " " + currentMonth.getYear(), SwingConstants.CENTER);

        monthLabel.setForeground(Color.WHITE);
        topPanel.add(prevMonth, BorderLayout.WEST);
        topPanel.add(monthLabel, BorderLayout.CENTER);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        rightButtons.setBackground(new Color(27,77,62));
        rightButtons.add(nextMonth);

        JButton returnButton = new JButton("Return");
        returnButton.setFocusPainted(false);
        returnButton.setBackground(new Color(27, 77, 62));
        returnButton.setForeground(Color.WHITE);
        returnButton.addActionListener(e-> {
            gui.cardLayout.show(gui.cardPanel, "graphMenu");
        });

        rightButtons.add(returnButton);

        topPanel.add(rightButtons, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        //calendar grid
        calendarGrid = new JPanel(new GridLayout(0, 7, 5, 5));
        calendarGrid.setBackground(new Color(46, 46, 46));
        add(calendarGrid, BorderLayout.CENTER);

        //fill the calendar with all the info
        populateCalendar();

        prevMonth.addActionListener(e -> {
            currentMonth = currentMonth.minusMonths(1);
            monthLabel.setText(currentMonth.getMonth().toString() + " " + currentMonth.getYear());
            populateCalendar();
        });
        nextMonth.addActionListener(e -> {
            currentMonth = currentMonth.plusMonths(1);
            monthLabel.setText(currentMonth.getMonth().toString() + " " + currentMonth.getYear());
            populateCalendar();
        });
    }

    //filling up the calendar with the average moods
    private void populateCalendar(){
        calendarGrid.removeAll();
        LocalDate firstDay = currentMonth.atDay(1);
        int startDay = firstDay.getDayOfWeek().getValue();
        // monday = 1, saturday = 7

        for(int i = 1; i<startDay; i++){
            calendarGrid.add(new JLabel(""));
        }

        int daysInMonth = currentMonth.lengthOfMonth();
        for(int day = 1; day <= daysInMonth; day++){
            LocalDate date = currentMonth.atDay(day);

            //finding the average mood
            ArrayList<SessionManager> daySessions = SessionManager.getSessionsByDate(allSessions, date);
            String avgMoodEmoji = calculateAverageMood(daySessions);

            JButton dayButton = new JButton();
            dayButton.setLayout(null);
            dayButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
            dayButton.setBackground(new Color(27,77,62));
            dayButton.setForeground(Color.WHITE);
            dayButton.setFocusPainted(false);
            dayButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            dayButton.setPreferredSize(new Dimension(100, 100));

            //SETTING THE DATE
            JLabel dateLabel = new JLabel(String.valueOf(day));
            dateLabel.setFont(new Font("Arial", Font.BOLD, 12));
            dateLabel.setForeground(Color.WHITE);
            dateLabel.setBounds(5, 5, 30, 20);
            dayButton.add(dateLabel);

            //THE MOOD ON EACH DAY PANEL
            JLabel moodLabel = new JLabel(avgMoodEmoji, SwingConstants.CENTER);
            moodLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
            moodLabel.setBounds(35, 30, 30, 30);
            dayButton.add(moodLabel);

            //STUDY TIME OF EACH DAY
            int totalSeconds = 0;
            for(SessionManager session : daySessions){
                totalSeconds = session.sessionLength + totalSeconds;
            }
            double hours = totalSeconds/3600.0;
            String hoursStudied = String.format("%.1f hrs", hours);
            JLabel timeLabel = new JLabel(hoursStudied, SwingConstants.CENTER);
            timeLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            timeLabel.setForeground(Color.WHITE);
            timeLabel.setBounds(20, 65, 60, 20);
            dayButton.add(timeLabel);

            //action listener, they can click on the calendar panel and get a daily overview of that day
            dayButton.addActionListener( e-> {
                int totSeconds = 0;
                String subject;
                String mood;
                HashMap<String, Integer> subjectTimeMap = new HashMap<>();
                HashMap<String, Integer> moodMap = new HashMap<>();

                //subject time aggregation
                for(SessionManager session : daySessions){
                    totSeconds = totSeconds + session.sessionLength;
                    if(session.getSubject() != null){
                        subject = session.getSubject();
                    }else{
                        subject = "Unknwon";
                    }
                    subjectTimeMap.put(subject, subjectTimeMap.getOrDefault(subject, 0) + session.sessionLength);

                    //mood aggre gation
                    if(session.getMoodBefore() != null){
                        mood = session.getMoodBefore().toString();
                    }else{
                        mood = "SKIP";
                    }
                    moodMap.put(mood, moodMap.getOrDefault(mood, 0) + 1);
                }

                StringBuilder tableBuilder = new StringBuilder();
                tableBuilder.append(date.toString() + "\n");
                tableBuilder.append(" Total Studied: " + String.format("%.1f hrs", totSeconds / 3600.0) + "\n\n");
                tableBuilder.append("Subjects:\n");

                for(String subj : subjectTimeMap.keySet()){
                    double hrs = subjectTimeMap.get(subj) / 3600.0;
                    tableBuilder.append(" - " + subj + " : " + String.format("%.1f hrs", hrs) + "\n");

                }

                JTextArea popUp = new JTextArea(tableBuilder.toString());
                popUp.setEditable(false);
                popUp.setFont(new Font("Arial", Font.PLAIN, 14));
                popUp.setBackground(new Color(27,77,62));
                popUp.setForeground(Color.WHITE);
                popUp.setMargin(new Insets(10 ,10, 10, 10));

                JScrollPane scrollPane = new JScrollPane(popUp);
                scrollPane.setPreferredSize(new Dimension(300, 250));

                //allows it to pop up in the top right corner
                JDialog dialog = new JDialog((Frame) null, "Daily Overview", false);
                //puts a scrollable summary inside the dialogue
                dialog.getContentPane().add(scrollPane);
                dialog.pack();

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                dialog.setLocation(screenSize.width - dialog.getWidth() - 20, 20);

                dialog.setVisible(true);
            });

            calendarGrid.add(dayButton);
        }
        calendarGrid.revalidate();
        calendarGrid.repaint();
    }

    private String calculateAverageMood(ArrayList<SessionManager> sessions){
        if(sessions.isEmpty()){
            return "⏭";
        }

        //count moods
        HashMap<String, Integer> moodCount = new HashMap<>();
        for(SessionManager session : sessions){
            String mood;
            if(session.getMoodBefore() != null){
                mood = session.getMoodBefore().toString();
            }else{
                mood = "SKIP";
            }
            moodCount.put(mood, moodCount.getOrDefault(mood, 0) + 1);
        }

        //getting the average mood/ most frequent mood felt
        String mFreqMood = "SKIP";
        int maxCount = 0;
        for(String mood : moodCount.keySet()){
            if(moodCount.get(mood) > maxCount){
                maxCount = moodCount.get(mood);
                mFreqMood = mood;
            }
        }
        return moodEmojis.getOrDefault(mFreqMood,"⏭");
    }
}
