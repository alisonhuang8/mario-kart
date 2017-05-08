package user;

import javafx.util.Pair;

/**
 * @author tahiaemran
 * 
 * represents a message being sent 
 *
 */
public class Message {
	private String commenter; 
	private String comment; 
	
	public Message(String person, String message){
		this.commenter = person; 
		this.comment = message; 
	}
	
	/**
	 * @return the message (person who wrote it and the message itself)
	 */
	public Pair<String, String>getComment(){
		return new Pair<String, String>(commenter, comment);
	}
}
