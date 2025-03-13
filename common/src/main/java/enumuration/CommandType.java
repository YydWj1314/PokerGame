package enumuration;

public enum CommandType {
    JOIN("JOIN"),
    DEAL("DEAL"),
    NOTIFY_SERVER("NOTIFY_SERVER"),
    NOTIFY_CLIENT("NOTIFY_CLIENT");

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
