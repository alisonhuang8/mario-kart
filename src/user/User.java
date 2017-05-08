package user;

import java.util.Map;

import javafx.scene.image.Image;
import user.UsersModel.MessagingHandler;

/**
 * @author tahiaemran
 *
 * this class represents all the pertinent data for any user, used to construct their social page/ center 
 * and updated upon every game they author or play, or every message they send
 * 
 */
public class User {

	private String name; 
	private Image image; 

	private UserHistory history; 
	private MessagingHistory messages;
	
	private MessagingHandler handler; 

	public User(String username, String imageFile, MessagingHandler MH) {
		this.name = username; 
		this.image = new Image(imageFile, 150, 150, false, false);
		history = new UserHistory(); 
		messages = new MessagingHistory(username); 
		handler = MH; 
	}

	/**
	 * @param gameFile - file of the game that has been played
	 * @param score - score of the game that has been played
	 */
	public void onGameEnded(String gameFile, int score){
		history.checkHighScore(gameFile,score);
		history.incrementPlays(gameFile);
	}


	/**
	 * @param name - name of the game that has just been played
	 * @param gameFile - filepath of the game that has just been played
	 * 
	 * method used to record a game once it has been played 
	 */
	public void recordGamePlayed(String name, String gameFile){
		history.addPlayedGame(name, gameFile);
	}


	/**
	 * @param name - name of the game that has just been authored 
	 * @param authoredFile - filepath of the game that has just been authored
	 * 
	 * method used to record a game once it has been authored 
	 */
	public void recordAuthored(String name, String authoredFile){
		history.addAuthoredGame(name, authoredFile);
	}

	/**
	 * @return UserHistory object associated with the User
	 */
	public UserHistory getUserHistory(){
		return history; 
	}

	/**
	 * @return the user's name 
	 */
	public String getName(){
		return name; 
	}

	/**
	 * @return the user's image 
	 */
	public Image getImage(){
		return image; 
	}


	/**
	 * @param sender - person who sent the message
	 * @param message - message being sent 
	 * 
	 * method called to facilitate a message being recieved by a user 
	 */
	public void recieveMessage(String sender, String message) {
		messages.recieve(sender, message);
	}
	
	/**
	 * @param recipient - person intended to recieve the message
	 * @param message - message being sent 
	 * 
	 * method that allows a message to be sent by the user 
	 */
	public void sendMessage(String recipient, String message){
		handler.sendMessage(name, message, recipient);
		messages.addSent(recipient, message);
		}
	
	/**
	 * @return map of username to message to allow for the display of User messages
	 */
	public Map<String,String> getDisplayableMessages(){
		return messages.getDisplayableMessages();
	}

	/**
	 * @return the User's MessagingHistory 
	 */
	public MessagingHistory getMessagingHistory() {
		return this.messages;
	}

}
