import com.learnup.test.jbdc.DbHelper;
import com.learnup.test.jbdc.entities.Day;
import org.postgresql.util.PSQLException;

public class ManagerStepsDb {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/stepsManager";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "postgres";

    private static DbHelper helper = new DbHelper(DB_URL, DB_USER, DB_PASS);

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

    public Integer getStepsByDay(Integer day){
        return helper.getStepsByDay(day);
    }

    public boolean deleteDay(Integer day){
        return helper.deleteDay(day);
    }
}
