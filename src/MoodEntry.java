//i dont know if this class is necessary anymore

import java.util.Scanner;

public class MoodEntry {
    enum Mood{
        HAPPY, SAD, TIRED, DETERMINED, ANGUISHED, SKIP
    }

    Scanner scanner = new Scanner(System.in);

    public Mood getMood(){
        System.out.println("-----------------------------------------------");
        System.out.println("How are you feeling?");
        System.out.println("1. Happy\n2. Sad\n3. Tired\n4. Determined\n5. Anguished\n6. Skip");
        System.out.print("Enter number: ");
        int num = scanner.nextInt();
        System.out.println("-----------------------------------------------");

        if((num < 1) || (num > 6)){
            System.out.println("Invalid input :( ");
            return Mood.SKIP;
        }else if(num == 6){
            return Mood.SKIP;
        }

        return Mood.values()[num - 1];
        //returns the mood corresponding to num - 1 in the mood array
    }

    public void displayMood(Mood mood){
        if(mood != Mood.SKIP) {
            System.out.println("You are feeling " + mood + " today.");
        }
    }




}
