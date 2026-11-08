package model;

public enum Role {
    CLIENT("Пользователь"),
    ADMIN("Aдминистратор");

    private final String title;

    Role(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
