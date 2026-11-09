package exception;

public class ApplicationNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ApplicationNotFoundException(long appId) {
        super("Заялвение №%d: " + appId + " не найдено");
    }
}
