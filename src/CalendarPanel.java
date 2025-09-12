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
        topPanel.add(nextMonth, BorderLayout.EAST);
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

    //TODO have to put the numbers in the top left of the calendar
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

            JButton dayButton = new JButton(avgMoodEmoji);
            dayButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
            dayButton.setBackground(new Color(27,77,62));
            dayButton.setForeground(Color.WHITE);
            dayButton.setFocusPainted(false);

            //TODO click the dates to open daily overview
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
