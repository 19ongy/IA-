import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        data data = new data(null, 0, 0 , 0 , 0, 0, 0, 0, null);
        System.out.println("How are you feeling?");
        System.out.println("1. Happy \n2. Sad \n3. Tired \n4. Determined \n5. Demotivated");
        System.out.println("enter number: ");
        String moodBefore = data.getMood(input.nextInt());
        System.out.println("mood - " + moodBefore);

        System.out.println("start time: ");



    }
}