import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Pomodoro {
    private Timer timer;
    private GUI gui;
    private JLabel timerLabel;
    private int[] allDurations;
    public String[] types;
    public int index = 0;
    public SessionManager sesh;

    private String moodBefore;
    private String moodAfter;

    //constructor
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
            System.out.println("stopping everything");
        }
        // reset index if you want to allow restart
        index = 0;
        gui.cardLayout.show(gui.cardPanel, "pMood");
        System.out.println("All Pomodoro sessions complete!");
    }

    //prepare for the next session (study or break)
    public void prepareNextSession() {
        System.out.println("this is working");
        timer.stopCountdown();
        timer.stopBreak();

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

        sesh = new SessionManager();
        sesh.setSessionLength(duration);
        sesh.setSubject("Pomodoro");
        sesh.setStartDate();
        sesh.setStartTime();

        gui.cardLayout.show(gui.cardPanel, "Session");
        gui.cardPanel.revalidate();
        gui.cardPanel.repaint();

        timer.startCountdown(duration, timerLabel, () -> {
            //saving the details for later to be stored
            sesh.setEndDate();
            sesh.setEndTime();
            sesh.saveSession();
            index = index + 1;
            if(index >= allDurations.length) {
                SwingUtilities.invokeLater(() -> {
                    endPomodoro();
                    //if the number of sessions has ended, end all the timers
                });
            } else{
                SwingUtilities.invokeLater(() -> {
                    prepareNextSession();
                    //if there are still sessions reamining, continue preparing for the next one
                });
            }
        });
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
            sesh.setEndDate();
            sesh.setEndTime();
            sesh.saveSession();

            index++; //increment before starting the next session
            //checks whether it's the final break
            if(index >= allDurations.length) {
                SwingUtilities.invokeLater(() -> {
                    endPomodoro();
                });
            }else{
                SwingUtilities.invokeLater(() -> {
                    prepareNextSession();

                });
            }
        });
    }

    //change pomodoro settings for test plan 6th point
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
