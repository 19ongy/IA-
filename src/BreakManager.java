import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BreakManager {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int breakDuration;

    //la constructor
    public BreakManager(int duration) {
        this.startDate = LocalDate.now();
        this.startTime = LocalTime.now();
        this.breakDuration = duration;
    }

    //ending the break
    public void endBreak(){
        this.endDate = LocalDate.now();
        this.endTime = LocalTime.now();
        saveBreak();
    }

    //converts it to the file
    public String toFileString(){
        String startTimeStr = startTime.getHour() + ":" + startTime.getMinute();
        String endTimeStr = endTime.getHour() + ":" + endTime.getMinute();
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
                    int breakLength = Integer.parseInt(parts[0]);
                    LocalDate startDate = LocalDate.parse(parts[1]);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
                    LocalTime startTime = LocalTime.parse(parts[2], formatter);
                    LocalDate endDate = LocalDate.parse(parts[3]);
                    LocalTime endTime = LocalTime.parse(parts[4], formatter);
                    result.add(new Break(breakLength, startDate, startTime, endDate, endTime));
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return result;
    }


}
