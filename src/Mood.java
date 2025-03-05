import java.util.Scanner;

public class Mood{
    int num;
    Scanner input = new Scanner(System.in);

    //setter
    public int setNum(int num){
        this.num = num;
        return num;
    }

    //moods: happy, sad, tired, determined, demotivated
    String[] moods = {"happy", "sad", "tired", "determined", "demotivated", "cancel"};

    //gets mood of the person before and after
    public String getMood(){
        System.out.println("How are you feeling?");
        System.out.println("1. Happy \n2. Sad \n3. Tired \n4. Determined \n5. Demotivated \n6. Cancel");
        System.out.println("enter number: ");
        int num = input.nextInt();
        if(num > 5 || num < 0){
            System.out.println("you have entered an invalid mood :( ");
        }else if(num == 5){
            return null;
        }else{
            return moods[num-1];
        }
        return null;
    }




}
