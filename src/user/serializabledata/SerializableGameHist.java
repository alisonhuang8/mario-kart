package user.serializabledata;

import java.util.List;
import java.util.Map;

import user.GameHistory;
import user.GameStats;

/**
 * @author tahiaemran
 *
 * serializable version of the game history object
 *
 */
public class SerializableGameHist {
	
	private String name; 
	private String filePath; 
	private GameStats stats;  
	private Map<String, List<String>> comments; 

	public SerializableGameHist(GameHistory gameHistory) {
		this.name = gameHistory.getName();
		this.filePath = gameHistory.getFile();
		this.stats = gameHistory.getStatsForSerialization(); 
		this.comments = gameHistory.getComments();
	}
	
	/**
	 * @return the name of the game for which the history is being recorded
	 */
	public String extractName(){
		return name;
	}
	
	/**
	 * @return the name of the image file path of the game's icon, if defined
	 */
	public String extractImageFile(){
		return filePath;
	}
	
	/**
	 * @return the game stats associated with the game
	 */
	public GameStats extractStats(){
		return stats; 
	}
	
	/**
	 * @return comments posted about the game 
	 */
	public Map<String, List<String>> extractComments(){
		return comments; 
	}

}
