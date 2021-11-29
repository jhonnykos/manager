public class IllegalStepsException extends IllegalArgumentException{
    public IllegalStepsException(int steps){
        super("Количество шагов должно быть положительным. Текущее значение: " + steps);
    }
}
