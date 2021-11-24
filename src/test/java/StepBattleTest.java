import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class StepBattleTest {

    private ManagerSteps managerFirst = Mockito.mock(ManagerSteps.class);
    private ManagerSteps managerSecond = Mockito.mock(ManagerSteps.class);
    private ManagerSteps managerEmpty = Mockito.mock(ManagerSteps.class);
    private Map<Integer, Integer> stepsFirst;
    private Map<Integer, Integer> stepsSecond;
    private Map<Integer, Integer> stepsEmpty;
    private StepBattle battle;

    @BeforeEach
    public void initBattle() {
        battle = new StepBattle(managerFirst, managerSecond);
    }

    @BeforeEach
    public void initGetManager() {
        stepsFirst = new HashMap<>();
        stepsSecond = new HashMap<>();
        stepsEmpty = new HashMap<>();
        {
            stepsFirst.put(1, 5000);
            stepsFirst.put(2, 10_000);
            stepsFirst.put(5, 3000);
            stepsFirst.put(6, 7000);
            stepsFirst.put(8, 15_000);
            stepsFirst.put(9, 0);
            stepsFirst.put(10, 2000);
        } //41
        {
            stepsSecond.put(1, 2000);
            stepsSecond.put(2, 11_000);
            stepsSecond.put(5, 1000);
            stepsSecond.put(6, 17_000);
            stepsSecond.put(8, 5000);
            stepsSecond.put(9, 0);
            stepsSecond.put(10, 500);
        } //36,5
        doReturn(stepsFirst).when(managerFirst).getSteps();
        doReturn(stepsSecond).when(managerSecond).getSteps();
        doReturn(stepsEmpty).when(managerEmpty).getSteps();
    }

    @Test
    void shouldAddStepsFirst() {
        battle.addSteps(1, 1, 5000);
        verify(managerFirst).add(1, 5000);
    }

    @Test
    void shouldAddStepsSecond() {
        battle.addSteps(2, 5, 5000);
        verify(managerSecond).add(5, 5000);
    }

    @Test
    void shouldNotAddStepsIfNegativeSteps() {
        battle.addSteps(2, 5, -5000);
        verify(managerSecond, never()).add(5, 5000);
    }

    @Test
    void shouldNotAddStepsIfNegativeDay() {
        battle.addSteps(2, -5, 5000);
        verify(managerSecond, never()).add(5, 5000);
    }

    @Test
    void shouldNotAddStepsIfIncorrectPlayer() {
        battle.addSteps(3, 5, 5000);
        verify(managerSecond, never()).add(5, 5000);
    }

    @Test
    void shouldReturnWinnerIfFirst() {
        int expected = 1;
        int actual = battle.winner();
        assertEquals(expected, actual, "Метод должен возвращать номер игрока, " +
                "который сделал больше шагов");
    }

    @Test
    void shouldReturnWinnerIfSecond() {
        battle = new StepBattle(managerSecond, managerFirst);
        int expected = 2;
        int actual = battle.winner();
        assertEquals(expected, actual, "Метод должен возвращать номер игрока, " +
                "который сделал больше шагов");
    }

    @Test
    void shouldReturnWinnerIfOneIsEmpty() {
        battle = new StepBattle(managerFirst, managerEmpty);
        int expected = 1;
        int actual = battle.winner();
        assertEquals(expected, actual, "Метод должен возвращать номер игрока, " +
                "который сделал больше шагов");
    }

    @Test
    void shouldGetStepsInSum() {
        battle.sumSteps(managerFirst);
        verify(managerFirst).getSteps();
    }

    @Test
    void shouldSumSteps() {
        int expected = 5000 + 10_000 + 3000 + 7000 + 15_000 + 0 + 2000;
        int actual = battle.sumSteps(managerFirst);
        assertEquals(expected, actual);
    }

    @Test
    void shouldSumStepsIfEmpty() {
        int expected = 0;
        int actual = battle.sumSteps(managerEmpty);
        assertEquals(expected, actual);
    }
}