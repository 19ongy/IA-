//dealing with the daily overview sb inspired graph

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DailyOverviewPanel extends JPanel {
    private ArrayList<SessionManager> sessions;
    private ArrayList<Break> breaks;
    private LocalDate date;
    private GUI gui;

    private static final Color studyColor = new Color(27, 77, 62);
    private static final Color breakColor = new Color(200,200,200);
    private static final Color backgroundGrey = new Color(46,46,46);

    private int bannerHeight = 50;

    public DailyOverviewPanel(LocalDate date, ArrayList<SessionManager> sessions, ArrayList<Break> breaks, GUI gui) {
        this.date = date;
        this.sessions = sessions;
        this.breaks = breaks;
        this.gui = gui;

        setLayout(new BorderLayout());
        setBackground(backgroundGrey);

        //add top banner
        JPanel banner = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        banner.setBackground(new Color(27, 77, 62));

        JButton returnBut = new JButton("Return");
        returnBut.setPreferredSize(new Dimension(80, 30));
        returnBut.setBackground(new Color(27, 77, 62));
        returnBut.setForeground(Color.WHITE);
        returnBut.setFocusPainted(false);
        returnBut.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        returnBut.setToolTipText("Return to graph menu");
        returnBut.addActionListener(e -> {
            gui.cardLayout.show(gui.cardPanel, "graphMenu");
        });
        banner.add(returnBut);

        add(banner, BorderLayout.NORTH);

        //add graph panel in the center, no more layout issues
        GraphPanel graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);
    }

    //class inside DailyOverviewPanel for drawing the timeline
    private class GraphPanel extends JPanel{
        public GraphPanel(){
            setPreferredSize(new Dimension(700, (24 * 60) / 2));
            //0.5 px per minute
            setBackground(backgroundGrey);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int yOffset = bannerHeight;
            //start drawing below banner

            //date label
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString(date.toString(), 10, 20);

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

            //draw study blocks
            for (SessionManager session : sessions) {
                Color subjectColor = GUI.subjectColors.getOrDefault(session.getSubject(), new Color(27, 77, 62));
                String label = session.getSubject() != null ? session.getSubject() : "Study";
                //? : is another way of displaying an if else
                drawBlock(g, session.startLocalTime, session.sessionLength, subjectColor, label);
            }

            //draw breaks
            for (Break br : breaks) {
                drawBlock(g, br.getStartTime(), br.getLength(), breakColor, "Break");
            }
            drawLegend(g);
        }

        private void drawBlock(Graphics g, LocalTime startTime, int lengthSeconds, Color color, String label) {
            int yStart = 50 + (int) ((startTime.getHour() + startTime.getMinute() / 60.0) * 30);
            int height = (int) (lengthSeconds / 60.0 * 0.5);
            //0.5 px per minutee

            g.setColor(color);
            g.fillRect(100, yStart, 400, height);
            g.setColor(Color.WHITE);
            g.drawRect(100, yStart, 400, height);
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.drawString(label, 110, yStart + 15);
        }

        private void drawLegend(Graphics g) {
            int xStart = getWidth() - 160;
            int yStart = 10;
            int boxSize = 12;
            int spacing = 18;
            int i = 1;

            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.setColor(Color.WHITE);
            g.drawString("Subject Key:", xStart, yStart);

            for(String subject : GUI.subjectColors.keySet()) {
                Color c = GUI.subjectColors.get(subject);
                int y = yStart + i * spacing;

                g.setColor(c);
                g.fillRect(xStart, y, boxSize, boxSize);
                g.setColor(Color.WHITE);
                g.drawRect(xStart, y, boxSize, boxSize);
                g.drawString(subject, xStart + boxSize + 5, y + boxSize -2);

                i = i + 1;
            }
        }
    }


    //helper method to put into a scroll pane for the GUI
    public static JScrollPane createSingleDayScroll(LocalDate date, ArrayList<SessionManager> sessions, ArrayList<Break> breaks, GUI gui){
        DailyOverviewPanel dayPanel = new DailyOverviewPanel(date, sessions, breaks, gui);
        JScrollPane scrollPane = new JScrollPane(dayPanel);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        return scrollPane;
    }
}

