import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.Timer;

public class SetTimer {
    private int startHour = 0;
    private int startMin = 0;
    private int endHour = 0;
    private int endMin = 0;
    int hourStudied = 0;
    int minStudied = 0;

    private int timeElapsed;
    private int timeRemaining;
    private boolean ready;
    private int preTimeRemaining;
    public int setTimerDuration;
    public boolean isPaused;
    public boolean isEnded;

    public String time;
    Timer stopwatchTimer = new javax.swing.Timer(1000, null);
    Timer countdownTimer = new javax.swing.Timer(1000, null);
    SessionManager manager = new SessionManager();


    //constructor
    public SetTimer(){
        this.timeElapsed = 0;
        this.timeRemaining = 0;
        ready = false;
        preTimeRemaining = 3;
        setTimerDuration = 0;
        isPaused = false;
        isEnded = false;
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

    public void endT(JLabel label){
        manager.setEndDate();
        manager.setEndTime();
        isEnded = true;
    }


    //pre timer coundown 3.. 2.. 1.. TIMER STARTS NOW
    public void preTimer(JLabel label){
        javax.swing.Timer[] preTimer = new javax.swing.Timer[1]; //creates an array with 1 slot to hold my timer in
        preTimer[0] = new javax.swing.Timer(1000, null); //creates the timer instance that counts every second

        preTimer[0].addActionListener(e -> {
            if (preTimeRemaining > 0) {
                label.setText(formatTime(preTimeRemaining));
                preTimeRemaining = preTimeRemaining -1;
            } else {
                label.setText("TIMER STARTS NOW!!!!!");
                preTimer[0].stop(); // stop the timer
            }
        });

        preTimer[0].start();
    }

    public String tCheck(String time){
        return time;
    }

    /*to do list 08.07
    make an isEnd to check whether the user has clicked the end button on the gui yet
    find a way to just pause the timer

     */

    public int getTimeElapsed(){
        return timeElapsed;
    }

    //STOPWATCH METHODS ------------------------------->
    public void startStopwatch(JLabel label) {
        timeElapsed = 0;
        isEnded = false;
        isPaused = false;

        System.out.println("stopwatch hs begun");
        stopwatchTimer.addActionListener(e -> {
            if (isEnded) {
                label.setText("Timer Ended!");
                stopwatchTimer.stop();
            } else if (!isPaused) {
                label.setText(formatTime(timeElapsed));  // update label
                label.repaint();
                timeElapsed = timeElapsed + 1;
            }
        });
        stopwatchTimer.start();
    }

    // <-------------------------------

    //COUNTDOWN METHODS ------------------------------->
    //method to set countdown duration
    //time returned aka timeRemaining is in seconds
    public int formatTime(String formattedAmt){
        //makes sure that user didn't enter all 0s
        if(formattedAmt.matches("0+")){
            System.out.println("Invalid input - Stop slacking and start a proper countdown >:((((DDD");
            return 0;
        }
        //'30' = 30 seconds whilst '150' = 1 minute 50 seconds
        if(formattedAmt.length() <= 2){ // if SS , so just seconds
            return Integer.parseInt(formattedAmt);
        }else if((formattedAmt.length() > 2) && (formattedAmt.length() <= 4)){ //if input is MMSS
            //takes the first two characers (MM) and converts the minutes to seconds
            int minutes = Integer.parseInt(formattedAmt.substring(0,2));
            int seconds = Integer.parseInt(formattedAmt.substring(2,4));
            int total = (minutes*60) + (seconds);
            return total;
        }else if((formattedAmt.length() > 4) && (formattedAmt.length() <= 6)){ //input = HHMMSS
            int hours = Integer.parseInt(formattedAmt.substring(0,2));
            int minutes = Integer.parseInt(formattedAmt.substring(2,4));
            int seconds = Integer.parseInt(formattedAmt.substring(4,6));;
            int total = (hours*3600) + (minutes*60) + (seconds);
            return total;
        }else{
            System.out.println("INVALID INPUT");
               return 0 ;
        }
    }



    //whats it doing is
    //starts countdown and displays how much time is left
    public void startCountdown(int timeRemaining, JLabel label){
        timeElapsed = 0;
        isEnded = false;
        isPaused = false;

        if(timeRemaining<=0){
            JOptionPane.showMessageDialog(null, "you havne't set a time yet!", "whaaattt", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (countdownTimer.isRunning()) {
            countdownTimer.stop();
            for (ActionListener al : countdownTimer.getActionListeners()) {
                countdownTimer.removeActionListener(al);
            }
        }

        countdownTimer.addActionListener(e -> {
            if (isEnded) {
                label.setText("Timer Ended!");
                manager.setEndDate();
                manager.setEndTime();
                countdownTimer.stop();
            } else if (!isPaused) {
                int timeLeft = timeRemaining - timeElapsed;
                if (timeLeft > 0) {
                    label.setText(formatTime(timeLeft));
                    timeElapsed = timeElapsed + 1;
                } else {
                    label.setText("Good job!! Timer finished!");
                    manager.setEndDate();
                    manager.setEndTime();
                    countdownTimer.stop();
                }
            }
        });
        countdownTimer.start();
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
