import javax.swing.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        SessionManager manager = new SessionManager();
        ReminderManager man = new ReminderManager();
        man.addReminder("1405", "hello");

        GUI gui = new GUI();
        ReminderManager reminderManager = new ReminderManager();
        reminderManager.startWaterReminders();

    }
}