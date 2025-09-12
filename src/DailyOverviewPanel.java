//dealing with the daily overview sb inspired graph

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DailyOverviewPanel extends JPanel {
    private ArrayList<SessionManager> sessions;
    private ArrayList<Break> breaks;
    private LocalDate date;

    private static final Color studyColor = new Color(27, 77, 62);
    private static final Color breakColor = new Color(200,200,200);
    private static final Color backgroundGrey = new Color(46,46,46);

    private int bannerHeight = 50;
    private GUI gui;

    public DailyOverviewPanel(LocalDate date, ArrayList<SessionManager> sessions, ArrayList<Break> breaks ) {
        this.date = date;
        this.sessions = sessions;
        this.breaks = breaks;

        setLayout(null);
        setPreferredSize(new Dimension(700, (24 * 60) + bannerHeight));
        //setting size of graph to 1 px per minute of the day
        setBackground(backgroundGrey);

        /*
        //add top banner
        JPanel banner = new JPanel();
        banner.setBackground(new Color(27, 77, 62));
        banner.setBounds(0, 0, 800, bannerHeight);
        banner.setLayout(null);
        add(banner);

        JButton returnBut = new JButton("⏎");
        returnBut.setBounds(650, 30, 40, 40);
        returnBut.setBackground(new Color(27, 77, 62));
        returnBut.setForeground(Color.WHITE);
        returnBut.setFocusPainted(false);
        returnBut.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        returnBut.addActionListener(e -> {
            gui.cardLayout.show(gui.cardPanel, "graphMenu");
        });
        banner.add(returnBut);

         */
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int yOffset = bannerHeight;
        //start drawing below banner

        //making the date at the top
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(date.toString(), 10, 20);

        //draw hour lines on the graph
        g.setColor(Color.GRAY);
        for (int hour = 0; hour <= 23; hour++) {
            int y = yOffset + (hour * 60);
            g.drawLine(50, y, getWidth(), y);
            g.drawString(String.format("%02d:00", hour), 5, y + 5);
        }

        //TODO get all the graphs to have YOffset to move them all downwards a bit
        //draw study blocks
        for (SessionManager session : sessions) {
            drawBlock(g, session.startLocalTime, session.sessionLength, studyColor, "Study");
        }

        //draw breaks
        for (Break br : breaks) {
            drawBlock(g, br.getStartTime(), br.getLength(), breakColor, "Break");

        }
    }

    private void drawBlock(Graphics g, LocalTime startTime, int lengthSeconds, Color color, String label) {
        int yStart = 50 + (int) ((startTime.getHour() + startTime.getMinute() / 60.0) * 30);
        int height = (int) (lengthSeconds / 60.0 * 0.5);
        //0.5 px per minutee
        g.setColor(color);
        g.fillRect(100, yStart, 400, height);
        g.setColor(Color.WHITE);
        g.drawRect(100, yStart, 400, height);
        g.drawString(label, 110, yStart + 15);
    }

    //helper method to put into a scroll pane for the GUI
    public static JScrollPane createSingleDayScroll(LocalDate date, ArrayList<SessionManager> sessions, ArrayList<Break> breaks){
        DailyOverviewPanel dayPanel = new DailyOverviewPanel(date, sessions, breaks);
        JScrollPane scrollPane = new JScrollPane(dayPanel);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        return scrollPane;
    }
}

