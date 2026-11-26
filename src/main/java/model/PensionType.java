package model;

public enum PensionType {
    STANDARD("По возрасту"),
    PREFERENTIAL("Льготная");

    private final String title;

    PensionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
