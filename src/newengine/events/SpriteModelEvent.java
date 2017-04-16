package newengine.events;

import java.util.List;

import bus.BusEvent;
import bus.BusEventType;
import newengine.sprite.Sprite;

public class SpriteModelEvent extends BusEvent {

	public static final BusEventType<SpriteModelEvent> ADD = new BusEventType<>();
	public static final BusEventType<SpriteModelEvent> ADD_LIST = new BusEventType<>();
	public static final BusEventType<SpriteModelEvent> REMOVE = new BusEventType<>();
	
	private Sprite sprite; // TODO better distinguish single and list
	private List<Sprite> sprites;
	
	public SpriteModelEvent(BusEventType<? extends BusEvent> busEventType, Sprite sprite) {
		super(busEventType);
		this.sprite = sprite;
	}
	public SpriteModelEvent(BusEventType<? extends BusEvent> busEventType, List<Sprite> sprites) {
		super(busEventType);
		this.sprites = sprites;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	public List<Sprite> getSprites() {
		return sprites;
	}
	
}