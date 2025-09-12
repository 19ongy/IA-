import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//where all study stat overview graphs made
public class TotalStatsPanel {
    private JPanel mainPanel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private int totalStudySeconds;
    private int totalBreakSeconds;

    //constructor
    public TotalStatsPanel() {
        mainPanel = new JPanel(new BorderLayout());

        ArrayList<SessionManager> sessions = SessionManager.readSessions();
        ArrayList<Break> breaks = readBreaks();
        totalStudySeconds = 0;
        totalBreakSeconds = 0;
        Map<String, Integer> moodBeforeMap = new HashMap<>();
        Map<String, Integer> moodAfterMap = new HashMap<>();
        Map<String, Integer> timePeriodMap = new HashMap<>();

        timePeriodMap.put("Morning", 0);
        timePeriodMap.put("Afternoon", 0);
        timePeriodMap.put("Evening", 0);

        for (SessionManager session : sessions) {
            totalStudySeconds = totalStudySeconds + session.sessionLength;

            String moodBefore = session.getMoodBefore().toString();
            String moodAfter = session.getMoodAfter().toString();

            moodBeforeMap.put(moodBefore, moodBeforeMap.getOrDefault(moodBefore, 0) + 1);
            moodAfterMap.put(moodAfter, moodAfterMap.getOrDefault(moodAfter, 0) + 1);

            int hour = session.startLocalTime.getHour();
            if (hour < 12) {
                timePeriodMap.put("Morning", timePeriodMap.get("Morning") + 1);
            } else if (hour > 12 && hour < 18) {
                timePeriodMap.put("Afternoon", timePeriodMap.get("Afternoon") + 1);
            } else if (hour > 18 && hour < 24) {
                timePeriodMap.put("Evening", timePeriodMap.get("Evening") + 1);
            }
        }

        for (Break br : breaks) {
            totalBreakSeconds = totalBreakSeconds + br.getLength();
        }

        //table data for all the total study stats
        String[][] data = {
                {"Total Study Time", totalStudySeconds / 60 + " minutes"},
                {"Total Break Time", totalBreakSeconds / 60 + " minutes"},
                {"Most Active Period", getMostActivePeriod(timePeriodMap)},
                {"Mood Before", moodBeforeMap.toString()},
                {"Mood After", moodAfterMap.toString()}
        };

        String[] columnNames = {"Total..", "Value"};
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    //allows us to access all the graph info from outside the class
    public JPanel getPanel() {
        return mainPanel;
    }

    private ArrayList<Break> readBreaks() {
        ArrayList<Break> breaks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("break_data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int length = Integer.parseInt(parts[0]);
                    LocalDate startDate = LocalDate.parse(parts[1]);
                    LocalTime startTime = LocalTime.parse(parts[2]);
                    LocalDate endDate = LocalDate.parse(parts[3]);
                    LocalTime endTime = LocalTime.parse(parts[4]);
                    breaks.add(new Break(length, startDate, startTime, endDate, endTime));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return breaks;
    }

    //finding which map string has the highest value, to get data for graph displaying most active times
    private String getMostActivePeriod(Map<String, Integer> map) {
        return map.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Unkown");
    }

}
