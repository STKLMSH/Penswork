package model;

import java.util.Objects;

public abstract class User {
    private final long id;
    private final String login;
    private final String fullName;

    protected User (long id, String login, String fullName) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Поле Логин не может быть пустым!");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Поле ФИО не может быть пустым!");
        }
        this.id = id;
        this.login = login.trim();
        this.fullName = fullName.trim();
    }

    public abstract Role getRole();

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return id == other.id && getRole() == other.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getRole());
    }

    @Override
    public String toString() {
        return fullName + " (" + getRole().getTitle() + ", " + login + ")";
    }
}

