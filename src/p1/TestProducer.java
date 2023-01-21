package p1;

import javax.swing.ImageIcon;

public class TestProducer {
    private static ArrayProducer getArrayProducer(int times, int delay) {
        Message[] messages = { new Message("UP",new ImageIcon("C:\\Users\\Anna\\Desktop\\SELANN_P1\\images\\new1.jpg")),
                new Message("Going down.",new ImageIcon("C:\\Users\\Anna\\Desktop\\SELANN_P1\\images\\new2.jpg")),
                new Message("Going down..",new ImageIcon("C:\\Users\\Anna\\Desktop\\SELANN_P1\\images\\new3.jpg")),
                new Message("Going down...",new ImageIcon("C:\\Users\\Anna\\Desktop\\SELANN_P1\\images\\new4.jpg")),
                new Message("Going down....",new ImageIcon("C:\\Users\\Anna\\Desktop\\SELANN_P1\\images\\new5.jpg")),
                new Message("Almost down",new ImageIcon("C:\\Users\\Anna\\Desktop\\SELANN_P1\\images\\new6.jpg")),
                new Message("DOWN",new ImageIcon("C:\\Users\\Anna\\Desktop\\SELANN_P1\\images\\new7.jpg")),
                new Message("Going up.",new ImageIcon("C:\\Users\\Anna\\Desktop\\SELANN_P1\\images\\new8.jpg")),
                new Message("Going up..",new ImageIcon("C:\\Users\\Anna\\Desktop\\SELANN_P1\\images\\new9.jpg")),
                new Message("Almost up",new ImageIcon("C:\\Users\\Anna\\Desktop\\SELANN_P1\\images\\new10.jpg")) };
        return new ArrayProducer(messages,times,delay);       
    }
    
	public static void main(String[] args) {
		Viewer viewer = new Viewer(300,200);
		Viewer.showPanelInFrame(viewer,"From Buffer<Message>",100,30);
		Buffer<MessageProducer> producerBuffer	= new Buffer<MessageProducer>();
		Buffer<Message> messageBuffer	= new Buffer<Message>();
				
		MessageProducerInput mpInput = new MessageProducerInput(producerBuffer);		
        mpInput.addMessageProducer(getArrayProducer(2,1000));
        mpInput.addMessageProducer(new TextfileProducer("C:\\Users\\Anna\\Desktop\\SELANN_P1\\files\\new.txt"));
		
		Producer producer = new Producer(producerBuffer,messageBuffer);
		producer.start();
		
		MessageConsumer testDs2 = new MessageConsumer(messageBuffer,viewer);
		testDs2.start();
	}
}

class MessageConsumer {
	private Viewer viewer;
	private Buffer<Message> buffer;
	private Thread thread;
	
	public MessageConsumer(Buffer<Message> buffer, Viewer viewer) {
		this.buffer = buffer;
		this.viewer = viewer;
	}
	
	public void start() {
		if(thread==null) {
			thread = new Worker();
			thread.start();
		}
	}

	private class Worker extends Thread {
		public void run() {
			Message message;
			while(!Thread.interrupted()) {
				try {
					message = buffer.get();
					viewer.setMessage(message);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}
}
