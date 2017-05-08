package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import user.serializabledata.SerializableUserModel;
import user.view.UserSocialPage;

/**
 * @author tahiaemran
 *
 * Model of all the User objects who have registered with the Game Authoring/ Playing 
 * Environment and facilitates communication between users via message
 *
 */
public class UsersModel {

	/**
	 * @author tahiaemran
	 * 
	 * nested class, allows encapsulated sending of messages from one user to the other 
	 * 
	 */
	public class MessagingHandler{
		public void sendMessage(String sender, String message, String reciever){
			sendAMessage(sender, message, reciever); 
		}
	}
	
	private Map<String, String> userToPass; 
	private Map<String, User> usernameToData; 
	private List<String> passwordVerify; 
	private User currentUser; 
	private UserSocialPage currentUserPage; 

	public UsersModel(){
		userToPass = new HashMap<String, String>(); 
		usernameToData = new HashMap<String, User>(); 
		passwordVerify = new ArrayList<String>();
	}

	private void sendAMessage(String sender, String message, String username){
		System.out.println("handler working");
		User reciever = usernameToData.get(username);
		reciever.recieveMessage(sender, message);
		
	}

	/**
	 * @param username - username of new user being registered
	 * @param password - password of new user being registered 
	 * 
	 * method called to register a new user (adds him/her to the model) 
	 */
	public void addUser(String username, String password){
		if (!userToPass.containsKey(username)){
			userToPass.put(username, password);
			passwordVerify.add(username+password);
			User user = new User(username, "resources/maple.jpg", new MessagingHandler());
			usernameToData.put(username, user);
		}
	}
	
	/**
	 * @param username - name of the user who is currently using the environment 
	 * 
	 * sets that user to the current user so that all the data from their game play/ authoring
	 * can be recorded
	 */
	public void setCurrentUser(String username) {
		this.currentUser = usernameToData.getOrDefault(username, 
				new User(username, "resources/maple.jpg", new MessagingHandler()));
		this.currentUserPage = new UserSocialPage(currentUser);
	}

	/**
	 * @return the User object associated with the current user 
	 */
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	/**
	 * @return the social page view associated with the current user 
	 */
	public Scene getUserSocialPage() {
		return new Scene(this.currentUserPage);
	}

	/**
	 * @param username - name of user trying to be verified 
	 * @param usernameandpassword - username and password entered, to be verified
	 */
	public void verifyUser(String username, String usernameandpassword){
		if (passwordVerify.contains(usernameandpassword)){
			currentUser = usernameToData.get(username); 
		}
	}

	/**
	 * @param string - the username associated with the User object that's trying 
	 * to be accessed 
	 * @return returns a User object associated with the entered username 
	 */
	public User getUserByName(String string) {
		return usernameToData.get(string);
	}
	
	/**
	 * @return the Serializable version of this class, to be used for saving/ loading
	 * the game data back in upon instantiation/ close 
	 */
	public SerializableUserModel getSerializable(){
		return new SerializableUserModel(this);
	}

	/**
	 * @return Username and Password data stored 
	 */
	public Map<String, String> getUserPasswordData() {
		return userToPass; 
	}

	/**
	 * @return Username and User object stored 
	 */
	public Map<String, User> getUserData() {
		return usernameToData; 
	}

	/**
	 * @return the List of usernameAndPassword as used to verify users when 
	 * they log in 
	 */
	public List<String> getVerificationStructure() {
		return passwordVerify; 
	}
	
	/**
	 * @return the current social center view, as defined 
	 * by the current user 
	 */
	public BorderPane getSocialView(){
		return currentUserPage; 
	}

}

