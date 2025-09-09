import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Pomodoro {
    private Timer timer;
    private GUI gui;
    private JLabel displayLabel;
    private int[] allDurations;
    private String[] types;
    private int index = 0;

    public Pomodoro(Timer timer, JLabel label, int[] allDurations, String[] studyOrBreak ){
        this.timer = timer;
        this.displayLabel = label;
        this.allDurations = allDurations;
        this.types = studyOrBreak;
    }

    //pomo start
    public void startPomo() {
        //makes sure there aren't multiple timers
        timer.stopwatchTimer.stop();
        timer.countdownTimer.stop();
        timer.breakTimer.stop();
        timer.isPaused = false;
        timer.isEnded = false;

        if (index < allDurations.length) {
            String type = types[index];
            int duration = allDurations[index];

            if (type.equals("Study")) {
                //saving it in sessionManager data
                SessionManager sesh = new SessionManager();
                sesh.setSessionLength(duration / 60);
                sesh.setSubject("Pomodoro");
                sesh.setStartDate();
                sesh.setStartTime();

                sesh.setMoodBefore("SKIP");
                sesh.setMoodAfter("SKIP");

                //starts study countdown
                timer.startCountdown(duration, displayLabel);

                new javax.swing.Timer(1000, e -> {
                    javax.swing.Timer t = (javax.swing.Timer) e.getSource();

                    if (!timer.isPaused && ((timer.timeRemaining - timer.timeElapsed) <= 0)) {
                        sesh.setEndDate();
                        sesh.setEndTime();
                        sesh.saveSession();

                        index = index + 1;
                        startPomo();

                        //e.getSource() is the swing timer
                        ((javax.swing.Timer) e.getSource()).stop();
                    }
                }).start();

            } else { //if its not "Study" but "Break"
                BreakManager breakManager = new BreakManager(duration);
                timer.startBreak(duration, displayLabel);

                new javax.swing.Timer(1000, e -> {
                    javax.swing.Timer t = (javax.swing.Timer) e.getSource();

                    if (!timer.isPaused && (timer.breakTimeLeft <= 0)) {
                        breakManager.endBreak();
                        breakManager.saveBreak();

                        index = index + 1;
                        startPomo();

                        t.stop();
                        //makes sure there aren't multiple instances of t
                    }
                }).start();
            }
        }
    }

    //pomo change
    public void changePomo(int sessionLength, int shortBreak, int longBreak, int numLoops){
        List<Integer> listOfDurations = new ArrayList<>();
        List<String> listOfTypes = new ArrayList<>();

        for(int i = 0; i < numLoops; i++){
            listOfDurations.add(sessionLength);
            listOfTypes.add("Study");

            if (i == numLoops-1){
                listOfDurations.add(longBreak*60);
                listOfTypes.add("Break");
            }else{
                listOfDurations.add(shortBreak*60);
                listOfTypes.add("Break");
            }
        }

    //converting the integer array list into an int
        allDurations = listOfDurations.stream().mapToInt(Integer::intValue).toArray();
        types = listOfTypes.toArray(new String[0]);
        index = 0;
    }
}
