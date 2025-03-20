package enumuration;

public enum CommandType {
    JOIN("JOIN"),
    JSON("JSON"),


    // Client command
    WELCOME("WELCOME"),
    BROADCAST("BROADCAST"),
    CLIENT_PLAY("CLIENT_PLAY");


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
