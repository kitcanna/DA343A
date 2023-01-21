package p1;
/* 
* Author: <Anna Selstam>  
* ID: <am3963>  
* Study program: <DT>
*/
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ObjectfileProducer implements MessageProducer {
    private int times = 0;
    private int delay = 0;
    private int size = 0;
    private int index = -1;
    private Message[] messages;

    public ObjectfileProducer (String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            times = ois.readInt();
            delay = ois.readInt();
            size = ois.readInt();
            messages = new Message[size];

            for (int i = 0; i < messages.length; i++) {
                messages[i] = (Message)ois.readObject();
            }
            info();
        } 
        catch (Exception e) {
           e.printStackTrace();
        }

    }

    @Override
    public int times() {
        return times; 
    }

    @Override
    public int delay() {
        return delay;
    }

    @Override
    public int size() {
        if (messages != null) {
            return messages.length;
        }
        else {
            return 0;
        }
    }

    @Override
    public Message nextMessage() {
        if (size() == 0) {
            return null;
        }
        index = (index + 1) % messages.length;
        return messages[index];
    }
}