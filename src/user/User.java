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
 * assumptions:
 * 
 * dependencies:
 * 
 * use case:
 * 
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

	public void onGameEnded(String gameFile, int score){
		history.checkHighScore(gameFile,score);
		history.incrementPlays(gameFile);
	}


	public void recordGamePlayed(String name, String gameFile){
		history.addPlayedGame(name, gameFile);
	}


	public void recordAuthored(String name, String authoredFile){
		history.addAuthoredGame(name, authoredFile);
	}

	public UserHistory getUserHistory(){
		return history; 
	}

	public String getName(){
		return name; 
	}

	public Image getImage(){
		return image; 
	}


	public void recieveMessage(String sender, String message) {
		messages.recieve(sender, message);
	}
	
	public void sendMessage(String recipient, String message){
		handler.sendMessage(name, message, recipient);
		messages.addSent(recipient, message);
		}
	
	public Map<String,String> getDisplayableMessages(){
		return messages.getDisplayableMessages();
	}

	public MessagingHistory getMessagingHistory() {
		return this.messages;
	}

}
