package p2;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import p1.Buffer;
import p1.Message;
import p1.MessageListener;
import p1.MessageManager;

/**
 * @author Anna Selstam.
 * Class receives {@link Message}-objects through callback from the {@link MessageManager}
 * and the server sends them to connected {@link MessageClient}(s) 
 * that are shown through P2Viewer-instances. This connection is established via TCP.
 * 
 * Server is multi-threaded where each connected Client is handled by one thread.
 */
public class MessageServer {
	private MessageManager mm;
	private int port;
	private ArrayList<ClientHandler> clients;
	private Thread server;
	
	/**
	 * @Constructor 
	 * @param messageManager which the server will receive Messages from.
	 * @param port which the server will run on.
	 * Class relies on the callback-function from p1.
	 */
	public MessageServer(MessageManager messageManager, int port) {
		this.mm = messageManager;
		this.port = port;
		clients = new ArrayList<ClientHandler>();
		mm.addMessageListener(new Messager()); 
		server = new Connection(port);
	}

	/**
	 * Used by the callback. 
	 * Sends Messages from the MessageManager to the Client(s).
	 * @param msg
	 */
	private void sendToClients(Message msg) {
		Iterator<ClientHandler> iter = clients.iterator();
		while(iter.hasNext()) {
			iter.next().put(msg);
		}
	}
	
	/**
	 * Inner class handling new incoming connections and creates a new Thread for each.
	 */
	private class Connection extends Thread {
		private int port;
		/**
		 * @Constructor 
		 * @param port
		 */
		public Connection(int port) {
			this.port = port;
			start();
		}
		/**
		 * Connects to the server and creates a new ClientHandler for each object.
		 */
		public void run() {
			try (ServerSocket serverSocket = new ServerSocket(port)){
				System.out.println("MessageServer Started initiated!");
				while(true) {
					try {
						Socket socket = serverSocket.accept();
						ClientHandler client = new ClientHandler(socket);
						clients.add(client);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Inner class that handles a connection and sends the instance to the Client.
	 */
	private class ClientHandler extends Thread {
		private Socket socket;
		private Buffer<Message> messageBuffer;
		/**
		 * @Constructor 
		 * @param socket
		 */
		public ClientHandler(Socket socket) {
			this.socket = socket;
			messageBuffer = new Buffer<Message>();
			start();
		}
		
		/**
		 * Used in the process where Messages from 
		 * the MessageManager are sent to the Client(s). 
		 * @param msg
		 */
		public void put(Message msg) {
			messageBuffer.put(msg);
		}
		
		/**
		 * Reads the output stream, gets the instance from the Buffer
		 * and sends it to the Client.
		 */
		public void run() {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				while(true) {
					try {
						Message msg = messageBuffer.get();
						oos.writeObject(msg);
						oos.flush();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * Inner callback class forwarding the messages
	 * from the {@link MessageManager} to the client(s).
	 */
    public class Messager implements MessageListener{
    @Override
        public void methodToCall(Message message) {
			sendToClients(message);
            System.out.println("Message sent to client!");
        }
    }
}