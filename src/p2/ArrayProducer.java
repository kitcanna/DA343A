package p2;
import p1.*;
import java.io.Serializable;

/**
 * @author Anna Selstam.
 * Class transfers a {@link MessageProducer}-object (via MesageProducerServer).
 * Class modified via Serializable to handle streams. 
 */
public class ArrayProducer implements MessageProducer, Serializable {
	//private static final long serialVersionUID = -5560302775287541561L;
	private Message[] messages;
	private int delay = 0;
	private int times = 0;
	private int currentIndex = -1;
	
	/**
	 * Constructor.
	 * @param messages
	 * @param times
	 * @param delay
	 */
	public ArrayProducer(Message[] messages, int times, int delay) {
		this.messages = messages;
		this.times = times;
		this.delay = delay;
	}
	
	@Override
	public int delay() {
		return delay;
	}

	@Override
	public int times() {
		return times;
	}

	@Override
	public int size() {
		return (messages==null) ? 0 : messages.length;
	}

    /**
     * Returns the next Message-object in the sequence.  
     * The Modulus function counts the existing objects and loops when it reaches the end,
     * so index will always have a value between 0-9.
     */
	@Override
	public Message nextMessage() {
		if(size()==0) {
		    return null;
		}
		currentIndex = (currentIndex+1) % messages.length;
		return messages[currentIndex];
	}
}
