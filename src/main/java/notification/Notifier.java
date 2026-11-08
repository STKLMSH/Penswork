package notification;

import model.User;

public interface Notifier {

    void notify(User recipient, String message);
}
