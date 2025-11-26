public class Main {
    public static void main(String[] args) {
        SessionManager manager = new SessionManager();
        GUI gui = new GUI();

        ReminderManager rem = new ReminderManager();
        rem.startWaterReminder();
    }
}

