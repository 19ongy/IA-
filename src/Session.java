import java.time.Month;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

public class Session {
    private MoodEntry.Mood moodBefore;
    private MoodEntry.Mood moodAfter;
    private int sessionLength;
    private String subject;
    private String startMonth;
    private int startDay;
    private String endMonth;
    private int endDay;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    //constructor
    public Session(MoodEntry.Mood moodBefore, MoodEntry.Mood moodAfter, int sessionLength, String subject, String startMonth, int startDay, String endMonth, int endDay, int startHour, int startMinute, int endHour, int endMinute) {
        this.moodBefore = moodBefore;
        this.moodAfter = moodAfter;
        this.sessionLength = sessionLength;
        this.subject = subject;
        this.startMonth = startMonth;
        this.startDay = (startDay);
        this.endMonth = endMonth;
        this.endDay = (endDay);
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;

    }

    //getters
    public MoodEntry.Mood getMoodBefore() {
        return moodBefore;
    }

    public MoodEntry.Mood getMoodAfter() {
        return moodAfter;
    }

    public int getSessionLength() {
        return sessionLength;
    }

    public String getSubject() {
        return subject;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }


    public void setSession(){
        Scanner scanner = new Scanner(System.in);

    }



}
