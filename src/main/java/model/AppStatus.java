package model;

public enum AppStatus {
    QUEUED("В очереди"),
    CHECK("Проверка"),
    APPROVED("Одобрено"),
    REJECTED("Отклонено"),
    CLOSED("Закрыто");

    private final String title;

    AppStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean canTransTo(AppStatus target) {
        return switch (this) {
            case QUEUED -> target == CHECK;
            case CHECK -> target == APPROVED || target == REJECTED;
            case APPROVED, REJECTED ->  target == CLOSED;
            case CLOSED -> false;
        };
    }

    public boolean isUnderReview() {
        return this == QUEUED || this == CHECK;
    }
}
