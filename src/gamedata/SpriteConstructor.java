package gamedata;

import data.SpriteMakerModel;
import newengine.sprite.Sprite;

/**
 * @author tahiaemran
 *
 *this interface defines the methods needed to build a sprite in a concrete builder class 
 *
 */
public interface SpriteConstructor {
	/**
	 * @param model - the model that represents the sprite to be created 
	 * @return the Sprite created by the builder class
	 */
	public Sprite getConstructedSprite(SpriteMakerModel model); 
}
