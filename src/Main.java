public class Main {
    public static void main(String[] args) {

        //study reminders
        User user = new User();
        user.menu();

        //timer
        SessionManager sessionManager = new SessionManager();
        sessionManager.createSession();
    }
}