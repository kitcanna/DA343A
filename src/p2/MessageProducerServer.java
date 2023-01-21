package p2;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import p1.MessageProducerInput;

/**
 * @author Anna Selstam.
 * Class that listens to a port, handling connections using Object Streams.
 * Class is an iterative server handling one client at a row.
 */
public class MessageProducerServer {
	private Thread server;
	private MessageProducerInput messageProducerInput;

	/**
	 * Constructor.
	 * @param messageProducerInput.
	 * @param port to listen at.
	 */
	public MessageProducerServer(MessageProducerInput messageProducerInput, int port) {
		this.messageProducerInput = messageProducerInput;
		server = new ConnectionHandler(port);
	}

	/**
	 * Starts the thread.
	 * The class is an iterative class handling one client at a time.
	 */
	public void startServer() {
		server.start();
	}

	/**
	 * Inner class handling the connection.
	 */
	private class ConnectionHandler extends Thread {
		private int port;
		/**
		 * Constructor.
		 * @param port.
		 */
		public ConnectionHandler(int port) {
			this.port = port;
		}
		/**
		 * Connects to the server, reads the incoming stream, 
		 * where the object is read as an ArrayProducer and then sent as 
		 * an instance of that class to MessageProducerInput. 
		 * Stream is then closed.
		 */
		public void run() {
			try (ServerSocket servSocket = new ServerSocket(port)) {
				System.out.println("MessageProducerServer initiated!");
				while (true) {
					Socket socket = servSocket.accept();
					//Communication between the client and the server.
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					ArrayProducer ap = (ArrayProducer) ois.readObject();
					
					//Server calls for method in MessageProducerInput
					messageProducerInput.addMessageProducer(ap);
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
