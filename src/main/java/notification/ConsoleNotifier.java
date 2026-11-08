package notification;

import model.User;

public final class ConsoleNotifier implements Notifier {

    @Override
    public void notify(User recipient, String message) {
        System.out.println("Уведомление для " + recipient.getLogin() + " " + message);
    }
}
