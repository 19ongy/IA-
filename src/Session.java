import java.util.Scanner;

public class Session {
    private int month;
    private int day;
    private int startHour;
    private int startMin;
    private int endHour;
    private int endMin;
    private String moodBefore;
    private String moodAfter;
    private String subject;

    //constructor
    public Session(int month, int day, int starthour, int startmin, int endhour, int endMinute, String moodbefore, String moodAfter, String subject){
        this.month = month;
        this.day = day;
        this.startHour = starthour;
        this.startMin = startmin;
        this.endHour = endhour;
        this.endMin = endMinute;
        this.moodBefore = moodbefore;
        this.moodAfter = moodAfter;
        this.subject = subject;
    }


    //getters
    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public String getSubject() {
        return subject;
    }

    public String getMoodBefore() {
        return moodBefore;
    }

    public String getMoodAfter() {
        return moodAfter;
    }


    //setters
    public void setStartTime(int startHour, int startMin){
        this.startHour = startHour;
        this.startMin = startMin;
    }

    public void setEndTime(int endHour, int endMin){
        this.endHour = endHour;
        this.endMin = endMin;
    }

    public void setSession(){
        Scanner scanner = new Scanner(System.in);

    }
}
