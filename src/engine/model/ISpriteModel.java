package engine.model;

import java.util.Collection;
import java.util.List;


public interface ISpriteModel<T> {
	public void addSprite(T sprite);
	
	public void removeSprite(T sprite);
	
	public List<T> getSprites();
}
