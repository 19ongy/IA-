import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

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


}
