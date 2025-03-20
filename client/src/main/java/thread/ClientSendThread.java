package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SocketHandler;

import java.net.Socket;

public class ClientSendThread extends Thread{
    private static final Logger log = LoggerFactory.getLogger(ClientSendThread.class);
    private Socket socket;
    private String message;
    private SocketHandler socketHandler;


    public ClientSendThread(Socket socket, String message){
        this.socket = socket;
        this.message = message;
        this.socketHandler = new SocketHandler(this.socket);
        log.info("ClientSendThread Initialized Successfully, Socket:{}",socket);
    }

    public void sendMessage(String newMessage){
        if(newMessage != null) {
            socketHandler.sendMessage(newMessage);
            log.info("Message send successfully: {}", newMessage);
        }
    }

    @Override
    public void run(){
        log.info("SocketHandler Initialized Successfully");
        while(true){
            if(this.message != null) {
                socketHandler.sendMessage(message);
                log.info("Message send successfully: {}", message);
                message = null;
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SocketHandler getSocketHandler() {
        return socketHandler;
    }

    public void setSocketHandler(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }
}
