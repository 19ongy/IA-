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

    public SetTimer(int startHour, int startMin, int endHour, int endMin){
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
    }

    public int timeForTimer(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("How long is your timer? enter in minutes: ");
        //returns the time wanted in minutes
        return scanner.nextInt();
    }

    public void startTimer(){
        final int[] count = {0};
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run(){
                System.out.println(count[0]);
                count[0] = count[0] + 1;
            }
        };
        //period parameter is measured in milliseconds
        int timeInMinutes = timeForTimer();
        int timeInMilliseconds = timeInMinutes * 60 * 1000;
        timer.schedule(task, 0, 1000);

        /*
        for(int i = 0; i<timeInMinutes; i++){
            timer.schedule(countdown, 0, 1000);
        }

         */
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
