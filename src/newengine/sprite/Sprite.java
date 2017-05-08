// THIS IS MY MASTERPIECE CODE.
// Yilin Gao (yg95)
/*
 * The Sprite class is implemented by me, except for the SpriteState variable which was added later for creating sprites.
 * 
 * The Sprite class is a good feature which is close to future updates in sprite functions. 
 * Sprite is a list (map) of different components. 
 * Sprites know what components they have, and components know what functions they are responsible for.
 * 
 * Actually at first the Sprite class was not designed in this way. 
 * All functions were handled together in the sprite class because there were not many functions. 
 * To add a new function of sprites (like moving), we need to modify the update method of sprite directly,
 * and the class becomes larger and larger.
 * As the functions of sprites got more complex, we realized the problem and rewrote the entire Sprite class.
 * 
 * The composition design of sprite class is able to avoid direct modifications to the sprite class itself, 
 * and place modifications and updates in specific components.
 */

package newengine.sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import bus.BasicEventBus;
import bus.BusEvent;
import bus.BusEventHandler;
import bus.BusEventType;
import bus.EventBus;
import helperAnnotations.DeveloperMethod;
import newengine.sprite.component.Component;
import newengine.sprite.component.ComponentType;
import newengine.sprite.state.SpriteState;

/**
 * The class to represent sprites in the game.
 * Sprites are a composition of different {@code newengine.sprite.component.Component}. 
 * Components are identified by the {@code newengine.sprite.component.ComponentType}.
 * Depends on {@code bus.EventBus}, {@code newengine.sprite.component.Component}, 
 * {@code newengine.sprite.component.ComponentType}, {@code newengine.sprite.SpriteID},
 * {@code newengine.sprite.state.SpriteState}.
 * 
 * @author Yilin Gao
 */
public class Sprite {
	private EventBus spriteBus = new BasicEventBus();
	private SpriteID spriteID;
	private SpriteState myState;
	private Map<ComponentType<? extends Component>, Component> components = new HashMap<>();

	/**
	 * Constructor of the Sprite class. Generates SpriteID automatically.
	 */
	public Sprite() {
		myState = new SpriteState(this);
		spriteID = IDGenerator.generateID();
	}

	/**
	 * Constructor of the Sprite class. SpriteID are passed as a parameter.
	 * @param spriteID
	 */
	public Sprite(SpriteID spriteID) {
		myState = new SpriteState(this);
		this.spriteID = spriteID;
	}
	
	/**
	 * Get the {@code bus.EventBus} owned by the sprite.
	 * @return EventBus
	 */
	public EventBus getSpriteBus() {
		return spriteBus;
	}

	/**
	 * Register a event handler for a event type in this sprite.
	 * @param eventType
	 * @param eventHandler
	 */
	public <T extends BusEvent> void on(BusEventType<T> eventType, BusEventHandler<? super T> eventHandler) {
		spriteBus.on(eventType, eventHandler);
	}
	
	/**
	 * Emit a new event in this sprite.
	 * @param event
	 */
	public void emit(BusEvent event) {
		spriteBus.emit(event);
	}

	/**
	 * Get the SpriteID of the sprite.
	 * @return SpriteID
	 */
	public SpriteID getID() {
		return spriteID;
	}
	
	/**
	 * Get the SpriteState of the sprite.
	 * @return SpriteState
	 */
	public SpriteState getState(){
		return myState;
	}

	/**
	 * Add a component to the sprite.
	 * If there are existing component of the same type, the new one will 
	 * override the old one.
	 * @param component
	 */
	public void addComponent(Component component) {
		components.put(component.getType(), component);
		component.onAdded(this);
	}

	/**
	 * Get the component of a given ComponentType.
	 * @param type
	 * @return <T extends Component> Optional<T> deals with the condition that no such type component is present.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> Optional<T> getComponent(ComponentType<T> type) {
		return Optional.ofNullable((T) components.get(type));
	}

	/**
	 * Remove the component of a given type.
	 * @param type
	 */
	public <T extends Component> void removeComponent(ComponentType<T> type) {
		Component component = components.get(type);
		component.beforeRemoved();
		components.remove(type);
	}

	/**
	 * Checks if the sprite has a component of a given type.
	 * @param type
	 * @return boolean
	 */
	public <T extends Component> boolean hasComponent(ComponentType<T> type) {
		return components.containsKey(type);
	}	
	
	/**
	 * To be called in each frame.
	 * To update status of a sprite, the onUpdated(double) method of each component is called.
	 * @param dt
	 */
	@DeveloperMethod
	public void update(double dt) {
		for (Component component : components.values()) {
			component.onUpdated(dt);
		}
	}

	/**
	 * Return a deep copy of the Sprite object with a deep copy of each Component object.
	 */
	@Override
	public Sprite clone() {
		Sprite newSprite = new Sprite();
		for (Component component : new ArrayList<Component>(components.values())) {
			Component newComponent = component.clone();
			newSprite.addComponent(newComponent);
		}
		return newSprite;
	}
	
	
	@Override
	public String toString() {
		return "sprite(" + spriteID + ")";
	}

}
