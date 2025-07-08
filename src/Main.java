import javax.swing.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // TODO delete the print line
        System.out.println();
        GUI gui = new GUI();
        ReminderGUI reminder = new ReminderGUI();
        JFrame frame = new JFrame("Study Time Graph");
        GraphMaking graph = new GraphMaking();

        frame.add(graph);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}