package utils;

import java.net.Socket;

public interface MessageListener {
    void onMessageReceived(Socket socket, String message);
}
