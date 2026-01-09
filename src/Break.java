import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Break {
    private int breakLength; //in minutes
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;

    //constructor
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

    //formats data to be added to the CSV file
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        String formattedStart = startTime.format(formatter);
        String formattedEnd = endTime.format(formatter);

        //CSV format length, startdate, start time, end date, end time
        return breakLength + "," + startDate + "," + formattedStart + "," + endDate + "," + formattedEnd;
    }

//filters all breaks to only include the ones on a certain date
    public static ArrayList<Break> getBreaksByDate(ArrayList<Break> allBreaks, LocalDate date){
        ArrayList<Break> result = new ArrayList<>();
        for(Break br : allBreaks){
            if(br.getStartDate().equals(date)){
                result.add(br); //add to list
            }
        }
        return result;
    }

    //placeholder method to read breaks from storage
    public static ArrayList<Break> readBreaks(){
        return new ArrayList<>();
    }
}
