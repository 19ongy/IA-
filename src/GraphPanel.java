//draws the daily timelines
//shows all the study sessions and breaks

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class GraphPanel extends JPanel {
    private LocalDate date;
    private ArrayList<SessionManager> sessions;
    private ArrayList<Break> breaks;

    //colours used
    private static final Color backgroundGrey = new Color(46,46,46);
    private static final Color breakColour = new Color(200, 200, 200);
    private int topGap = 50;
    private double minuteScale = 0.5; //how many pixels represent 1 minute

    public GraphPanel(LocalDate date, ArrayList<SessionManager> sessions, ArrayList<Break> breaks){
        this.date = date;
        this.sessions = sessions;
        this.breaks = breaks;
        setBackground(backgroundGrey);
        //24 hours x 60 minutes = 1440 mins
        int totalHeight = (int)(1440*minuteScale) + topGap;
        setPreferredSize(new Dimension(700,totalHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawDate(g);
        drawHourLines(g);
    }

    private void drawDate(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(date.toString(), 10, 20);

    }
    private void drawHourLines(Graphics g){
        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        //drawing the vertical lines downwards from 00:00 - 24:00
        for(int i = 0; i <= 24; i++){
            int y = topGap + (int)(i * 60 * minuteScale);
            //horizontal lines
            g.drawLine(50, y, getWidth(), y);

            //time label
            String text = String.format("%02d:00", i % 24);
            g.drawString(text, 5, y + 3);
        }

    }


}