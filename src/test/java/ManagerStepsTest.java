import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ManagerStepsTest {

    private ManagerSteps manager = new ManagerSteps();
    private ManagerSteps manager2 = new ManagerSteps();
    private ManagerSteps manager3 = new ManagerSteps();
    private ManagerSteps manager4 = new ManagerSteps();
    private List<ManagerSteps> managers = new ArrayList<>();

    private ManagerStepsComparator comp = new ManagerStepsComparator(5000);

    @BeforeEach
    public void addStepsFirst() {
        manager.add(1, 5000);
        manager.add(5, 8000);
        manager.add(2, 3000);
        manager.add(6, 11_000);
        manager.add(1, 4000);
    } //sum: 31, days: 3

    @BeforeEach
    public void addStepsSecond() {
        manager2.add(1, 5000);
        manager2.add(5, 20_000);
        manager2.add(10, 3000);
    } //sum: 28, days: 1

    @BeforeEach
    public void addStepsThird() {
        manager3.add(6, 2000);
        manager3.add(6, 5000);
        manager3.add(9, 1000);
        manager3.add(1, 3000);
    } //sum: 11, days: 1

    @BeforeEach
    public void addStepsFourth() {
        manager4.add(10, 10_000);
        manager4.add(1, 13_000);
    } //sum: 23, days:2

    @BeforeEach
    public void addManagers() {
        managers.add(manager);
        managers.add(manager2);
        managers.add(manager3);
        managers.add(manager4);
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


    //Sum
    @Test
    public void shouldReturnSum() {
        int expected = 5000 + 8000 + 3000 + 11000 + 4000;
        int actual = manager.getSum();
        assertEquals(expected, actual, "Метод должен возвращать сумму шагов");
    }

    @Test
    public void shouldReturnSumIfEmpty() {
        manager = new ManagerSteps();
        int expected = 0;
        int actual = manager.getSum();
        assertEquals(expected, actual, "Метод должен возвращать 0");
    }

    @Test
    public void shouldReturnSumIfOne() {
        manager = new ManagerSteps();
        {
            manager.add(1, 5000);
        }
        int expected = 5000;
        int actual = manager.getSum();
        assertEquals(expected, actual, "Метод должен возвращать единственное количество шагов");
    }

    //Comparable
    @Test
    public void shouldCompareManagersIfLess() {
        int actual = manager2.compareTo(manager);
        assertTrue(actual < 0, "manager меньше чем manager2");
    }

    @Test
    public void shouldCompareManagersIfGreater() {
        int actual = manager.compareTo(manager2);
        assertTrue(actual > 0, "manager больше чем manager2");
    }

    @Test
    public void shouldCompareManagersIfEqualsAndSameOrder() {
        manager2 = new ManagerSteps();
        {
            manager2.add(1, 5000);
            manager2.add(5, 8000);
            manager2.add(2, 3000);
            manager2.add(6, 11_000);
            manager2.add(1, 4000);
        }
        int expected = 0;
        int actual = manager.compareTo(manager2);
        assertEquals(expected, actual, "manager равен чем manager2");
    }

    @Test
    public void shouldCompareManagersIfEqualsAndDiffOrder() {
        manager2 = new ManagerSteps();
        {
            manager2.add(5, 8000);
            manager2.add(1, 9000);
            manager2.add(2, 3000);
            manager2.add(6, 11_000);
        }
        int expected = 0;
        int actual = manager.compareTo(manager2);
        assertEquals(expected, actual, "manager равен чем manager2");
    }

    @Test
    public void shouldCompareManagersIfEqualsAndDiffDays() {
        manager2 = new ManagerSteps();
        {
            manager2.add(15, 31_000);
        }
        int expected = 0;
        int actual = manager.compareTo(manager2);
        assertEquals(expected, actual, "manager равен чем manager2");
    }

    @Test
    public void shouldSortBySum() {
        List<ManagerSteps> expected = new ArrayList<>();
        {
            expected.add(manager3);
            expected.add(manager4);
            expected.add(manager2);
            expected.add(manager);
        }
        List<ManagerSteps> actual = managers;

        Collections.sort(actual);
        assertEquals(expected, actual, "Список должен сортироваться по возрастанию суммы шагов");
    }

    //Comparator
    @Test
    public void shouldCompareManagersIfFirstLess() {
        int actual = comp.compare(manager2, manager);
        assertTrue(actual < 0, "В manager2 больше дней с количеством шагов больше или равному " +
                "минимальному, чем в manager (2 и 3 соответственно)");
    }

    @Test
    public void shouldCompareManagersIfFirstGreater() {
        int actual = comp.compare(manager, manager2);
        assertTrue(actual > 0, "В manager больше дней с количеством шагов больше или равному " +
                "минимальному, чем в manager2 (3 и 2 соответственно)");
    }

    @Test
    public void shouldCompareManagersIfFirstEqualsAndSameOrder() {
        manager2 = new ManagerSteps();
        {
            manager2.add(1, 5000);
            manager2.add(5, 8000);
            manager2.add(2, 3000);
            manager2.add(6, 11_000);
            manager2.add(1, 4000);
        }
        int expected = 0;
        int actual = comp.compare(manager, manager2);
        assertEquals(expected, actual, "Одинаковое количество дней с количеством шагов, " +
                "большим или равным минимальному");
    }

    @Test
    public void shouldCompareManagersIfFirstEqualsAndDiffOrder() {
        manager2 = new ManagerSteps();
        {
            manager2.add(5, 8000);
            manager2.add(1, 9000);
            manager2.add(2, 3000);
            manager2.add(6, 11_000);
        }
        int expected = 0;
        int actual = comp.compare(manager, manager2);
        assertEquals(expected, actual, "Одинаковое количество дней с количеством шагов, " +
                "большим или равным минимальному");
    }

    @Test
    public void shouldCompareManagersIfFirstEqualsAndDiff() {
        manager2 = new ManagerSteps();
        {
            manager2.add(15, 3000);
            manager2.add(10, 9000);
            manager2.add(15, 3000);
            manager2.add(10, 1000);
            manager2.add(6, 11_000);
        }
        int expected = 0;
        int actual = comp.compare(manager, manager2);
        assertEquals(expected, actual, "Одинаковое количество дней с количеством шагов, " +
                "большим или равным минимальному");
    }

    @Test
    public void shouldSortByDays() {
        List<ManagerSteps> expected = new ArrayList<>();
        {
            expected.add(manager2); //1
            expected.add(manager3); //1
            expected.add(manager4); //2
            expected.add(manager); //3
        }
        List<ManagerSteps> actual = managers;

        Collections.sort(actual, comp);
        assertEquals(expected, actual, "Список должен сортироваться по возрастанию количества дней, " +
                "в которые было сделано количество шагов, большее или равное минимальному");
    }


    //Stream
    @Test
    public void shouldReturnStream() {
        List<Integer> expected = new ArrayList();
        Collections.addAll(expected, 1, 5, 6);

        List<Integer> actual =
                manager.getAllAbove(5000)
                        .collect(Collectors.toList());
        assertEquals(expected, actual, "Метод должен возвращать стрим с днями, " +
                "в которые было сделано больше чем 5000 шагов");
    }

    @Test
    public void shouldReturnStreamIfEmpty() {
        List<Integer> expected = new ArrayList();

        List<Integer> actual =
                manager.getAllAbove(15000)
                        .collect(Collectors.toList());
        assertEquals(expected, actual, "Метод должен возвращать пустой стрим");
    }

    @Test
    public void shouldReturnStreamIfAll() {
        List<Integer> expected = new ArrayList();
        Collections.addAll(expected, 1, 2, 5, 6);

        List<Integer> actual =
                manager.getAllAbove(1000)
                        .collect(Collectors.toList());
        assertEquals(expected, actual, "Метод должен возвращать пустой стрим");
    }


    //Exception
    @Test
    public void shouldThrowsExceptionDayUpperBound() {
        int day = 0;
        int steps = 5000;
        assertThrows(IllegalDayException.class, () -> {
            manager.add(day, steps);
        });
    }

    @Test
    public void shouldThrowsExceptionDayLowerBound() {
        int day = 366;
        int steps = 5000;
        assertThrows(IllegalDayException.class, () -> {
            manager.add(day, steps);
        });
    }

    @Test
    public void shouldThrowsExceptionDayIfNegative() {
        int day = -15;
        int steps = 5000;
        assertThrows(IllegalDayException.class, () -> {
            manager.add(day, steps);
        });
    }

    @Test
    public void shouldThrowsExceptionDayIfGreater() {
        int day = 500;
        int steps = 5000;
        assertThrows(IllegalDayException.class, () -> {
            manager.add(day, steps);
        });
    }

    @Test
    public void shouldThrowsExceptionDayIfLowerBoundPositive() {
        int day = 1;
        int steps = 5000;
        try {
            manager.add(day, steps);
        } catch (IllegalDayException e) {
            fail("Метод не должен вызывать исключение IllegalDayException");
        }
    }

    @Test
    public void shouldThrowsExceptionDayIfUpperBoundPositive() {
        int day = 365;
        int steps = 5000;
        try {
            manager.add(day, steps);
        } catch (IllegalDayException e) {
            fail("Метод не должен вызывать исключение IllegalDayException");
        }
    }

    @Test
    public void shouldThrowsExceptionDayIfPositive() {
        int day = 150;
        int steps = 5000;
        try {
            manager.add(day, steps);
        } catch (IllegalDayException e) {
            fail("Метод не должен вызывать исключение IllegalDayException");
        }
    }

    @Test
    public void shouldThrowsExceptionStepsUpperBound() {
        int day = 150;
        int steps = 0;
        assertThrows(IllegalStepsException.class, () -> {
            manager.add(day, steps);
        });
    }

    @Test
    public void shouldThrowsExceptionStepsIfLower() {
        int day = 150;
        int steps = -15;
        assertThrows(IllegalStepsException.class, () -> {
            manager.add(day, steps);
        });
    }

    @Test
    public void shouldThrowsExceptionStepsIfLowerBoundPositive() {
        int day = 150;
        int steps = 1;
        try {
            manager.add(day, steps);
        } catch (IllegalStepsException e) {
            fail("Метод не должен вызывать исключение IllegalStepsException");
        }
    }

    @Test
    public void shouldThrowsExceptionStepsIfPositive() {
        int day = 150;
        int steps = 5000;
        try {
            manager.add(day, steps);
        } catch (IllegalStepsException e) {
            fail("Метод не должен вызывать исключение IllegalStepsException");
        }
    }
}