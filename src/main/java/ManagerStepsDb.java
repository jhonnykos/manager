import com.learnup.test.orm.DbHelper;
import com.learnup.test.orm.entities.Day;

public class ManagerStepsDb {

    private static DbHelper helper = new DbHelper();

    public boolean addDay(Integer day, Integer steps) {
        if (day == null || day < 1 || day > 365) {
            throw new IllegalDayException(day);
        }
        if (steps == null || steps <= 0) {
            throw new IllegalStepsException(steps);
        }
        if (!helper.addDay(new Day(day, steps))) {
            return helper.updateDay(new Day(day, steps));
        }
        return true;
    }

    public Integer getStepsByDay(Integer day) {
        return helper.getStepsByDay(day);
    }

    public boolean deleteDay(Integer day) {
        return helper.deleteDay(day);
    }
}
