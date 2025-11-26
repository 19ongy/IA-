import javax.swing.*;
import java.awt.event.ActionListener;

public class Timer {
    private int startHour = 0;
    private int startMin = 0;
    private int endHour = 0;
    private int endMin = 0;
    int hourStudied = 0;
    int minStudied = 0;

    public int timeElapsed;
    public int timeRemaining;
    public boolean isPaused;
    public boolean isEnded;

    private int preTimeRemaining;
    public int setTimerDuration;

    private int savedTimeRemaining;
    private int savedTimeElapsed;

    public int breakTimeLeft;
    private boolean onBreak = false;

    public String time;
    javax.swing.Timer stopwatchTimer = new javax.swing.Timer(1000, null);
    javax.swing.Timer countdownTimer = new javax.swing.Timer(1000, null);
    javax.swing.Timer breakTimer = new javax.swing.Timer(1000, null);
    SessionManager manager = new SessionManager();

    //constructor
    public Timer(){
        this.timeElapsed = 0;
        this.timeRemaining = 0;
        preTimeRemaining = 3;
        setTimerDuration = 0;
        isPaused = false;
        isEnded = false;
    }

    //pauses the timer
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

    //ending timer on the label
    public void endT(JLabel label){
        if(manager != null){
            manager.setEndDate();
            manager.setEndTime();
        }
        isEnded = true;
        if(label != null){
            label.setText("timer ended");
        }
    }

    //method for ending the timer internally
    public void stopCountdown(){
        countdownTimer.stop();
    }

    //stopping break timers safely
    public void stopBreak() {
        if (breakTimer != null) {
            breakTimer.stop();
        }
    }


    //starting timer countdown
    public void preTimer(JLabel label){
        if(label == null){
            return;
        }

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

    //setting up a stopwatch
    public void startStopwatch(JLabel label) {
        if(label == null){
            return;
        }

        countdownTimer.stop();
        //clears all previous action listeners/ timers so it doesn't overlap
        for(ActionListener acli : stopwatchTimer.getActionListeners()){
            stopwatchTimer.removeActionListener(acli);
        }

        if(manager != null){
            manager.setStartDate();
            manager.setStartTime();
        }

        timeElapsed = 0;
        isEnded = false;
        isPaused = false;

        System.out.println("stopwatch hs begun");
        stopwatchTimer.addActionListener(e -> {
            if (isEnded) {
                //checks if the user has ended the timer
                label.setText("Timer Ended!");
                stopwatchTimer.stop();
            } else if (!isPaused) {
                //checks if user has paused the timer
                label.setText(formatTime(timeElapsed));  // update label
                label.repaint();
                //label = GUI display screen --> it's updated every second
                timeElapsed = timeElapsed + 1;
            }
        });
        stopwatchTimer.start();
    }


    //setting up the countdown
    public void startCountdown(int timeRemaining, JLabel label, Runnable onFinish){
        if(label == null){
            return;
        }

        this.timeRemaining = timeRemaining;
        System.out.println(timeRemaining);
        this.timeElapsed = 0;
        isEnded = false;
        isPaused = false;

        //checks whether a countdown timer amount has been set yet
        if(timeRemaining<=0){
            JOptionPane.showMessageDialog(null, "you havne't set a time yet!", "whaaattt", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //stops the timer
        countdownTimer.stop();
        //removes all the previous action listeners so it counts down at 1 second intervals after a new countdown
        for (ActionListener al : countdownTimer.getActionListeners()) {
            countdownTimer.removeActionListener(al);
        }

        if(manager != null){
            manager.setStartDate();
            manager.setStartTime();
        }

        countdownTimer.addActionListener(e -> {
            if (isEnded) {
                label.setText("Timer Ended!");
                //collects the date and time information to make graphs with
                manager.setEndDate();
                manager.setEndTime();
                countdownTimer.stop();
                if(onFinish != null){
                    onFinish.run();
                }
            } else if (!isPaused) {
                int timeLeft = this.timeRemaining - this.timeElapsed;
                if (timeLeft > 0) {
                    label.setText(formatTime(timeLeft));
                    timeElapsed = timeElapsed + 1;
                    //increments by 1 second
                } else {
                    timeElapsed = timeRemaining;
                    countdownTimer.stop();
                    manager.setEndDate();
                    manager.setEndTime();
                    label.setText("Good job!! Timer finished!");
                    if(onFinish != null){
                        onFinish.run();
                    }
                }
            }
        });
        countdownTimer.start();
    }

    //setting up a break
    public void startBreak(int breakDuration, JLabel label, Runnable onFinish) {
        if (label == null) {
            return;
        }

        breakTimeLeft = breakDuration;
        onBreak = true;

        //saves all the previous timer data
        savedTimeRemaining = this.timeRemaining;
        savedTimeElapsed = this.timeElapsed;

        //stops the current counter
        if (breakTimer != null) {
            breakTimer.stop();
            for(ActionListener al : breakTimer.getActionListeners()) {
                breakTimer.removeActionListener(al);
            }
        }
        BreakManager breakManager = new BreakManager(breakDuration);
        breakTimer = new javax.swing.Timer(1000, e -> {
            if (breakTimeLeft > 0) {
                label.setText("Break: " + formatTime(breakTimeLeft));
                breakTimeLeft = breakTimeLeft - 1;
            } else {
                breakTimer.stop();
                label.setText("break over ! ");
                breakManager.endBreak();
                breakManager.saveBreak();
                onBreak = false;

                //resumeStudy(label);
                //checks whether there's a next session
                if(onFinish != null){
                    onFinish.run();
                }
            }
        });
        breakTimer.start();
    }

    //finding total amount of time in seconds, based off what the user inputted
    public int formatTime(String formattedAmt){
        //makes sure that user didn't enter all 0s
        if(formattedAmt.matches("0+")){
            System.out.println("Invalid input - Stop slacking and start a proper countdown >:((((DDD");
            return 0;
        }
        //'30' = 30 seconds whilst '0150' = 1 minute 50 seconds
        //splits the string input based on their positions to respective hours, minutes, and seconds
        if(formattedAmt.length() <= 2){ //
            return Integer.parseInt(formattedAmt);
        }else if((formattedAmt.length() > 2) && (formattedAmt.length() <= 4)){
            int minutes = Integer.parseInt(formattedAmt.substring(0,2));
            int seconds = Integer.parseInt(formattedAmt.substring(2,4));
            int total = (minutes*60) + (seconds); //converts it all to seconds
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

    //method calculates how much time is left in countdown
    private int returnTimeLeft(){
        timeElapsed = timeElapsed + 1;
        return timeRemaining - timeElapsed;
    }

    //formats time left in countdown as HH:MM:SS so that it can be displayed in the GUI screen
    private String formatTime(int amtSeconds){
        int hoursLeft = amtSeconds /3600;
        int minsLeft = (amtSeconds %3600)/60;
        int secsLeft = amtSeconds %60;

        String timeDisplay = String.format("%02d:%02d:%02d", hoursLeft, minsLeft, secsLeft);
        return timeDisplay;
    }

    //starts the normal countdown/stopwatch timer again after break
    private void resumeStudy(JLabel label){
        isPaused = false;
        onBreak = false;
        if(savedTimeRemaining > 0){
            startCountdown(savedTimeRemaining, label, ()-> {});
            this.timeElapsed = savedTimeElapsed;
        }
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
