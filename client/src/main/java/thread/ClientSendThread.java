package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SocketHandler;

import java.net.Socket;

public class ClientSendThread extends Thread{
    private static final Logger log = LoggerFactory.getLogger(ClientSendThread.class);
    private Socket socket;
    private String message;

    public ClientSendThread(Socket socket, String message){
        this.socket = socket;
        this.message = message;
        log.info("ClientSendThread Initialized Successfully, Socket:{}",socket);
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

    @Override
    public void run(){
        SocketHandler socketHandler = new SocketHandler(this.socket);
        log.info("SocketHandler Initialized Successfully In ClientSendThread");
        while(true){
            if(this.message != null){
                socketHandler.sendMessage(this.message);
                log.info("Message Send Successfully: {}", this.message);
                message = null;
            }
        }
    }

}
