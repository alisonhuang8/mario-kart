package engine.spritecreation.keping.attributes;

import engine.sprite.movable.Movable;

public class MovableBuilder {

	public Movable buildMovable(MovableConfig config) {
		Movable movable = new Movable();
		movable.setSpeed(config.getSpeed());
		return new Movable();
	}
	
}
