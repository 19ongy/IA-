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

    private int timeElapsed;
    private int timeRemaining;
    private Timer timer;

    //constructor
    public SetTimer(){
        this.timeElapsed = 0;
        this.timeRemaining = 0;
    }

    //method to set timer duration
    public int setTimerDuration(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("How long is your timer? enter in minutes: ");
        timeRemaining = scanner.nextInt() * 60;
        return timeRemaining;
    }

    //starts timer and displays how much time is left
    public void startTimer(int timeRemaining){
        //makes sure you entered a proper time
        if(timeRemaining <= 0){
            System.out.println("Make set a valid time. ");
            return;
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run(){
                //checks if there's still time left
                if(timeElapsed < timeRemaining){
                    System.out.println("TIME LEFT: "  + formatTime(returnTimeLeft()));
                }else{
                    System.out.println("Good job!! Timer finished! ");
                    timer.cancel();
                }
            }
        };

        //period parameter is measured in milliseconds
        timer.schedule(task, 0, 1000);
    }

    //method calculates how much time is left in countdown
    private int returnTimeLeft(){
        timeElapsed = timeElapsed + 1;
        return timeRemaining - timeElapsed;
    }

    //formats time left in countdown as HH:MM:SS
    private String formatTime(int secondsLeft){
        int hoursLeft = secondsLeft/3600;
        int minsLeft = (secondsLeft%3600)/60;
        int secsLeft = secondsLeft%60;
        String timeDisplay = String.format("%02d:%02d:%02d", hoursLeft, minsLeft, secsLeft);
        return timeDisplay;
    }


    //gets the amount of time studied for an already existing schedule
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
