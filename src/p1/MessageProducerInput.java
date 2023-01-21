package p1;
/* 
* Author: <Anna Selstam>  
* ID: <am3963>  
* Study program: <DT>
*/

/**
 * Creates an instance of a class X that implements MessageProducer. 
 * This is either Array-, Textfile- or ObjectfileProducer.. 
 * This instance is placed in a Buffer<MessageProducer> with help from 
 * the put() method. 
 * The instance is later retrieved from the Producer class via get().
 */
public class MessageProducerInput {
	private Buffer<MessageProducer> producerBuffer;
	
	public MessageProducerInput(Buffer<MessageProducer> mp) {
		producerBuffer = mp;
	}
	
	public void addMessageProducer(MessageProducer m) {
		producerBuffer.put(m);
	}
    
}
