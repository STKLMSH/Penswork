package exception;

import model.AppStatus;

public class InvalidStatusTransitionException extends RuntimeException {
    private static final long serialVersionID = 1L;

    public InvalidStatusTransitionException(long appId, AppStatus from, AppStatus to) {
        super(String.format("Заялвение №%d: переход из %s в %s недопустим",
                appId, from.getTitle(), to.getTitle()));
    }
}
