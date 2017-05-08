package user;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tahiaemran
 * 
 * this class represents an object in which all stats for a particular game are kept track of
 *
 */
public class GameStats {

	private final String RATING_NAME = "Rating";
	private final String PLAYS_NAME = "Number of Plays";
	private final String LIKES_NAME = "Likes";

	private Integer rating; 
	private Integer numPlays; 
	private Integer numLikes; 

	private Map<String, String> displayableStats; 

	public GameStats(){
		rating = 0; 
		numPlays = 0; 
		numLikes = 0; 
		displayableStats = new HashMap<String, String>();
		displayableStats.put(RATING_NAME, Integer.toString(rating));
		displayableStats.put(PLAYS_NAME, Integer.toString(numPlays));
	}

	public void rate(int rate){
		rating = rate; 
		displayableStats.put(RATING_NAME, Integer.toString(rate));
	}

	/**
	 * increments the number of plays the game has recorded 
	 */
	public void incrementPlays(){
		numPlays++;
		displayableStats.put(PLAYS_NAME, Integer.toString(numPlays));
	}

	/**
	 * increments the number of likes the game has recorded 
	 */
	public void incrementLikes(){
		numLikes++;
		displayableStats.put(LIKES_NAME, Integer.toString(numLikes));
	}

	/**
	 * @return map of name of stat to stat for display 
	 */
	public Map<String,String> getDisplayableStats(){
		return displayableStats; 
	}

	/**
	 * @param name - name of the new, custom stat to be recorded
	 * @param value - initial value of the stat to be recorded
	 * 
	 * adds a custom statistic to be recorded in the game's history 
	 */
	public void addCustomStat(String name, String value){
		displayableStats.put(name, value);
	}

	/**
	 * @param name - name of the custom stat
	 * @return value of the custom stat in the history
	 */
	public String getCustomStat(String name){
		return displayableStats.get(name);
	}

}
