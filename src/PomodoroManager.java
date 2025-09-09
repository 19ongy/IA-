public class PomodoroManager {
    private int[] allDurations;
    private String[] types;
    private int index = 0;

    public PomodoroManager() {
        allDurations = new int[]{1500, 300, 1500, 300, 1500, 900};
        types = new String[]{"Study", "Break", "Study", "Break","Study", "Break"};
    }


}
