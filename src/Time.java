import java.util.Timer;
// to get the amount of time spent on the study session

public class Time {
    private int startHour = 0;
    private int startMin = 0;
    private int endHour = 0;
    private int endMin = 0;
    int hourStudied = 0;
    int minStudied = 0;

    public Time(int startHour, int startMin, int endHour, int endMin){
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
    }

    //gets the amount of time studied for an already existing schedule
    public void ExistingTime(int startHour, int startMin, int endHour, int endMin){

    }

}
