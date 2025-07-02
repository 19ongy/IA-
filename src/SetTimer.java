/*
CLASS SUMMARY:
- Handles all of the methods for running timers/ countdowns
 */

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

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
    private TimerTask timerTask;
    boolean ready;
    private int preTimeRemaining;
    public int setTimerDuration;
    public boolean isPaused;

    public String time;

    SessionManager manager = new SessionManager();


    //constructor
    public SetTimer(){
        this.timeElapsed = 0;
        this.timeRemaining = 0;
        ready = false;
        preTimeRemaining = 3;
        setTimerDuration = 0;
        isPaused = false;
        time = "";
    }

    //setters and getters
    public int getSetTimerDuration() {
        return setTimerDuration;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    /*
    //decides if countdown or stopwatch
    public void setType(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What type of timer would you like to set: ");
        System.out.println("1. Countdown \n2. Stopwatch");;
        int answer = scanner.nextInt();

        if(answer == 2){
            preTimer();
            startStopwatch();
        }else if(answer == 1){
            //sets the amount of time before running the pre countdown
            int time = setCountdownDuration();
            //System.out.println(time);
            if(time != 0){
                preTimer();
                startCountdown(time);
            }
        }else{
            System.out.println("Invalid input. Try again.");
            //setType();
        }
    }

     */

    //pause the timer
    public void pause(){
        if(!isPaused){
            isPaused = true;
            System.out.println("\nTimer paused");
        }
    }

    //resume the timer
    public void resume(){
        if(isPaused){
            isPaused = false;
            System.out.println("\nTimer resumed");
        }
    }


    //pre timer coundown 3.. 2.. 1.. TIMER STARTS NOW
    public void preTimer(JLabel label){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run(){
                if (preTimeRemaining > 0) {
                    //prints on the same line
                    System.out.printf("\rSTUDY SESSION BEGINNING IN: : %s", formatTime(preTimeRemaining));
                    setTime(formatTime(preTimeRemaining));
                    preTimeRemaining = preTimeRemaining - 1;
                    label.setText(String.valueOf(preTimeRemaining));
                } else {
                    System.out.println("\rTIMER STARTS NOW!!!!!");
                    timer.cancel();
                    ready = true;
                }
            }
        };

        //period parameter is measured in milliseconds
        timer.scheduleAtFixedRate(task, 0, 1000);

        //prevents the separate threads issue
        while (!ready) {
            try {
                //creates a small delay
                Thread.sleep(500); // Small delay to prevent CPU overuse
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String tCheck(String time){
        return time;
    }


    //STOPWATCH METHODS ------------------------------->
    public void startStopwatch(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run(){
                if (!isPaused) {
                        if (timeRemaining > 0) {
                        System.out.printf("\rTIME ELAPSED: %s", formatTime(timeElapsed));
                        setTime(formatTime(timeElapsed));
                        timeElapsed = timeElapsed + 1;

                    } else {
                        System.out.println("\nGood job!! Timer finished!");
                        timer.cancel();
                    }
                }
            }
        };
        //period parameter is measured in milliseconds
        timer.schedule(task, 0, 1000);
    }

    // <-------------------------------

    //COUNTDOWN METHODS ------------------------------->
    //method to set countdown duration
    //time returned aka timeRemaining is in seconds
    public int setCountdownDuration(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("How long is your timer? \n(Format HHMMSS or MMSS or SS) ");
        String formattedAmt = scanner.next();

        //makes sure that user didn't enter all 0s
        if(formattedAmt.matches("0+")){
            System.out.println("Invalid input - Stop slacking and start a proper countdown >:((((DDD");
            return 0;
        }

        //'30' = 30 seconds whilst '150' = 1 minute 50 seconds
        if(formattedAmt.length() <= 2){ // if SS , so just seconds
            int timeInSecs = Integer.parseInt(formattedAmt);
            //System.out.println(timeInSecs);
            timeRemaining = timeInSecs;
            setTimerDuration = timeRemaining;
            return timeRemaining;
        }else if((formattedAmt.length() > 2) && (formattedAmt.length() <= 4)){ //if input is MMSS
            //takes the first two characers (MM) and converts the minutes to seconds
            int minutes = Integer.parseInt(formattedAmt.substring(0,2));
            int seconds = Integer.parseInt(formattedAmt.substring(2,4));
            timeRemaining = (minutes * 60) + seconds;
            setTimerDuration = timeRemaining;
            return timeRemaining;
        }else if((formattedAmt.length() > 4) && (formattedAmt.length() <= 6)){ //input = HHMMSS
            int hours = Integer.parseInt(formattedAmt.substring(0,2));
            int minutes = Integer.parseInt(formattedAmt.substring(2,4));
            int seconds = Integer.parseInt(formattedAmt.substring(4,6));
            timeRemaining = (hours * 3600) + (minutes * 60) + seconds;
            setTimerDuration = timeRemaining;
            return timeRemaining;
        }else{
            System.out.println("INVALID INPUT");
            return 0;
        }
    }

    //starts countdown and displays how much time is left
    public void startCountdown(int timeRemaining){
        //makes sure you entered a proper time
        if(timeRemaining <= 0){
            System.out.println("Make set a valid time. ");
            return;
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run(){
                //checks if there's still time left
                if (!isPaused) {
                    if(timeElapsed < timeRemaining){
                        //System.out.println(timeElapsed);
                        //System.out.println(timeRemaining);
                        //makes sure it prints on one line
                        System.out.printf("\rTIME LEFT: %s", formatTime(returnTimeLeft()));
                    }else{
                        System.out.println();
                        System.out.println("Good job!! Timer finished! ");
                        timer.cancel();
                    }
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


    // <-------------------------------


    //formats time left in countdown as HH:MM:SS
    private String formatTime(int amtSeconds){
        int hoursLeft = amtSeconds /3600;
        int minsLeft = (amtSeconds %3600)/60;
        int secsLeft = amtSeconds %60;


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
