package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tahiaemran
 * 
 * each user's game history for both authored and played games 
 * 
 *
 */
public class UserHistory {
	
	Map<String, String> gameToFile; 
	List<String> authored; 
	List<String> played;
	Map<String, GameHistory> gameToHistory;
	Map<String, GameHistory> authToHistory;
	
	public UserHistory(){
		gameToFile = new HashMap<String, String>();  
		authored = new ArrayList<String>();
		played = new ArrayList<String>(); 
		
		authToHistory = new HashMap<String, GameHistory>();
		gameToHistory = new HashMap<String, GameHistory>();
	}
		
	/**
	 * @return a map of game name to file path of that game 
	 */
	public Map<String, String> getFileMap(){
		return this.gameToFile;
	}
	
	/**
	 * @return list of the names of Games Authored by the associated user 
	 */
	public List<String> getAuthoredGames(){
		return authored; 
	}
	
	/**
	 * @return list of the names of Games Played by the associated user 
	 */
	public List<String> getPlayedGames(){
		return played; 
	}
	
	/**
	 * @return map of Game Name to GameHistory object for each game played by the associated user
	 */
	public Map<String, GameHistory> getPlayedHistory(){
		return gameToHistory; 
	}
	
	/**
	 * @return map of Game Name to GameHistory object for each game authored by the associated user
	 */
	public Map<String, GameHistory> getAuthoredHistory(){
		return authToHistory; 
	}

	/**
	 * @param name - name of the game
	 * @param filepath - filepath of the game 
	 * 
	 * adds a played game to the User's history
	 * 
	 */
	public void addPlayedGame(String name, String filepath){
		gameToFile.put(name, filepath);
		played.add(name);
		GameHistory history = configGame(name, filepath);
		gameToHistory.put(name, history);
	}
	
	/**
	 * @param name - name of the game
	 * @param filepath - filepath of the game
	 * 
	 * adds an authored game to the User's history 
	 */
	public void addAuthoredGame(String name, String filepath){
		gameToFile.put(name, filepath);
		played.add(name);
		GameHistory authHistory = configAuth(name, filepath);
		authToHistory.put(name, authHistory);
	}


	/**
	 * @param name - name of game being rated
	 * @param rating - rating for the game 
	 */
	public void rateGame(String name, int rating){
		if(authored.contains(name)){
			authToHistory.get(name).addRating(rating);
		}
		if(played.contains(name)){
			gameToHistory.get(name).addRating(rating);
		}
	}
	

	/**
	 * @param name - name of game that's been played 
	 * 
	 * method called to increase the number of plays for a game after its been played
	 */
	public void incrementPlays(String name){
		if(authored.contains(name)){
			authToHistory.get(name).incrementPlays(); 
		}
		if(played.contains(name)){
			gameToHistory.get(name).incrementPlays(); 
		}
	}
	

	/**
	 * @param username - user name who is commenting 
	 * @param comment - comment they're posting 
	 */
	public void addComment(String username, String comment){
		if(authored.contains(username)){
			authToHistory.get(username).addComment(username, comment);
		}
		if(played.contains(username)){
			gameToHistory.get(username).addComment(username, comment);
		}
	}
	
	
	private GameHistory configAuth(String name, String filepath) {
		GameHistory GH = new GameHistory(name, filepath);
		return GH; 
	}

	private GameHistory configGame(String name, String filepath) {
		GameHistory GH = new GameHistory(name, filepath);
		GH.addRecordedStat("High Score", Integer.toString(0));
		return GH;
	}



	/**
	 * @param gameFile - filepath of game played
	 * @param score - score of the game when it's done being played 
	 * 
	 * method used to check if the current high score has been beat after a 
	 * game has been played 
	 * 
	 */
	public void checkHighScore(String gameFile, int score) {
		if(authored.contains(gameFile)){
				if (Integer.parseInt(gameToHistory.get(gameFile).getCustomStat("High Score")) < score){
					gameToHistory.get(gameFile).addRecordedStat("High Score", Integer.toString(score));
				}
		}
		if(played.contains(gameFile)){
			if (Integer.parseInt(gameToHistory.get(gameFile).getCustomStat("High Score")) < score){
				gameToHistory.get(gameFile).addRecordedStat("High Score", Integer.toString(score));
			}
		}
		
	}
	
	

	
}
