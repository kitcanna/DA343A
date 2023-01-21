package p2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import p1.Message;
import p1.Viewer;

/**
 * @author Anna Selstam.
 * Receives Message-objects from {@link MessageClient} via PropertyChangeListener.
 */
public class P2Viewer extends Viewer implements PropertyChangeListener{
	private MessageClient mc;
	
	/**
	 * @Constructor
	 * @param messageClient
	 * @param xSize
	 * @param ySize
	 */
	public P2Viewer(MessageClient messageClient, int xSize, int ySize) {
		super(xSize,ySize);
		this.mc = messageClient;
		mc.addPopertyChangeListener(this);
	}

	/**
	 * Calls for its parent class Viewer to send the message.
	 * @param msg
	 */
	public void setMessage(Message msg) {
		super.setMessage(msg);
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		setMessage((Message) arg0.getNewValue());		
	}
}