package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ClientMessageBuffer {
    private static final Logger log = LoggerFactory.getLogger(ClientMessageBuffer.class);

    private static final BlockingQueue<MessageEntry> messageQueue = new LinkedBlockingDeque<>();


    public static void addMessage(Socket socket, String message){
        try {
            messageQueue.put(new MessageEntry(socket, message));
            log.info("ClientMessageBuffer Add Message: [{} from {}]", message, socket);
        } catch (InterruptedException e) {
            log.error("ClientMessageBuffer Put Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt(); // setting interruption
        }
    }

    public static MessageEntry takeMessage(){
        try {
            return messageQueue.take();
        } catch (InterruptedException e) {
            log.error("ClientMessageBuffer Take Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public static class MessageEntry {
        public final Socket socket;
        public final String message;

        public MessageEntry(Socket socket, String message) {
            this.socket = socket;
            this.message = message;
        }
    }

}
