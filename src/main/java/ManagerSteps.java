import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ManagerSteps implements Comparable<ManagerSteps> {
    public final static int MAX_STEPS = 10_000;
    private Map<Integer, Integer> steps;

    public ManagerSteps() {
        steps = new HashMap<>();
    }

    public int add(int day, int steps) {
        if (day < 1 || day > 365) {
            throw new IllegalDayException(day);
        }
        if (steps <= 0) {
            throw new IllegalStepsException(steps);
        }
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

    public int getSum() {
        int sum = 0;
        for (int key : steps.keySet()) {
            sum += steps.get(key);
        }
        return sum;
    }

    public Stream<Integer> getAllAbove(int steps) {
        return this.steps.keySet().stream()
                .filter(k -> this.steps.get(k) > steps);
    }

    @Override
    public int compareTo(ManagerSteps manager) {
        return getSum() - manager.getSum();
    }
}

