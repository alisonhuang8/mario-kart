package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javafx.util.Pair;

/**
 * @author tahiaemran
 * 
 * Object that keeps track of messages sent and messages recieved for a particular user 
 *
 */
public class MessagingHistory extends Observable {
	
	private String myUserName; 
	
	private List<Message> myRecievedPosts; 
	private	List<Message> mySentMessages;
	private Map<String, List<Message>> myPrivateMessages;
	
	public MessagingHistory(String username){
		this.myUserName = username; 
		myRecievedPosts = new ArrayList<Message>();
		mySentMessages = new ArrayList<Message>();
		myPrivateMessages = new HashMap<String, List<Message>>();  // map of person being messaged by the user + the string of messages 
	}
	
	/**
	 * @param sender - sender of the message
	 * @param message - message being sent 
	 * 
	 * called when the user recieves a new message 
	 */
	public void recieve(String sender, String message){
		Message newMessage = new Message(sender, message);
		myRecievedPosts.add(newMessage);
		setChanged();
		notifyObservers(); 
	}
	
	/**
	 * @param recipient - person to whom this user sends a message
	 * @param message - string message being sent 
	 * 
	 * adds a sent message to the user's send history 
	 */
	public void addSent(String recipient, String message){
		mySentMessages.add(new Message(recipient, message));
	}
	
	/**
	 * @param other - recipient of the user's message
	 * @param message - message being sent 
	 * 
	 * allows for users to send private messages, which won't be displayed
	 * on the social page 
	 */
	public void sendPrivateMessage(String other, String message){
		if (!myPrivateMessages.containsKey(other)){
			myPrivateMessages.put(other, new ArrayList<Message>());
		}
		myPrivateMessages.get(other).add(new Message(other, message));
	}
	
	/**
	 * @param sender - sender of the private message
	 * @param message - message sent
	 * 
	 * allows the user to recieve a private message 
	 */
	public void recievePrivateMessage(String sender, String message){
		if (!myPrivateMessages.containsKey(sender)){
			myPrivateMessages.put(sender, new ArrayList<Message>());
		}
		myPrivateMessages.get(sender).add(new Message(sender, message));
	}
	
	/**
	 * @return public messages for display 
	 */
	public Map<String, String> getDisplayableMessages(){
		Map<String, String> dispMessages = new HashMap<String, String>(); 
		myRecievedPosts.stream().forEach(message -> {
			Pair<String,String> m = message.getComment();
			dispMessages.put(m.getKey(), m.getValue());
		});
		return dispMessages; 
	}

	/**
	 * @return the username associated with this messaging history 
	 */
	public String getUsername() {
		return myUserName; 
	}

	/**
	 * @return the posts recieved by the user 
	 */
	public List<Message> getRecievedPosts() {
		return myRecievedPosts; 
	}

	/**
	 * @return the messages sent by the user
	 */
	public List<Message> getSentMessages() {
		return mySentMessages;
	}

	/**
	 * @return the user's private message 
	 */
	public Map<String, List<Message>> getPrivateMessages() {
		return myPrivateMessages; 
	}

}
