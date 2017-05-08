package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * @author tahiaemran
 * 
 * history object for every game authored or played 
 *
 */

public class GameHistory extends Observable {
	
	private String name; 
	private String filePath; 
	private GameStats stats;  
	private Map<String, List<String>> comments; 
	
	
	public GameHistory(String name, String filepath){
		this.name = name; 
		this.filePath = filepath; 
		stats = new GameStats(); 
		comments = new HashMap<String, List<String>>(); 
	}
	
	
	/**
	 * @return Map of String gamename to a List of comments associated with the game
	 */
	public Map<String, List<String>>getComments(){
		return comments; 
	}
	
	/**
	 * @return the recorded statistics associated with the game
	 */
	public Map<String,String> getStats(){
		return stats.getDisplayableStats();
	}
	
	/**
	 * @return name of the game with which this history object is associated 
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return file path of the game with which this history object is associated 
	 */
	public String getFile(){
		return filePath; 
	}
	
	/**
	 * @param user - user who is commenting on the game
	 * @param message - comment by the user on the game
	 * 
	 * allows for comments on the game to be recorded 
	 */
	public void addComment(String user, String message){
		if (!comments.containsKey(user)){
			comments.put(user, new ArrayList<String>());
		}
		comments.get(user).add(message);
		
	}
	
	/**
	 * @param statName - name of statistic being recorded
	 * @param stat - the statistic 
	 * 
	 * method called to add a stat to be recorded 
	 */
	public void addRecordedStat(String statName, String stat){
		stats.addCustomStat(statName, stat);
	}
	
	/**
	 * @param name - name of the stat 
	 * @return whether or not said stat is being recorded for the associated game
	 */
	public boolean containsRecordedStat(String name){ 
		return (stats.getCustomStat(name)!=null);
	}
	
	/**
	 * @param name - name of the custom stat
	 * @return the value of the custom statistic being recorded 
	 */
	public String getCustomStat(String name){
		return stats.getCustomStat(name);
	}
	
	/**
	 * @param rating - the integer rating
	 * adds a rating to the game 
	 */
	public void addRating(Integer rating){
		stats.rate(rating);
	}
	
	/**
	 * increments the number of plays to the game associated
	 */
	public void incrementPlays(){
		stats.incrementPlays();
		setChanged();
		notifyObservers(); 
	}
	
	/**
	 * increments the number of likes to the game associated 
	 */
	public void incrementLikes(){
		stats.incrementLikes();
		setChanged();
		notifyObservers();
	}
	
	/**
	 * @return returns the stats of the game for serialization 
	 */
	public GameStats getStatsForSerialization(){
		return stats; 
	}
	
}
