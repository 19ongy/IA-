import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalTime;

public class ReminderManager {
    private ArrayList<ReminderTime> studyReminders = new ArrayList<>();
    private Timer waterReminderTimer;
    private ReminderGUI reminderWindow = null;
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public void loadRemindersFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                String timeStr = parts[0].trim();
                String message = parts[1].trim();
                try {
                    LocalTime reminderTime = LocalTime.parse(timeStr, timeFormatter);
                    startStudyReminder(reminderTime, message);
                } catch (DateTimeParseException e) {
                    System.out.println("Skipping invalid time format: " + timeStr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startStudyReminder(LocalTime reminderTime, String message) {
        LocalTime now = LocalTime.now();
        Duration delay = Duration.between(now, reminderTime);
        if (delay.isNegative()) {
            delay = delay.plusHours(24); // schedule for next day if time has passed
        }
        long delayMillis = delay.toMillis();
        //long stores ints that are very big

        scheduler.schedule(() -> {
            SwingUtilities.invokeLater(() -> {
                if (reminderWindow != null) {
                    reminderWindow.dispose();
                }
                reminderWindow = new ReminderGUI("study", message);
            });
        }, delayMillis, TimeUnit.MILLISECONDS);
    }

    public void startWaterReminder(){
        if (waterReminderTimer != null) {
            waterReminderTimer.cancel(); // cancel any existing timer
        }
        waterReminderTimer = new Timer();

        //to be scheduled first reminder after 10 seconds, then every 30 minutes
        waterReminderTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    System.out.println("Water reminder triggered at " + LocalTime.now());
                    if (reminderWindow != null) {
                        reminderWindow.dispose();
                    }
                    reminderWindow = new ReminderGUI("waterRem", null);
                });
            }
        }, 10000, 30 * 60 * 1000); // 10s delay, 30min interval
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public ReminderTime convertTime(String time) {
        if (time.length() != 4) {
            System.out.println("INVALID INPUT");
            return null;
        } else {
            int hourInput = Integer.parseInt(time.substring(0, 2));
            int minInput = Integer.parseInt(time.substring(2, 4));

            //checks whether hour is above 24 and min is above 60
            if ((hourInput > 24) || (minInput >= 60)) {
                System.out.println("INVALID INPUT");
                return null;
            } else {
                return new ReminderTime(hourInput, minInput);
            }
        }

    }

    public boolean hasReminders() {
        return !studyReminders.isEmpty();
    }

    public String formatData(String inputTime, String message) {
        return inputTime + "," + message;
    }

    public String formatTime(String time) {
        ReminderTime rt = convertTime(time);
        return String.format("%02d:%02d", rt.getHour(), rt.getMin());
    }

    public void addReminder(String time, String message) {
        String setTime = formatTime(time);
        if (setTime != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("reminder_data.txt", true))) {
                writer.write(formatData(setTime, message));
                writer.newLine(); // Adds a newline after each session
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //helper method to get all of the reminders into one array list
    public List<String> loadReminders() {
        List<String> reminders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("reminder_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reminders.add(line);
            }
        } catch (IOException e) {
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

    public void deleteReminder(int remToDelete, JPanel reminderPanel, List<String> reminders) {
        if (remToDelete >= 1 && remToDelete <= reminders.size()) {
            reminders.remove(remToDelete - 1);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reminder_data.txt"))) {
            for (String reminder : reminders) {
                writer.write(reminder);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateReminderList(reminderPanel, reminders);
    }

    public void replaceReminder(int remToReplace, String timeReplace, String messageReplace) {
        List<String> totReminders = loadReminders();

        if (remToReplace >= 1 && remToReplace <= totReminders.size()) {
            String formattedInput = formatTime(timeReplace) + "," + messageReplace;
            totReminders.set(remToReplace - 1, formattedInput);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reminder_data.txt"))) {
            for (String reminder : totReminders) {
                writer.write(reminder);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(JPanel reminderPanel, List<String> reminders) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reminder_data.txt"))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        reminders.clear();
        reminderPanel.removeAll();
        updateReminderList(reminderPanel, reminders);
    }

    public int getDataLength() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("reminder_data.txt"))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}