import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ManagerStepsTest {

    ManagerSteps manager = new ManagerSteps();

    @BeforeEach
    public void addSteps() {
        manager.add(1, 5000);
        manager.add(5, 8000);
        manager.add(2, 3000);
        manager.add(6, 11_000);
        manager.add(1, 4000);
    }

    @Test
    public void shouldReturnAllSteps() {
        Map<Integer, Integer> expected = new HashMap<>();
        {
            expected.put(1, 9000);
            expected.put(2, 3000);
            expected.put(5, 8000);
            expected.put(6, 11_000);
        }
        Map<Integer, Integer> actual = manager.getSteps();
        Assertions.assertEquals(expected, actual, "Метод должен возвращать Map шагов и дней");
    }

    @Test
    public void shouldReturnEmpty() {
        ManagerSteps managerEmpty = new ManagerSteps();
        Map<Integer, Integer> expected = new HashMap<>();
        Map<Integer, Integer> actual = managerEmpty.getSteps();
        Assertions.assertEquals(expected, actual, "Метод должен возвращать пустой Map");
    }

    @Test
    public void shouldReturnDiffIfNew() {
        int expected = 2000;
        int actual = manager.add(10, 8000);
        Assertions.assertEquals(expected, actual, "Метод должен возвращать количество шагов до максимума," +
                "если новый день");
    }

    @Test
    public void shouldReturnDiffIfUsed() {
        int expected = 500;
        int actual = manager.add(1, 500);
        Assertions.assertEquals(expected, actual, "Метод должен возвращать количество шагов до максимума," +
                "если использованный день");
    }

    @Test
    public void shouldReturnDiffIfBiggerThanMax() {
        int expected = 0;
        int actual = manager.add(1, 2000);
        Assertions.assertEquals(expected, actual, "Метод должен возвращать 0");
    }

    @Test
    public void shouldReturnDiffIfMax() {
        int expected = 0;
        int actual = manager.add(10, 10_000);
        Assertions.assertEquals(expected, actual, "Метод должен возвращать 0");
    }

    @Test
    public void shouldReturnErrorIfNegativeSteps() {
        int expected = -1;
        int actual = manager.add(1, -5000);
        Assertions.assertEquals(expected, actual, "Метод должен возвращать -1");
    }

    @Test
    public void shouldReturnErrorIfNegativeDay() {
        int expected = -1;
        int actual = manager.add(-1, 5000);
        Assertions.assertEquals(expected, actual, "Метод должен возвращать -1");
    }
}