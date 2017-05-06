package newengine.managers.levels;

//This entire file is part of my masterpiece.
//Matthew Tribby
/**
 * The goal of this interface is to set forward the expectation of what a LevelManager should be that is both extensible
 * and non-genre specific. These basic methods defined here from a clean API and encapsulate inner implementation. This
 * interface is well designed because the methods are simple, easy to use, and nothing is known about the underneath
 * implementation features. 
 * @author Matthew Tribby
 */
public interface ILevelManager {

	/**
	 * Calls the LevelManager to load the next level. If there is no next level then winning screen will be shown
	 */
	public void nextLevel();
	
	/**
	 * Calls the LevelManager to reset the current level. This will reload in the currently selected level
	 */
	public void resetLevel();
	
	/**
	 * Sets the level, if one is present, corresponding to the number being passed in and the order of the ILevelData 
	 * inside the LevelManager
	 * @param newLevel integer of the level number. This number if not an index number but is a number referring to
	 * 		  the order of the levels i.e. the second level in the level manager is 2.
	 */
	public void setLevel(int newLevel);
}
