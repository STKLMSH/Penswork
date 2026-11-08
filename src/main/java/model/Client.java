package model;

public final class Client extends User {
    private final String number;
    private final EmployHistory employHistory = new EmployHistory();

    public Client (long id, String login, String fullName, String number) {
        super (id, login, fullName);
        if (number == null || !number.matches("[0-9A-Z]{14}")) {
            throw new IllegalArgumentException();
        }
        this.number = number;
    }

    @Override
    public Role getRole() {
        return Role.Client;
    }

    public String getNumber() {
        return number;
    }

    public EmployHistory getEmployHistory() {
        return employHistory;
    }
}
