import java.util.Comparator;
import java.util.Map;

public class ManagerStepsComparator implements Comparator<ManagerSteps> {

    private final int minSteps;

    public ManagerStepsComparator(int minSteps) {
        this.minSteps = minSteps;
    }

    @Override
    public int compare(ManagerSteps manager1, ManagerSteps manager2) {
        int dayCount1 = dayCount(manager1);
        int dayCount2 = dayCount(manager2);
        return dayCount1 - dayCount2;
    }

    private int dayCount(ManagerSteps manager) {
        int count = 0;
        Map<Integer, Integer> steps = manager.getSteps();
        for (int key : steps.keySet()) {
            if (steps.get(key) > minSteps) {
                count++;
            }
        }
        return count;
    }
}
