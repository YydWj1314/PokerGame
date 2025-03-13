package model;

import model.enumuration.CommandType;

import java.util.Arrays;

public class Command {
    private String cmdType;
    private String[] cmdParams;

    public Command(String message) {
        this.parseMessage(message);
    }

    public void parseMessage(String message) {
        String[] cmdParts = message.split(" ");
        if(cmdParts.length > 0){
            this.cmdType = cmdParts[0];
            this.cmdParams = Arrays.copyOfRange(cmdParts, 1, cmdParts.length);
        }
    }

    public String getCmdType() {
        return cmdType;
    }

    public String[] getCmdParams() {
        return cmdParams;
    }

    @Override
    public String toString() {
        return "Command{" +
                "cmdType='" + cmdType + '\'' +
                ", cmdParams=" + Arrays.toString(cmdParams) +
                '}';
    }
}
