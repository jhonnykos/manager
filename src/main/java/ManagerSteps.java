import java.util.HashMap;
import java.util.Map;

public class ManagerSteps {
    public final static int MAX_STEPS = 10_000;

    private Map<Integer, Integer> steps;

    public ManagerSteps() {
        steps = new HashMap<>();
    }

    public int add(int day, int steps) {
        if (steps < 0 || day < 0) return -1;

        if (this.steps.containsKey(day)) {
            this.steps.put(day, this.steps.get(day) + steps);
        } else {
            this.steps.put(day, steps);
        }

        if (MAX_STEPS > this.steps.get(day)) {
            return MAX_STEPS - this.steps.get(day);
        }
        return 0;
    }

    public Map<Integer, Integer> getSteps() {
        return this.steps;
    }
}

