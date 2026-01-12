//draws the daily timelines
//shows all the study sessions and breaks

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
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

    //provides compile time error checking which makes sure there are no mistakes
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawDate(g);
        drawHourLines(g);
        drawStudySessions(g);
        drawBreaks(g);
        drawKey(g);
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

    private void drawBlock(Graphics g, LocalTime start, int lengthSeconds, Color colour, String label){
        //draws the block for study session, converts the start time and length to y and height
        int startMinutes = (start.getHour() * 60) + start.getMinute();
        int yStart = topGap + (int)(startMinutes* minuteScale);
        int height = (int)((lengthSeconds / 60.0)* minuteScale);

        //box filled with colour and outline
        g.setColor(colour);
        g.fillRect(100, yStart, 400, height);
        g.setColor(Color.WHITE);
        g.drawRect(100, yStart, 400, height);
    }

    //draw the grey block
    private void drawBreaks(Graphics g){
        for(Break b: breaks){
            drawBlock(g, b.getStartTime(), b.getLength(), breakColour, "Break");
        }
    }

    //draws study session block
    private void drawStudySessions(Graphics g) {
        for (SessionManager session : sessions) {
            Color subjectColour = GUI.subjectColors.getOrDefault(session.getSubject(), new Color(27, 77, 62));
            drawBlock(g, session.startLocalTime, session.sessionLength, subjectColour, session.getSubject());
        }
    }


    //key for subjects and their colours
    private void drawKey(Graphics g){
        g.setColor(Color.WHITE);
        g.drawString("Subjects:  ", getWidth() - 150, 20);
        int y = 40;

        //for every colour and subject inside the hashmap, a new row is formed
        for(String subject : GUI.subjectColors.keySet()){
            Color c = GUI.subjectColors.get(subject);

            g.setColor(c);
            g.fillRect(getWidth() - 150, y, 12, 12);
            g.setColor(Color.WHITE);
            g.drawRect(getWidth() - 150, y, 12, 12);
            g.drawString(subject, getWidth() - 130, y + 11);
            y += 20;
        }
    }



}