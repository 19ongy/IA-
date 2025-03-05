import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Mood mood = new Mood();
        System.out.println("Before session - enter your mood");
        String moodBefore = mood.getMood();

        System.out.println("After session - enter your mood");
        String moodAfter = mood.getMood();
    }
}