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
        this.endDate = LocalDate.now();
        this.startTime = LocalTime.now();
        this.endTime = LocalTime.now().plusSeconds(duration);
        this.breakDuration = duration;
    }

    //converts it to the file
    public String toFileString(){
        return breakDuration + "," + startDate + "," + startTime.getHour() + ":" + startTime.getMinute() + "," + endDate + "," + endTime.getHour() + ":" + endTime.getMinute();
    }

    //saves the break to the file
    public void saveBreak(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("break_data.txt"))){
            writer.write(toFileString());
            writer.newLine();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
