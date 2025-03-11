package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class SocketHandler{
    private static final Logger log = LoggerFactory.getLogger(SocketHandler.class);
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public SocketHandler(Socket socket) {
        try {
            this.socket = socket;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            log.info("SocketHandler Initialization Successfully: {}", socket.getInetAddress());
        } catch (IOException e) {
            log.error("SocketHandler Initialization Failed", e);
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String msg){
        if(this.socket == null){
            log.warn("Message Send Failed: Socket is null");
        }
        if(dataOutputStream == null){
            log.warn("Message Send Failed: Stream not initialized");
        }
        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();  // refreshing cache
            log.info("Message Send Successfully: {}", msg);
        } catch (IOException e) {
            log.error("Message Send Failed", e);
        }
    }

    public String receiveMessage(){
        if(this.socket == null){
            log.warn("Message Receive Failed: Socket is null");
        }
        if(dataOutputStream == null){
            log.warn("Message Receive Failed: Stream not initialized");
        }
        try {
            log.info("Message Receive");
            return dataInputStream.readUTF();
        } catch (IOException e) {
            log.error("Message Receive Failed");
            throw new RuntimeException(e);
        }

    }
}
