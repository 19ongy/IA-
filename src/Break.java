import java.time.LocalDate;
import java.time.LocalTime;

public class Break {
    private int breakLength;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;

    public Break(int breakLength, LocalDate startDate, LocalTime startTime,
                 LocalDate endDate, LocalTime endTime){
        this.breakLength = breakLength;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    //getters and setters
    public int getLength(){
        return breakLength;
    }

    public LocalDate getStartDate(){
        return startDate;
    }

    public LocalTime getStartTime(){
        return startTime;
    }

    public LocalDate getEndDate(){
        return endDate;
    }

    public LocalTime getEndTime(){
        return endTime;
    }

    public String toFileString() {
        String formattedStart = String.format("%02d:%02d", startTime.getHour(), startTime.getMinute());
        String formattedEnd = String.format("%02d:%02d", endTime.getHour(), endTime.getMinute());

        return breakLength + "," +
                startDate.toString() + "," + formattedStart + "," +
                endDate.toString() + "," + formattedEnd;
    }
}
