import javax.swing.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        SessionManager manager = new SessionManager();
        GUI gui = new GUI();
        ReminderManager reminderManager = new ReminderManager();
        reminderManager.checkTime();
        reminderManager.startWaterReminders();

    }
}