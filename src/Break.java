import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Break {
    private int breakLength;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;

    public Break(int breakLength, LocalDate startDate, LocalTime startTime,
                 LocalDate endDate, LocalTime endTime) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        String formattedStart = startTime.format(formatter);
        String formattedEnd = endTime.format(formatter);

        return breakLength + "," + startDate + "," + formattedStart + "," + endDate + "," + formattedEnd;
    }

    public static ArrayList<Break> getBreaksByDate(ArrayList<Break> allBreaks, LocalDate date){
        ArrayList<Break> result = new ArrayList<>();
        for(Break br : allBreaks){
            if(br.getStartDate().equals(date)){
                result.add(br);
            }
        }
        return result;
    }

    public static ArrayList<Break> readBreaks(){
        return new ArrayList<>();
    }
}
