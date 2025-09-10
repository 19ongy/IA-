import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Pomodoro {
    private Timer timer;
    private GUI gui;
    private JLabel displayLabel;
    private int[] allDurations;
    private String[] types;
    public int index = 0;
    SessionManager sesh;

    public Pomodoro(GUI gui, Timer timer, JLabel label, int[] allDurations, String[] studyOrBreak ){
        this.timer = timer;
        this.displayLabel = label;
        this.allDurations = allDurations;
        this.types = studyOrBreak;
        this.gui = gui;
    }

    //pomo start
    public void startPomo() {
        //makes sure there aren't multiple timers
        timer.stopwatchTimer.stop();
        timer.countdownTimer.stop();
        timer.breakTimer.stop();
        timer.isPaused = false;
        timer.isEnded = false;

        String type = types[index];
        int duration = allDurations[index];

        if (index < allDurations.length) {
            if (type.equals("Study")) {                //saving it in sessionManager data
                SessionManager sesh = new SessionManager();
                sesh.setSessionLength(duration / 60);
                sesh.setSubject("Pomodoro");
                sesh.setStartDate();
                sesh.setStartTime();

                sesh.setMoodBefore("SKIP");
                sesh.setMoodAfter("SKIP");

                //timer starts after user selects mood
                gui.cardLayout.show(gui.cardPanel, "bMood");

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

    public void setBeforeMood(String mood){
        if(sesh != null){
            sesh.setMoodBefore(mood);
        }

        //starts countdown
        int duration = allDurations[index];
        timer.startCountdown(duration, displayLabel);

        new javax.swing.Timer(1000, e -> {
            javax.swing.Timer t = (javax.swing.Timer) e.getSource();
            if (!timer.isPaused && ((timer.timeRemaining - timer.timeElapsed) <= 0)) {
                //gets mood after session
                gui.cardLayout.show(gui.cardPanel, "pMood");
                startPomo();

                t.stop();
            }
        }).start();
    }

    //setting session details after user selects final mood
    public void setAfterMood(String mood){
        if(sesh != null){
            sesh.setMoodAfter(mood);
            sesh.setEndDate();
            sesh.setEndTime();
            sesh.saveSession();
        }

        index = index + 1;
        startPomo();
    }

    //change pomodoro settings
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
