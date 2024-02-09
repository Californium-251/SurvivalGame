package exceptions;

public class EnemyAlreadyActiveException extends Exception {
    public EnemyAlreadyActiveException() {
        super();
    }

    public EnemyAlreadyActiveException(String msg) {
        super(msg);
    }
}
