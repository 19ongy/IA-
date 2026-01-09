import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/*
pomodoro controls the order of study and break sessions
index progression
 */

public class Pomodoro {
    private Timer timer;
    private GUI gui;
    private JLabel timerLabel;
    private int[] allDurations; //length in seconds
    public String[] types; //study or break identity
    public int index = 0;
    public SessionManager sesh;


    public Pomodoro(GUI gui, Timer timer, JLabel timerLabel, int[] allDurations, String[] studyOrBreak ){
        this.timer = timer;
        this.timerLabel = timerLabel;
        this.allDurations = allDurations;
        this.types = studyOrBreak;
        this.gui = gui;
    }

    private void endPomodoro() {
        // stopS everything
        timer.stopCountdown();
        timer.stopBreak();
        // reset timer display
        if (timerLabel != null) {
            timerLabel.setText("Pomodoro finished!");
        }
        // reset index if you want to allow restart
        index = 0;
        gui.cardLayout.show(gui.cardPanel, "Session");
        System.out.println("All Pomodoro sessions complete!");
    }

    //prepare for the next session (study or break)
    public void prepareNextSession() {
              //hard code it so when it gets to the last point in the array, the timer stops afterwards
        if(index >= allDurations.length) {
            endPomodoro();
            return; // finished all sessions
        }
        if(types[index].equals("Study")) {
            startStudySession();
        } else {
            startBreakSession();
        }
    }

    //starting a study session
    public void startStudySession() {
        timer.stopCountdown();
        timer.stopBreak();
        int duration = allDurations[index];

        //create new not yet saved session
        sesh = new SessionManager();
        sesh.setSubject("Pomodoro");
        sesh.setSessionLength(duration);
        sesh.setStartDate();
        sesh.setStartTime();

        gui.cardLayout.show(gui.cardPanel, "Session");

        timer.startCountdown(duration, timerLabel, () -> {
            System.out.println("such a skill issue");
//will go to post mood screen and save there
            SwingUtilities.invokeLater(() ->
                    gui.cardLayout.show(gui.cardPanel, "pMood")
            );
        });
    }

    //saving all the data
    public void finishCurrentSession(String moodAfter, int actualLength){
        //safety check
        if(sesh == null){
            return;
        }

        sesh.setMoodAfter(moodAfter);
        sesh.setEndDate();
        sesh.setEndTime();
        sesh.setSessionLength(actualLength);
        sesh.saveSession();

        index = index + 1;
        if(index >= allDurations.length){
            endPomodoro();
        }else{
            prepareNextSession();
        }
    }

    //starting break loop
    public void startBreakSession() {
        timer.stopCountdown();
        timer.stopBreak();
        int duration = allDurations[index];

        //creates sessionmanager for the break
        sesh = new SessionManager();
        sesh.setSessionLength(duration);
        sesh.setSubject("Break");
        sesh.setStartDate();
        sesh.setStartTime();

        gui.cardLayout.show(gui.cardPanel, "Session");

        timer.startBreak(duration, timerLabel, () -> {
            SwingUtilities.invokeLater(() ->
                    gui.cardLayout.show(gui.cardPanel, "pMood"));
        });
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

    //forcefully ending the session
    public void forceEndingCurrentSession(String mood){
        if(sesh!= null){
            if(mood == null){
                mood = "SKIP"; //safety level
            }
            sesh.setMoodAfter(mood);
            sesh.setEndDate();
            sesh.setEndTime();
            sesh.setSessionLength(timer.timeElapsed);
            sesh.saveSession();
        }

        index = allDurations.length;// stops the next session from starting if there is one
        endPomodoro();
    }
}
