package p2;
import java.io.ObjectOutputStream;
import java.net.Socket;
import p1.*;

/**
 * @author Anna Selstam.
 * Class will connect to a {@link MessageProducerServer}. After establishing connection,
 * a {@link MessageProducer}-implementation be sent to the server. 
 */
public class MessageProducerClient {
    private String ipAddress;
    private int port;
    /**
     * Constructor. 
     * Parameters are sent in via MessageProducerServer.
     * @param ipAddress
     * @param port
     */
    public MessageProducerClient (String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    /**
     * Connects to the client, transfers a MessageProducer-instance and shuts the connection.
     */
    public void send(MessageProducer producer) {
        ArrayProducer array = null;
        int times = 0; 
        int delay = 0; 
        int size = 0;

        times = producer.times();
        delay = producer.delay();
        size = producer.size();
        Message[] messages = new Message[size];

        for (int i = 0; i < size; i++) {
            messages[i] = producer.nextMessage();
        }
        array = new ArrayProducer(messages, times, delay);

        try (Socket socket = new Socket(ipAddress, port)) {
			//Communication between the client and the server.
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ArrayProducer obj = array;
			oos.writeObject(obj);
			Thread.sleep(500);
			oos.flush();
			oos.close();
			socket.close();
		} 
        catch (Exception e) {
			e.printStackTrace();
		}
    }

}