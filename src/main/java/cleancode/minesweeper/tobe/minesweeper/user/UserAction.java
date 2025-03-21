package cleancode.minesweeper.tobe.minesweeper.user;

public enum UserAction {
    OPEN("셀 여기"),
    FLAG("깃발 꽂기"),
    UNKNOWN("알 수 없음");

    private final String description;

    UserAction(String description) {
        this.description = name().toLowerCase();
    }
}
