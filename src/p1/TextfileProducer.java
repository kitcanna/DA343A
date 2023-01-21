package p1;
/* 
* Author: <Anna Selstam>  
* ID: <am3963>  
* Study program: <DT>
*/

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.swing.ImageIcon;

/** 
*   @author Anna Selstam
*   Class creates an array of Messages from a text-file.
*   time = (x) number of repetitions of sequence
*   delay = (y) milliseconds/picture
*   size = (z) number of messages in the sequence
*   
*/
public class TextfileProducer implements MessageProducer {
    private int times = 0;      
    private int delay = 0;         
    private int size = 0;           
    private int index = -1;
    private Message[] messages;

    /**
     * Constructor
     * @param filename String - file new.txt
     * Retrieves values from a text file, where the structure is the same as seen below.
     */
    public TextfileProducer (String filename) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            times = Integer.parseInt(br.readLine());            //get (x) 
            delay = Integer.parseInt(br.readLine());            //get (y)
            size = Integer.parseInt(br.readLine());             //get (z)
            messages = new Message[size];                      

            //Assigns the object its messages from the file new.txt
            for (int i = 0; i < messages.length; i++) {
                String text = br.readLine();
                ImageIcon icon =  new ImageIcon(br.readLine());
                messages[i] = new Message(text, icon);
            }
            br.close();     //Closing the stream
            info();         //Prints info about the stream (x, y, z)
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * (X) Returns the amount of times the Message-sequence will loop. 
	 */
    @Override
    public int times() {
        return times; 
    }
	/**
	 * (Y) Returns the number of milliseconds between each Message object (image).
	 */
    @Override
    public int delay() {
        return delay;
    }
	/**
	 * (Z) Returns the size of the Message sequence (number of messages in the sequence).
	 */
    @Override
    public int size() {
        if (messages != null) {
            return messages.length;
        }
        else {
            return 0;
        }
    }
    
    /**
     * Returns the next Message-object in the sequence.  
     * The Modulus function counts the existing objects and loops when it reaches the end,
     * so index will always have a value between 0-9.
     */
    @Override
    public Message nextMessage() {
        if (size() == 0) {
            return null;
        }
        index = (index + 1) % messages.length;
        return messages[index];
    }
    
}