import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.*;


public class BreakManager {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int breakDuration;
    private Timer timer;
    private Timer breakTimer;
    //formatter for saving and reading the times
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    //la constructor
    public BreakManager(int duration) {
        this.startDate = LocalDate.now();
        this.startTime = LocalTime.now();
        this.breakDuration = duration;
    }

    public void startBreak(){
        final int[] remaining = {breakDuration}; //remaining minutes as array to allow modification inside lambda
        timer = new Timer(1000, e -> { //every second
            remaining[0]--; //decreases by 1
            System.out.println("Minutes left: " + remaining[0]);

            if (remaining[0] <= 0) {
                endBreak();
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
        System.out.println("Break started for " + breakDuration + " minutes");
    }

    //ending the break manually or when theh timer reaches zeroo
    public void endBreak(){
        this.endDate = LocalDate.now();
        this.endTime = LocalTime.now();
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        saveBreak();
        System.out.println("Break ended at " + endTime);
    }

    //converts it to the file
    public String toFileString(){
        String startTimeStr = startTime.format(TIME_FORMATTER);
        String endTimeStr = endTime.format(TIME_FORMATTER);
        //converts to CSV format
        return breakDuration + "," + startDate + "," + startTimeStr + "," + endDate + "," + endTimeStr;
    }

    //saves the break to the file
    public void saveBreak(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("break_data.txt", true))){
            writer.write(toFileString());
            writer.newLine();
            System.out.print("break saved");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //reading from the break_data.txt file to get data for graphing
    public static ArrayList<Break> readBreaks(){
        ArrayList<Break> result = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("break_data.txt"))){
            String line;
            while((line = reader.readLine())!= null){
                String[] parts = line.split(",");
                if(parts.length == 5){
                    //splitting the line into sections to be used individually
                    int breakLength = Integer.parseInt(parts[0]);
                    LocalDate startDate = LocalDate.parse(parts[1]);
                    LocalTime startTime = LocalTime.parse(parts[2], TIME_FORMATTER);
                    LocalDate endDate = LocalDate.parse(parts[3]);
                    LocalTime endTime = LocalTime.parse(parts[4], TIME_FORMATTER);

                    result.add(new Break(breakLength, startDate, startTime, endDate, endTime));
                }
            }
        }catch(IOException e){
            e.printStackTrace(); //handles file read errors
        }
        return result;
    }


}
