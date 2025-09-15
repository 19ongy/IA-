import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Pomodoro {
    private Timer timer;
    private GUI gui;
    private JLabel timerLabel;
    private int[] allDurations;
    private String[] types;
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

    public void prepareStudySession(){
        if(index >= allDurations.length) {
            index = 0;
            return;
        }
        String type = types[index];
        if (type.equals("Study")) {
            sesh = new SessionManager();
            sesh.setSessionLength(allDurations[index]);
            sesh.setSubject("Pomodoro");
            sesh.setStartDate();
            sesh.setStartTime();
            gui.cardLayout.show(gui.cardPanel, "bMood");
        }else{
            startBreakSession();
        }
    }

    public void startStudySession(){
        int duration = allDurations[index];
        gui.cardLayout.show(gui.cardPanel, "Session");
        gui.cardPanel.revalidate();
        gui.cardPanel.repaint();

        timer.startCountdown(duration, timerLabel);

        new javax.swing.Timer(1000, e -> {
            if (timer.timeRemaining - timer.timeElapsed <= 0) {
                ((javax.swing.Timer) e.getSource()).stop();
                gui.cardLayout.show(gui.cardPanel, "pMood");
            }
        }).start();
    }

    public void startBreakSession(){
        int duration = allDurations[index];
        BreakManager breakManager = new BreakManager(duration);
        timer.startBreak(duration, timerLabel);
        gui.cardLayout.show(gui.cardPanel, "Session");

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
        }}.start();
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
