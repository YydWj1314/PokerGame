package enumuration;

public enum CommandType {
    JOIN("JOIN"),
    JSON("JSON"),

    WELCOME("WELCOME"),
    BROADCAST("BROADCAST");

    private final String type;

   CommandType(String message) {
        this.type = message;
    }

    public String getMessage() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
