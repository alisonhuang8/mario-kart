package newengine.events.sprite;
import java.util.List;
import bus.BusEvent;
import bus.BusEventType;
import newengine.events.HasTriggeringSprite;
import newengine.sprite.Sprite;
import newengine.utils.Target;
public class MoveEvent extends BusEvent implements HasTriggeringSprite {
	
	public static final BusEventType<MoveEvent> TYPE = new BusEventType<>(MoveEvent.class.getName() + "SPECIFIC");
	private Sprite sprite;
	private List<Sprite> sprites;
	private Target target;
	public MoveEvent(BusEventType<? extends BusEvent> busEventType, Sprite sprite, Target target) {
		super(busEventType);
		this.sprite = sprite;
		this.target = target;
	}
	public MoveEvent(BusEventType<? extends BusEvent> busEventType, List<Sprite> sprites, Target target) {
		super(busEventType);
		this.sprites = sprites;
		this.target = target;
	}
	@Override
	public Sprite getSprite() {
		return sprite;
	}
	public List<Sprite> getSprites() {
		return sprites;
	}
	public Target getTarget() {
		return target;
	}
}