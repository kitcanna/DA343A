package p2;
import p1.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author Anna Selstam.
 * Class receives {@link Message}-objects via MessageServer,
 * via object streams in this class' inner class {@link Connection}.
 */
public class MessageClient {
    private Buffer<Message> messageBuffer;
	private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

	/**
	 * Constructor that sets the incoming parameters as values in the inner class, 
	 * as a new Message-object placed in the Buffer.
	 * @param address 
	 * @param port
	 */
    public MessageClient(String address, int port) {
		new Connection(address, port);
		messageBuffer = new Buffer<Message>();
	}

	/**
	 * Add listener to the object.
	 * @param listener
	 */
	public void addPopertyChangeListener(PropertyChangeListener listener) {
		changes.addPropertyChangeListener(listener);
	}

	/**
	 * Inner class with thread-extension.
	 * Opens the stream and keeps it open during the execution. 
	 */
    private class Connection extends Thread {
		private String address;
		private int port;

		/**
		 * Constructor. Starting run.
		 * @param address
		 * @param port
		 */
		public Connection(String address, int port) {
			this.address = address;
			this.port = port;
			start();
		}

		/**
		 * Connects to the client, reads the incoming stream and fires a propertychange
		 * so that P2Viewer can show them.
		 */
		public void run() {
			try (Socket socket = new Socket(address, port)) {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				while(true) {
					try {
						Message msg = (Message)ois.readObject();
						messageBuffer.put(msg);
						changes.firePropertyChange("message", null, msg);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
