public class Main {
    public static void main(String[] args) {

        //study reminders
        SetReminder setReminder = new SetReminder();
        setReminder.menu();

        //timer
        SessionManager sessionManager = new SessionManager();
        sessionManager.createSession();
    }
}