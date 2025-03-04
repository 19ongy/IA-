import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        data data = new data(null, 0, 0 , 0 , 0, 0, 0, 0, null);
        System.out.println("How are you feeling? [before sesh] ");
        System.out.println("1. Happy \n2. Sad \n3. Tired \n4. Determined \n5. Demotivated \n6. Cancel");
        System.out.println("enter number: ");
        String moodBefore = data.getMood(input.nextInt());
        System.out.println("mood - " + moodBefore);

        System.out.println("What time did you start studying in 24 hour format: ");
        System.out.println("hour:");
        int startHour = input.nextInt();
        System.out.println("min:");
        int startMin = input.nextInt();

        System.out.println("End times: ");
        System.out.println("hour:");
        int endHour = input.nextInt();
        System.out.println("min:");
        int endMin = input.nextInt();

        int time = data.timeStudied(startHour, startMin, endHour, endMin);
        System.out.println(time + " minutes");
    }
}