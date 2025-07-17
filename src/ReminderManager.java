import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReminderManager {
    private ArrayList<ReminderTime> studyReminders = new ArrayList<>();
    private Timer waterReminderTimer;
    private ReminderGUI reminderWindow = null;
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private String timeOfReminder;
    private String remMessage;

    public void loadRemindersFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2); // limit to 2 parts
                if (parts.length < 2) continue;

                String timeStr = parts[0].trim();
                String message = parts[1].trim();

                try {
                    LocalTime reminderTime = LocalTime.parse(timeStr, timeFormatter);
                    scheduleReminder(reminderTime, message);
                } catch (DateTimeParseException e) {
                    System.out.println("Skipping invalid time format: " + timeStr);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scheduleReminder(LocalTime reminderTime, String message) {
        LocalTime now = LocalTime.now();
        Duration delay = Duration.between(now, reminderTime);

        if (delay.isNegative()) {
            delay = delay.plusHours(24); // schedule for next day if time has passed
        }

        long delayMillis = delay.toMillis();

        scheduler.schedule(() -> {
            System.out.println("🔔 Reminder: " + message + " (" + reminderTime + ")");
        }, delayMillis, TimeUnit.MILLISECONDS);
        reminderWindow.sendStudyRem(message);
    }

    public void shutdown(){
        scheduler.shutdown();
    }




    //TODO cry cry cry need to find a way to make the times as --:--:00 in text file
    //ALSO checkTime needs to be cnstnatly running in the background then? to constantly check the time
    //surely there is a better way

    public void checkTime(){
        LocalTime timeNow = LocalTime.now();
        //String formattedTimeNow = String.format("%02d:%02d:", );
        LocalTime cTime = LocalTime.parse("13:52:00");
        System.out.println(timeNow);

    }


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


    //writing to the data file
    public String formatData(String inputTime, String message){
        return inputTime + "," + message;
    }

    public String formatTime(String time){
        ReminderTime rt = convertTime(time);
        String formattedTime = String.format("%02d:%02d", rt.getHour(), rt.getMin());
        return formattedTime;
    }

    public void addReminder(String time, String message) {
        String setTime = formatTime(time);
        if(setTime != null){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("reminder_data.txt", true))) {
                writer.write(formatData(setTime, message));
                writer.newLine(); // Adds a newline after each session
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //reading from the data file
    public void readFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader("reminder_data.txt"))){
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(",", 2);
                if(parts.length ==2){
                    this.timeOfReminder = parts[0];
                    this.remMessage = parts[1];
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //helper method to get all of the reminders into one array list
    public List<String> loadReminders(){
        List<String> reminders = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("reminder_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reminders.add(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return reminders;
    }

    public void updateReminderList(JPanel reminderPanel, List<String> reminders) {
        reminderPanel.removeAll();
        int i = 1;
        for (String reminder : reminders) {
            JLabel reminderLabel = new JLabel("Reminder " + i + "  :  " + reminder);
            reminderLabel.setForeground(new Color(200, 200, 200));
            reminderLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            reminderLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            reminderPanel.add(reminderLabel);
            i = i + 1;
        }
        reminderPanel.revalidate();
        reminderPanel.repaint();
    }

    public void deleteReminder(int remToDelete, JPanel reminderPanel, List<String> reminders){
        if(remToDelete >= 1 && remToDelete <= reminders.size()){
            reminders.remove(remToDelete - 1);
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("reminder_data.txt"))){
            for(String reminder : reminders){
                writer.write(reminder);
                writer.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        updateReminderList(reminderPanel, reminders);
    }

    public void replaceReminder(int remToReplace, String timeReplace, String messageReplace){
        List<String> totReminders = loadReminders();

        if(remToReplace >= 1 && remToReplace <= totReminders.size()){
            String formattedInput = formatTime(timeReplace) + "," + messageReplace;
            totReminders.set(remToReplace -1, formattedInput);
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("reminder_data.txt"))){
            for(String reminder : totReminders){
                writer.write(reminder);
                writer.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void deleteAll(JPanel reminderPanel, List<String> reminders){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("reminder_data.txt"))){
            writer.write("");
        }catch (IOException e){
            e.printStackTrace();
        }
        reminders.clear();
        reminderPanel.removeAll();
        updateReminderList(reminderPanel, reminders);
    }

    public String getTimeOfRem(){
        System.out.println(timeOfReminder);
        return this.timeOfReminder;
    }

    public String getRemMessage(){
        System.out.println("hi" + remMessage);
        return this.remMessage;
    }

    public int getDataLength(){
        int count = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader("reminder_data.txt"))){
            while(reader.readLine() != null){
                count++;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return count;
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
