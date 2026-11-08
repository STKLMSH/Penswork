package model;

public final class Admin extends User {

    public Admin(long id, String login, String fullName) {
        super (id, login, fullName);
    }

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }
}
