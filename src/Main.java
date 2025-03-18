public class Main {
    public static void main(String[] args) {
        MoodEntry mood = new MoodEntry();
        System.out.println("Before session - enter your mood");
        String moodBefore = mood.getMood();

        System.out.println("After session - enter your mood");
        String moodAfter = mood.getMood();
    }
}