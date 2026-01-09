import javax.swing.*;
import java.awt.*;

public class GraphMaking extends JPanel {

    // test data - time studied, will be added on later ( only for total amount of time studied, not for any specific subject stuff )
    //serves as placeholder until we link to actual session data
    private int[] timeStudied = {1, 2, 3, 4, 5, 6, 7}; // hours studied
    private String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int padding = 50; //space around edges for axis and labels
        int labelPadding = 25;
        int pointWidth = 8;
        int numberYDivisions = 5;

        // background
        g.setColor(Color.WHITE); //clear canvas
        g.fillRect(0, 0, width, height);

        // draws x and y axis
        g.setColor(Color.BLACK);
        g.drawLine(padding, height - padding, width - padding, height - padding); // x-axis
        g.drawLine(padding, padding, padding, height - padding); // y-axis

        // Y-axis labels and lines to mark hours studied
        for (int i = 0; i <= numberYDivisions; i++) {
            int y = height - padding - (i * (height - 2 * padding) / numberYDivisions);
            g.drawLine(padding - 5, y, padding + 5, y);
            String yLabel = i * 2 + "h";
            g.drawString(yLabel, padding - 35, y + 5);
        }

        //x-axis for days of the week
        for (int i = 0; i < days.length; i++) {
            int x = padding + i * (width - 2 * padding) / (days.length - 1);
            g.drawLine(x, height - padding - 5, x, height - padding + 5);
            g.drawString(days[i], x - 10, height - padding + 20);
        }

        //connections between each day's study time
        g.setColor(Color.GREEN); //gotta set with the custom colours later on
        for (int i = 0; i < timeStudied.length - 1; i++) {
            int x1 = padding + i * (width - 2 * padding) / (days.length - 1);
            int y1 = height - padding - (timeStudied[i] * (height - 2 * padding) / (numberYDivisions * 2));
            int x2 = padding + (i + 1) * (width - 2 * padding) / (days.length - 1);
            int y2 = height - padding - (timeStudied[i + 1] * (height - 2 * padding) / (numberYDivisions * 2));
            g.drawLine(x1, y1, x2, y2);
        }

        //highlights each days exact value
        g.setColor(Color.RED);
        for (int i = 0; i < timeStudied.length; i++) {
            int x = padding + i * (width - 2 * padding) / (days.length - 1);
            int y = height - padding - (timeStudied[i] * (height - 2 * padding) / (numberYDivisions * 2));
            g.fillOval(x - pointWidth / 2, y - pointWidth / 2, pointWidth, pointWidth);
            //filed oval to mark the exact data point
        }
    }
}
