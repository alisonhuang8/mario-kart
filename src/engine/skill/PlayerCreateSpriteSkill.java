package engine.skill;

import bus.EventBus;
import engine.camera.GamePoint;
import engine.model.SpriteModelEvent;
import engine.sprite.Sprite;

public class PlayerCreateSpriteSkill implements Skill {

	private EventBus bus;
	private Sprite sprite;
	
	public PlayerCreateSpriteSkill(EventBus bus, Sprite sprite) {
		this.bus = bus;
		this.sprite = sprite;
	}
	
	@Override
	public void trigger(GamePoint pos) {
		sprite.setInitialPos(pos);
		bus.emit(new SpriteModelEvent(SpriteModelEvent.ADD, sprite));
	}

	
}