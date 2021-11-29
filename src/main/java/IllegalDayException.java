public class IllegalDayException extends IllegalArgumentException {
    public IllegalDayException(int day){
        super("День должен быть от 1 до 365. Текущий день: " + day);
    }

}
