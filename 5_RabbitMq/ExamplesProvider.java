import java.util.Map;
import static java.util.Map.entry;


public class ExamplesProvider {
    private static final String[] d1Examinations = { "hip", "hip", "hip", "hip", "hip", "hip" };
    private static final String[] d2Examinations = { "hip", "hip", "knee", "hip", "elbow", "knee" };

    public static Map<String, String[]> sampleExaminations = Map.ofEntries(
            entry("D1", d1Examinations),
            entry("D2", d2Examinations)
    );
}
