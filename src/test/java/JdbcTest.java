import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcTest {

    private List<Integer> days;
    private ManagerStepsDb manager;

    @BeforeEach
    public void setUp() {
        manager = new ManagerStepsDb();
    }

    @Test
    public void addStepsDiffDays() {
        days = List.of(1, 2, 6);
        List<Integer> expected = List.of(5000, 7000, 1000);

        manager.addDay(1, 5000);
        manager.addDay(2, 7000);
        manager.addDay(6, 1000);

        List<Integer> actual = List.of(
                manager.getStepsByDay(1),
                manager.getStepsByDay(2),
                manager.getStepsByDay(6)
        );
        assertEquals(expected, actual, "Метод должен записывать в таблицу новые дни");
    }

    @Test
    public void addStepsDaysExist() {
        days = List.of(3, 5, 7);
        List<Integer> expected = List.of(8000, 1500, 7000);

        manager.addDay(3, 4000);
        manager.addDay(5, 1500);
        manager.addDay(7, 7000);
        manager.addDay(3, 4000);

        List<Integer> actual = List.of(
                manager.getStepsByDay(3),
                manager.getStepsByDay(5),
                manager.getStepsByDay(7)
        );
        assertEquals(expected, actual, "Метод должен добавлять шаги в уже существующий день");
    }

    @Test
    public void throwExceptionIfStepsNull() {
        days = new ArrayList<>();
        assertThrows(IllegalStepsException.class, () -> {
            manager.addDay(10, null);
        });
    }

    @Test
    public void notAddStepsIfStepsNull() {
        days = new ArrayList<>();
        try {
            manager.addDay(11, null);
        } catch (IllegalStepsException e) {
            e.printStackTrace();
        }
        assertNull(manager.getStepsByDay(11));
    }

    @Test
    public void throwExceptionIfDayNull() {
        days = new ArrayList<>();
        assertThrows(IllegalDayException.class, () -> {
            manager.addDay(null, 5000);
        });
    }

    @AfterEach
    public void tearDown() {
        if (days != null) {
            days.forEach(d -> manager.deleteDay(d));
        }
    }
}
