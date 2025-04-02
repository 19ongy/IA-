public class Main {
    public static void main(String[] args) {


        Session session = new Session(0, 0, 0, 0, 0 , 0, null, null, null );
        session.getDateAndTime();

        MoodEntry moodEntry = new MoodEntry();
        moodEntry.displayMood(moodEntry.getMood());

        SetTimer timer = new SetTimer();
        timer.setType();

    }
}