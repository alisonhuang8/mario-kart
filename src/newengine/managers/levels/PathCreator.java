package newengine.managers.levels;

import java.util.List;

import data.SpriteMakerModel;

//not part of masterpiece, just created as part of refactoring
/**
 * Path Creator interface for drawing paths on screen
 * @author Matthew Tribby
 */
public interface PathCreator {

	/**
	 * Creates path on screen
	 * @param pathSprites SpriteMakerModels of sprites with pathfollower components
	 */
	public void createPath(List<SpriteMakerModel> pathSprites);
}
