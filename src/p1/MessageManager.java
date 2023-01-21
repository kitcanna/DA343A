package p1;
/* 
* Author: <Anna Selstam>  
* ID: <am3963>  
* Study program: <DT>
*/

import java.util.LinkedList;

/**
 * The class retrieves Messages from the buffer and sends them to be displayed 
 * through P1Viewer instances.
 * The class uses Callback to push information.
 * @author Anna Selstam
 */
public class MessageManager implements Runnable {
    private Buffer<Message> messageBuffer;
    private Thread thread;
    private LinkedList<MessageListener> list = new LinkedList<MessageListener>();

    /**
     * Constructor assigns the Messages from the buffer to the the instance variable.
     * @param messageBuffer
     */
    public MessageManager(Buffer<Message> messageBuffer) {
        this.messageBuffer = messageBuffer;
    }


    /**
     * Adds listeners to the list.
     * Happens via P1Viewer
     * @param listener
     */
    public void addMessageListener(MessageListener listener) {
		if (listener != null) {
			list.add(listener);
		}
	}

    /**
     * Executes run().
     */
    public void start() {
        if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
    }

   /**
     * Executed by start().
     * Gets the current object, prints the text,
     * loops through the existing listener and calls for the 
     * interface method which then P1Viewer uses.
     */
    @Override
    public void run() {
       while(!Thread.interrupted()) {
           try {
                Message message = messageBuffer.get();      //Gets the current object
                System.out.println(message.getText());      //Text    

                for (MessageListener ml : list) {           //Looping through the existing listeners
                    ml.methodToCall(message);               
                }
                
           } 
           catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }
}