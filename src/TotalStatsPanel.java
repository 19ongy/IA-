import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//where all study stat overview graphs made
public class TotalStatsPanel {
    private JPanel mainPanel;
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
        Map<String, Integer> subjectMap = new HashMap<>();

        timePeriodMap.put("Morning", 0);
        timePeriodMap.put("Afternoon", 0);
        timePeriodMap.put("Evening", 0);

        int longestSession = 0;

        for (SessionManager session : sessions) {
            int sessionLength = session.sessionLength;
            totalStudySeconds = totalStudySeconds + session.sessionLength;

            if (sessionLength > longestSession) {
                longestSession = sessionLength;
            }

            moodBeforeMap.merge(session.getMoodBefore().toString(), 1, Integer::sum);
            moodAfterMap.merge(session.getMoodAfter().toString(), 1, Integer::sum);
            subjectMap.merge(session.getSubject(), 1, Integer::sum);

            //tracking the time periods <3
            int hour = session.startLocalTime.getHour();
            if (hour < 12) {
                timePeriodMap.merge("Morning", 1, Integer::sum);
            } else if (hour < 18) {
                timePeriodMap.merge("Afternoon", 1, Integer::sum);
            } else {
                timePeriodMap.merge("Evening", 1, Integer::sum);
            }
        }

        for (Break br : breaks) {
            totalBreakSeconds = totalBreakSeconds + br.getLength();
        }

        //calculates average study session
        int avgSession;
        if (sessions.size() > 0) {
            avgSession = totalStudySeconds / sessions.size();
        } else {
            avgSession = 0;
        }

        //setting up tabs
        String mostBefore = emojiMost(moodBeforeMap);
        String mostAfter = emojiMost(moodAfterMap);
        String mostActivePeriod = getMostActivePeriod(timePeriodMap);
        String mostStudiedSubject = getMostActivePeriod(subjectMap);

        //table data for all the total study stats
        String[][] data = {
                {"Total Study Time", totalStudySeconds / 60 + " minutes"},
                {"Total Break Time", totalBreakSeconds / 60 + " minutes"},
                {"Longest Session", longestSession / 60 + " minutes"},
                {"Average Session Length", avgSession / 60 + " minutes"},
                {"Most Active Period", mostActivePeriod},
                {"Most Felt Mood Before", mostBefore},
                {"Most Felt Mood After", mostAfter},
                {"Most Studied Subject", mostStudiedSubject}
        };

        //creating a table to display info, with a not white background
        JTable table = new JTable(data, new String[]{"Total..", "Value"});
        table.setBackground(new Color(46, 46, 46));
        table.setForeground(new Color(200, 200, 200));
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.getViewport().setBackground(new Color(46, 46, 46));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(27, 77, 62));
        tabbedPane.addTab("Summary table", tableScroll);

        //subjects time breakdown
        Map<String, Integer> weekMap = new HashMap<>();
        Map<String, Integer> monthMap = new HashMap<>();
        Map<String, Integer> totalMap = new HashMap<>();
        LocalDate now = LocalDate.now();

        for (SessionManager session : sessions) {
            String subject = session.getSubject();
            int length = session.sessionLength;

            totalMap.merge(subject, length, Integer::sum);
            if (session.startLocalDate.isAfter(now.minusDays(7))) {
                weekMap.merge(subject, length, Integer::sum);
            }
            if (session.startLocalDate.getMonth() == now.getMonth()) {
                monthMap.merge(subject, length, Integer::sum);
            }
        }

        //making the github style heatmap activity graph
        JPanel heatmapPanel = new JPanel() {
            Map<LocalDate, Integer> studyMap = new HashMap<>();
            {
                setPreferredSize(new Dimension(800, 160));
                setBackground(new Color(46, 46, 46));
                for (SessionManager session : sessions) {
                    LocalDate date = session.startLocalDate;
                    studyMap.merge(date, 1, Integer::sum);
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                LocalDate start = LocalDate.now().minusDays(364);
                while (start.getDayOfWeek() != DayOfWeek.SUNDAY) {
                    start = start.minusDays(1);
                }
                int boxSize = 12;
                int padding = 2;
                int yOffset = 20;
                int xOffset = 40;
                Font labelFont = new Font("Arial", Font.PLAIN, 10);
                g2.setFont(labelFont);
                g2.setColor(Color.WHITE);

                //labelling the days with sun, tue, thur
                for (int d = 0; d < 7; d++) {
                    DayOfWeek dow = DayOfWeek.of((d + 7) % 7 + 1);
                    if (dow == DayOfWeek.SUNDAY || dow == DayOfWeek.TUESDAY || dow == DayOfWeek.THURSDAY) {
                        String dayLabel = dow.toString().substring(0, 3);
                        int y = d * (boxSize + padding) + yOffset + boxSize / 2;
                        g2.drawString(dayLabel, 5, y);
                    }
                }

                Month currentMonth = null;
                for (int week = 0; week < 52; week++) {
                    LocalDate weekStartDate = start.plusDays(week * 7);
                    Month weekMonth = weekStartDate.getMonth();
                    int x = week * (boxSize + padding) + xOffset;

                    if (weekMonth != currentMonth) {
                        currentMonth = weekMonth;
                        String monthLabel = currentMonth.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
                        g2.setColor(Color.WHITE);
                        g2.drawString(monthLabel, x, 15);
                    }

                    for (int day = 0; day < 7; day++) {
                        LocalDate date = start.plusDays(week * 7 + day);
                        int count = studyMap.getOrDefault(date, 0);
                        Color colour = getTHEshadeOfGreen(count);
                        int y = day * (boxSize + padding) + yOffset;
                        g.setColor(colour);
                        g.fillRect(x, y, boxSize, boxSize);
                    }
                }
            }

            //helps to display how many sessions the user did that day
            private Color getTHEshadeOfGreen(int sessionsDone) {
                if (sessionsDone == 0) return new Color(30, 30, 30);
                if (sessionsDone == 1) return new Color(80, 120, 80);
                if (sessionsDone == 2) return new Color(60, 160, 60);
                return new Color(40, 200, 40);
            }
        };

//adding it to my tabbed pane
        JScrollPane heatmapScroll = new JScrollPane(heatmapPanel);
        heatmapScroll.setPreferredSize(new Dimension(800, 160));
        heatmapScroll.getViewport().setBackground(new Color(46, 46, 46));
        tabbedPane.addTab("Activity heatmap over the years", heatmapScroll);

//setting up the table for the subject time breakdown
        String[] subjectsCols = {"Color", "Subject", "This Week", "This Month", "Total"};
        String[][] subjectData = new String[totalMap.size()][5];

        int i = 0;

        for (String subject : totalMap.keySet()) {
            Color c = GUI.subjectColors.getOrDefault(subject, new Color(27, 77, 62));
            //uses html colour boxes to get the colour swatches in the table, so user can see more clearly
            String colorBox = String.format("<html><div style='width:20px;height:20px;background-color:rgb(%d,%d,%d);'></div></html>",
                    c.getRed(), c.getGreen(), c.getBlue());

            subjectData[i][0] = colorBox;
            subjectData[i][1] = subject;
            subjectData[i][2] = String.format("%.1f", weekMap.getOrDefault(subject, 0) / 3600.0);
            subjectData[i][3] = String.format("%.1f", monthMap.getOrDefault(subject, 0) / 3600.0);
            subjectData[i][4] = String.format("%.1f", totalMap.getOrDefault(subject, 0) / 3600.0);
            i = i + 1;
        }

        JTable subjectTable = new JTable(subjectData, subjectsCols);
        subjectTable.setBackground(new Color(46, 46, 46));
        subjectTable.setForeground(Color.WHITE);
        subjectTable.setRowHeight(30);
        subjectTable.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));

        JScrollPane subjectScroll = new JScrollPane(subjectTable);
        subjectScroll.getViewport().setBackground(new Color(46, 46, 46));
        tabbedPane.addTab("Subject Time Breakdown", subjectScroll);

//add tabbedPane to the main panel
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
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
        return map.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Unknown");
    }

    private String emojiMost(Map<String, Integer> moodMap) {
        if (moodMap.isEmpty()) {
            return "N/A";
        }
        String most = moodMap.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("SKIP");
        return moodToEmoji(most) + " x " + moodMap.get(most);
    }

    private String moodToEmoji(String mood) {
        switch (mood) {
            case "HAPPY":
                return "😊";
            case "SAD":
                return "😢";
            case "TIRED":
                return "😴";
            case "DETERMINED":
                return "💪";
            case "ANGUISHED":
                return "😞";
            default:
                return "⏭";
        }
    }
}
