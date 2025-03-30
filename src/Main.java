public class Main {
    public static void main(String[] args) {
        MoodEntry mood = new MoodEntry();
        mood.MoodTest(MoodEntry.Test.HAPPY);
        /*
        System.out.println("Before session - enter your mood");
        String moodBefore = mood.getMood();

        System.out.println("After session - enter your mood");
        String moodAfter = mood.getMood();

         */

        SetTimer timer = new SetTimer();
        timer.startTimer(timer.setTimerDuration());


    }
}