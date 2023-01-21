package p1;
/* 
* Author: <Anna Selstam>  
* ID: <am3963>  
* Study program: <DT>
*/

import java.io.Serializable;
import javax.swing.Icon;


/**
 * Class contains data about the Message-object.
 * @author Anna Selstam
 *
 */
public class Message implements Serializable{
    
    private String text;
    private Icon icon;

    public Message (String text, Icon icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() { return text; }
    public Icon getIcon() { return icon; }
}
