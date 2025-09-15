import java.util.*;
public class MotivationManager {
    private static final List<String> generalQuotes = List.of(
            "Progress is progress, no matter how small.",
            "You’re doing better than you think.",
            "Every step forward counts."
    );

    private static final List<String> mediumQuotes = List.of(
            "You’re allowed to feel off. Just don’t forget how strong you are.",
            "Even slow progress is progress.",
            "You’re allowed to pause, not to give up."
    );

    private static final List<String> intenseQuotes = List.of(
            "You’ve made it through every hard day so far. This one’s no different.",
            "Pain is real, but so is hope.",
            "Your story isn’t over yet."
    );

    public static String getQuoteByIntensity(int intensity) {
        List<String> pool;
        if (intensity >= 8) {
            pool = intenseQuotes;
        } else if (intensity >= 5) {
            pool = mediumQuotes;
        } else {
            pool = generalQuotes;
        }
        return pool.get(new Random().nextInt(pool.size()));
    }
}
