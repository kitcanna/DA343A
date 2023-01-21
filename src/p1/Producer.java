package p1;
/* 
* Author: <Anna Selstam>  
* ID: <am3963>  
* Study program: <DT>
*/

/** 
*   @author Anna Selstam
*   The class retrieves all Messages from a MessageProducer implementation, 
* 	and puts the Messages in a Buffer<Message> with a pause in between.
*/
public class Producer extends Thread {
	private Buffer<MessageProducer> producerBuffer;
	private Buffer<Message> messageBuffer;

	/**
	 * Constructor creates a Producer object with the parameters.
	 * @param prodBuffer (Buffer containing MessageProducer)
	 * @param messageBuffer (Buffer containing Message)
	 */
    public Producer(Buffer<MessageProducer> prodBuffer, Buffer<Message> messageBuffer) {
		this.producerBuffer = prodBuffer;
		this.messageBuffer = messageBuffer;
	}

    /**
     * Starts execution of run.
     */
    @Override
	public synchronized void start() {
		super.start();
	}

    /**
     * Initialized by start().
     * While the thread isn't interrrupted, it will get the current object, 
	 * iterate the amount of times that mp returns (x),
     * iterate its inner loop through the size of that object, 
     * where put() adds the next Message-object in the end of the Buffer sequence and 
     * waits (y) milliseconds between each picture.
     */
    @Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				MessageProducer mp = producerBuffer.get();
				for (int times = 0; times < mp.times(); times++) {	//(x) Loops through the number of repetitions of sequence
					for (int i = 0; i < mp.size(); i++) { 			//Loops through the size of the current object, 
						messageBuffer.put(mp.nextMessage());		//where put() adds the next Message-object in the end of the sequence,
						Thread.sleep(mp.delay());					// and waits (y) milliseconds between every picture.
					}
				}
			} 
            catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}