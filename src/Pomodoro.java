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

    public Pomodoro(GUI gui, Timer timer, JLabel timerLabel, int[] allDurations, String[] studyOrBreak ){
        this.timer = timer;
        this.timerLabel = timerLabel;
        this.allDurations = allDurations;
        this.types = studyOrBreak;
        this.gui = gui;
    }

    //prepare for the next session (study or break)
    public void prepareNextSession() {
        timer.stopCountdown();
        timer.stopBreak();

        if(index >= allDurations.length) {
            index = 0;
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
            gui.cardLayout.show(gui.cardPanel, "pMood");
            index = index + 1;
            prepareNextSession();
        });
    }

        /*
        timer.startCountdown(duration, timerLabel);

        new javax.swing.Timer(1000, e -> {
            if (timer.timeRemaining - timer.timeElapsed <= 0) {
                ((javax.swing.Timer) e.getSource()).stop();
                gui.cardLayout.show(gui.cardPanel, "pMood");
            }
        }).start();

         */


    public void startBreakSession() {
        timer.stopCountdown();
        timer.stopBreak();
        int duration = allDurations[index];
        BreakManager breakManager = new BreakManager(duration);

        gui.cardLayout.show(gui.cardPanel, "Session");

        timer.startBreak(duration, timerLabel, () -> {
            breakManager.endBreak();
            breakManager.saveBreak();
            index++;
            prepareNextSession();
        });
    }

    /*
        new javax.swing.Timer(1000, e -> {
            if (timer.breakTimeLeft <= 0) {
                ((javax.swing.Timer) e.getSource()).stop();
                breakManager.endBreak();
                breakManager.saveBreak();
                index = index + 1;

                if (index < types.length) {
                    if (types[index].equals("Study")) {
                        prepareStudySession();
                    } else {
                        startBreakSession();
                    }
                }
            }
        }).start();
    }

     */

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
