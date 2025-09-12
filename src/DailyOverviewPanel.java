//dealing with the daily overview sb inspired graph

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DailyOverviewPanel extends JPanel {
    private ArrayList<SessionManager> sessions;
    private ArrayList<Break> breaks;
    private LocalDate date;

    private static final Color studyColor = new Color(27, 77, 62);
    private static final Color breakColor = new Color(200,200,200);
    private static final Color backgroundGrey = new Color(46,46,46);

    public DailyOverviewPanel(LocalDate date, ArrayList<SessionManager> sessions, ArrayList<Break> breaks ) {
        this.date = date;
        this.sessions = sessions;
        this.breaks = breaks;
        setPreferredSize(new Dimension(700, 24 * 60));
        //setting size of graph to 1 px per minute of the day
        setBackground(backgroundGrey);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //making the date at the top
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(date.toString(), 10, 20);

        //draw hour lines on the graph
        g.setColor(Color.GRAY);
        for(int hour = 0; hour <= 23; hour++) {
            int y = hour * 60;
            g.drawLine(50, y, getWidth(), y);
            g.drawString(String.format("%02d:00", hour), 5, y+ 5);
        }

        //drawing the breaks !
        //break study blocks will have dotted lines
        g.setColor(breakColor);
        Graphics2D g2 = (Graphics2D) g;
        for(Break br : breaks){
            int startY = br.getStartTime().getHour()*60 + br.getStartTime().getMinute();

            int endY = startY + (br.getLength() / 60);
            for(int y = startY;y<endY ; y+= 4 ){
                g2.drawLine(60, y, 660, y);
            }
        }
    }
}
