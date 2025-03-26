import java.sql.SQLOutput;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;
// to get the amount of time spent on the study session


public class SetTimer {
    private int startHour = 0;
    private int startMin = 0;
    private int endHour = 0;
    private int endMin = 0;
    int hourStudied = 0;
    int minStudied = 0;

    int timeElapsed;
    int timeRemaining;

    public SetTimer(int startHour, int startMin, int endHour, int endMin){
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;

        timeElapsed = 0;
        timeRemaining = 0;
    }

    public int timeForTimer(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("How long is your timer? enter in minutes: ");
        //returns the time wanted in minutes
        return scanner.nextInt();
    }

    public void startTimer(){
        timeRemaining = 10;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run(){
                System.out.println(returnTimeLeft());
            }
        };
        //period parameter is measured in milliseconds


        //question: how to limit the seconds? how to make it go down
        //how to format it so its like 00: 00


        timer.schedule(task, 0, 1000);

        //System.out.println(count);

        /*
        for(int i = 0; i<timeInMinutes; i++){
            timer.schedule(countdown, 0, 1000);
        }

         */
    }

    public int returnTimeLeft(){
        System.out.println("test");
        timeElapsed = timeElapsed + 1;
        return timeRemaining - timeElapsed;
    }

    //gets the a;mount of time studied for an already existing schedule
    public int ExistingTime(int startHour, int startMin, int endHour, int endMin){
        if(endHour == startHour){
            hourStudied = 0;
        }else if(endHour > startHour){
            hourStudied = endHour - startHour;
        }else if(endHour < startHour){
            endHour = endHour + 24;
            hourStudied = endHour - startHour;
        }

        if(endMin == startMin){
            minStudied = 0;
        }else if(endMin > startMin){
            minStudied = endMin - startMin;
        }else if(endMin < startMin){
            minStudied = (endMin + 60) - startMin;
        }

        return((hourStudied*60) + minStudied);
    }

}
