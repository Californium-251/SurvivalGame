package exceptions;

public class EnemyOutOfBoundsException extends Exception {
    public EnemyOutOfBoundsException() {
        super();
    }

    public EnemyOutOfBoundsException(String msg) {
        super(msg);
    }
}
