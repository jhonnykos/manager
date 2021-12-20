public class IllegalStepsException extends IllegalArgumentException{
    public IllegalStepsException(Integer steps){
        super("Количество шагов должно быть положительным. Текущее значение: " + steps);
    }
}
