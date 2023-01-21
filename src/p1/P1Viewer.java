package p1;
/* 
* Author: <Anna Selstam>  
* ID: <am3963>  
* Study program: <DT>
*/


/**
 * Class creates the GUI box and picture where the messages are pushed in 
 * via the Callback function from MessageManager.
 * @author Anna Selstam
 *
 */
public class P1Viewer extends Viewer implements MessageListener{

	/**
	 * Constructor.
	 * @param mm
	 * @param width
	 * @param height
	 */
    public P1Viewer(MessageManager mm, int width, int height) {
        super(width, height);
        mm.addMessageListener(this);     

        //Registrerar klassen som implementerar interfacet.       
        //mm.addMessageListener(new Messager()); 
    }

    /**
     * Calls for its parent class Viewer to send the message. 
     * @param message
     */
    private void sendMessage (Message message) {
        super.setMessage(message);
    }

    /**
     * Callback interface method.
     */
    @Override
    public void methodToCall(Message message) {
        sendMessage(message);
        //System.out.println("y ");
    }
    
    /**
     * Not used inner class.
     */
    /*public class Messager implements MessageListener{
    @Override
        public void methodToCall(Message message) {
            //sendMessage(message);
            System.out.println("x ");
        }
    }*/


}