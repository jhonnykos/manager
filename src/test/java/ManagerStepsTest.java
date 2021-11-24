import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class ManagerStepsTest {

    private ManagerSteps manager = new ManagerSteps();
    private ManagerSteps manager2 = new ManagerSteps();
    private ManagerSteps manager3 = new ManagerSteps();
    private ManagerSteps manager4 = new ManagerSteps();
    private List<ManagerSteps> managers = new ArrayList<>();
    ManagerSteps managerMock = Mockito.mock(ManagerSteps.class);

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
    } //sum: 28, days: 2

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
    public void addManagers(){
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
    public void shouldCompareManagersIfFirstLess(){
        int actual = comp.compare(manager2, manager);
        assertTrue(actual < 0, "В manager2 больше дней с количеством шагов больше или равному " +
                "минимальному, чем в manager (2 и 3 соответственно)");
    }

    @Test
    public void shouldCompareManagersIfFirstGreater(){
        int actual = comp.compare(manager, manager2);
        assertTrue(actual > 0, "В manager больше дней с количеством шагов больше или равному " +
                "минимальному, чем в manager2 (3 и 2 соответственно)");
    }

    @Test
    public void shouldCompareManagersIfFirstEqualsAndSameOrder(){
        manager2 = new ManagerSteps();
        {
            manager2.add(1, 5000);
            manager2.add(5, 8000);
            manager2.add(2, 3000);
            manager2.add(6, 11_000);
            manager2.add(1, 4000);
        }
        int expected =0;
        int actual = comp.compare(manager, manager2);
        assertEquals(expected, actual, "Одинаковое количество дней с количеством шагов, " +
                "большим или равным минимальному");
    }

    @Test
    public void shouldCompareManagersIfFirstEqualsAndDiffOrder(){
        manager2 = new ManagerSteps();
        {
            manager2.add(5, 8000);
            manager2.add(1, 9000);
            manager2.add(2, 3000);
            manager2.add(6, 11_000);
        }
        int expected =0;
        int actual = comp.compare(manager, manager2);
        assertEquals(expected, actual, "Одинаковое количество дней с количеством шагов, " +
                "большим или равным минимальному");
    }

    @Test
    public void shouldCompareManagersIfFirstEqualsAndDiff(){
        manager2 = new ManagerSteps();
        {
            manager2.add(15, 3000);
            manager2.add(10, 9000);
            manager2.add(15,1000);
            manager2.add(10,1000);
            manager2.add(3, 5000);
            manager2.add(6, 11_000);
        }
        int expected =0;
        int actual = comp.compare(manager, manager2);
        assertEquals(expected, actual, "Одинаковое количество дней с количеством шагов, " +
                "большим или равным минимальному");
    }

    @Test
    public void shouldSortByDays() {
        List<ManagerSteps> expected = new ArrayList<>();
        {
            expected.add(manager3); //1
            expected.add(manager2); //2
            expected.add(manager4); //2
            expected.add(manager); //3
        }
        List<ManagerSteps> actual = managers;

        Collections.sort(actual, comp);
        assertEquals(expected, actual, "Список должен сортироваться по возрастанию количества дней, " +
                "в которые было сделано количество шагов, большее или равное минимальному");
    }
}