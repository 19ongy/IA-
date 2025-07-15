import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderManager {
    private ArrayList<ReminderTime> studyReminders = new ArrayList<>();
    private Timer waterReminderTimer;
    private ReminderGUI reminderWindow = null;
    public ArrayList<ReminderTime> getStudyReminders() {
        return studyReminders;
    }

    public void setStudyReminders(ArrayList<ReminderTime> studyReminders) {
        this.studyReminders = studyReminders;
    }

    //converts user String input in format HHMM to hour and minute ints
    public ReminderTime convertTime(String time){
        if(time.length() != 4){
            System.out.println("INVALID INPUT");
            return null;
        }else{
            int hourInput = Integer.parseInt(time.substring(0, 2));
            int minInput = Integer.parseInt(time.substring(2,4));

            //checks whether hour is above 24 and min is above 60
            if((hourInput > 24) || (minInput >= 60)){
                System.out.println("INVALID INPUT");
                return null;
            }else{
                return new ReminderTime(hourInput, minInput);
            }
        }
    }

    //checks whether there are reminders set first
    public boolean hasReminders(){
        if(studyReminders.size() != 0){
            return true;
        }else{
            return false;
        }
    }

    //displays all the reminders set with a number ( i ) next to it
    public String displayReminders() {
        StringBuilder result = new StringBuilder("STUDY REMINDERS SET AT:\n");
        for (int i = 0; i < studyReminders.size(); i++) {
            int hour = studyReminders.get(i).getHour();
            int min = studyReminders.get(i).getMin();
            result.append("( ")
                    .append(i + 1)
                    .append(" ) ")
                    .append(String.format("%02d : %02d", hour, min))
                    .append("\n");
        }
        return result.toString();
    }

    public void addReminder(String time) {
        ReminderTime rt = convertTime(time);
        if(rt != null){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("reminder_data", true))) {
                String formattedTime = String.format("%02d:%02d", rt.getHour(), rt.getMin());
                writer.write(formattedTime);
                writer.newLine(); // Adds a newline after each session
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteReminder(int remToDelete){
        displayReminders();

        studyReminders.remove(remToDelete -1);
        System.out.println("deleted");
    }


    public void replaceReminder(int remToReplace, String time){
        if(remToReplace >= 1 && remToReplace <= studyReminders.size()){
            studyReminders.remove(remToReplace -1);
            studyReminders.add(remToReplace-1, convertTime(time));
        }else{
            System.out.println("You have entered an invalid number.");
        }
    }


    //deletes all reminders set previously
    public void deleteAllReminders(){
        //for(int i = 0; i<studyReminders.size();i++){
        //    deleteReminder(i);
        for (int i = studyReminders.size() - 1; i >= 0; i--) {
            deleteReminder(i + 1); // +1 if your method uses 1-based index
        }
    }


    //water reminders
    public void startWaterReminders(){
        if (waterReminderTimer != null) {
            waterReminderTimer.cancel();
        }
        waterReminderTimer = new Timer();
        waterReminderTimer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (reminderWindow != null) {
                        reminderWindow.dispose();
                    }
                    reminderWindow = new ReminderGUI();
                });
            }
        }, 0, 60*30*1000);
    }

    public void stopWaterReminders(){
        if(waterReminderTimer != null){
            waterReminderTimer.cancel();
            waterReminderTimer = null;
        }
    }

}
