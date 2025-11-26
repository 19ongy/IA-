//swing component that draws vertical graph to display the study times in a day

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

    private static final Color breakColor = new Color(200,200,200);
    private static final Color backgroundGrey = new Color(46,46,46);
    private int bannerHeight = 50;

    //aesthetics
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


        //return buttons
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
        GraphPanel graphPanel = new GraphPanel(date, sessions, breaks);
        add(graphPanel, BorderLayout.CENTER);
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

