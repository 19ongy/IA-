public class ReminderTime {
    private int hour;
    private int min;
    private int place;

    public ReminderTime(int hour, int min){
        this.hour = hour;
        this.min = min;
    }

    //getters
    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public int getPlace(){
        return place;
    }

    public String toString() {
        return String.format("%02d:%02d", hour, min);
    }
}
