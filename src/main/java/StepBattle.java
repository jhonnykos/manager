import java.util.Map;

public class StepBattle {

    private ManagerSteps managerFirst;
    private ManagerSteps managerSecond;

    public StepBattle(ManagerSteps manager1, ManagerSteps manager2) {
        managerFirst = manager1;
        managerSecond = manager2;
    }

    public void addSteps(int player, int day, int steps) {
        if (day < 0 || steps < 0) return;
        if (player == 1) {
            managerFirst.add(day, steps);
        }
        if (player == 2) {
            managerSecond.add(day, steps);
        }
    }

    public int winner() {
        int sumFirst = sumSteps(managerFirst);
        int sumSecond = sumSteps(managerSecond);
        if (sumFirst > sumSecond) {
            return 1;
        } else {
            return 2;
        }
    }

    public int sumSteps(ManagerSteps manager) {
        int sum = 0;
        Map<Integer, Integer> steps = manager.getSteps();
        for (int value : steps.values()) {
            sum += value;
        }
        return sum;
    }
}
