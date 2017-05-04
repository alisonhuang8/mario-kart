package newengine.sprite.components;

import java.io.Serializable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import bus.BusEvent;
import bus.BusEventHandler;
import commons.MathUtils;
import commons.point.GamePoint;
import helperAnnotations.ConstructorForDeveloper;
import helperAnnotations.DeveloperMethod;
import helperAnnotations.VariableName;
import newengine.events.QueueEvent;
import newengine.events.SpriteModelEvent;
import newengine.events.sound.SoundEvent;
import newengine.events.sprite.ChangeHealthEvent;
import newengine.events.sprite.FireProjectileEvent;
import newengine.events.sprite.MoveEvent;
import newengine.events.sprite.StateChangeEvent;
import newengine.events.stats.ChangeLivesEvent;
import newengine.events.timer.DelayedEvent;
import newengine.sprite.Sprite;
import newengine.sprite.component.Component;
import newengine.sprite.component.ComponentType;
import newengine.utils.Target;

public class Position extends Component {
	public static final ComponentType<Position> TYPE = new ComponentType<>(Position.class.getName());
	private GamePoint pos = new GamePoint();
	private double heading;
	@XStreamOmitField
	private Target target;
	private boolean isMoving = false;
	private boolean followingSprite = false;

	public Position(GamePoint pos, double heading) {
		this.pos = pos;
		this.heading = heading;
	}
	
	public Position(GamePoint pos){
		this.pos = pos;
	}

	@ConstructorForDeveloper
	public Position(@VariableName(name = "xPosition") double xPos, @VariableName(name = "yPosition") double yPos,
			@VariableName(name = "heading") double heading) {
		this(new GamePoint(xPos, yPos), heading);
	}

	@Override
	public void afterAdded() {
		sprite.on(MoveEvent.START_POSITION, (e) -> {
			moveTo(e.getTarget());
			sprite.getComponent(SoundEffect.TYPE).ifPresent((sound) -> {
				sprite.getComponent(GameBus.TYPE).ifPresent((bus) -> {
					if (bus.getGameBus() == null) {
						System.out.println("main bus is null");
					}
					bus.getGameBus().emit(new SoundEvent(SoundEvent.SOUND_EFFECT, sound.getMoveSoundFile()));
				});
			});
		});
		sprite.on(MoveEvent.START_SPRITE, (e) -> {
			moveTo(e.getTarget());
			followingSprite();
			sprite.getComponent(SoundEffect.TYPE).ifPresent((sound) -> {
				sprite.getComponent(GameBus.TYPE).ifPresent((bus) -> {
					bus.getGameBus().emit(new SoundEvent(SoundEvent.SOUND_EFFECT, sound.getMoveSoundFile()));
				});
			});
		});
		sprite.on(MoveEvent.STOP, (e) -> {
			if (e.getSprite().getComponent(Weapon.TYPE).isPresent()) {
				sprite.getComponent(GameBus.TYPE).get().getGameBus()
						.emit(new SpriteModelEvent(SpriteModelEvent.REMOVE, e.getSprite()));
			}
		});

	}

	@Override
	public void onUpdated(double dt) {
		
		sprite.getComponent(PathFollower.TYPE).ifPresent((pathFollower) -> {
			
			if (sprite.getComponent(EventQueue.TYPE).get().isEmpty()) {
				System.out.println("sprite has stopped moving and reached end of path");
				sprite.getComponent(GameBus.TYPE).get().getGameBus().emit(new ChangeLivesEvent(ChangeLivesEvent.CHANGE,
						sprite.getComponent(Owner.TYPE).get().player(), -1));
				sprite.getComponent(GameBus.TYPE).get().getGameBus()
						.emit(new SpriteModelEvent(SpriteModelEvent.REMOVE, sprite));
			}
		});
		if (!isMoving()) {
			return;
		}
		GamePoint pDest = getFollowingPoint();
		updateMovePosition(dt, pDest);
	}

	private void updateMovePosition(double dt, GamePoint pDest) {
		if (!sprite.getComponent(Speed.TYPE).isPresent()) {
			return;
		}
		if (pDest == null){
			return;
		}
		
		double xDest = pDest.x();
		double yDest = pDest.y();
		double x = pos.x();
		double y = pos.y();

		double xDiff = xDest - x;
		double yDiff = yDest - y;
		double dist = pos.distFrom(pDest);
		double speed = sprite.getComponent(Speed.TYPE).get().speed();

		if (speed * dt > dist) {
			// arrives at destination at this frame.
			pos = new GamePoint(xDest, yDest);
			target.getSprite().ifPresent((targetSprite) -> {
				targetSprite.emit(new MoveEvent(MoveEvent.STOP, sprite, target));
				isMoving = false;
			});
			sprite.emit(new QueueEvent(QueueEvent.NEXT, new BusEvent(BusEvent.ANY)));
			return;
		}
		// not arrived at destination, move by time and speed.
		double vx = 0;
		double vy = 0;
		if (!MathUtils.doubleEquals(x, xDest) && MathUtils.doubleEquals(y, yDest)) {
			vx = xDiff > 0 ? speed : -speed;
		} else if (MathUtils.doubleEquals(x, xDest) && !MathUtils.doubleEquals(y, yDest)) {
			vy = yDiff > 0 ? speed : -speed;
		} else {
			vx = speed / dist * xDiff;
			vy = speed / dist * yDiff;
		}
		pos = new GamePoint(x + vx * dt, y + vy * dt);
		sprite.emit(new StateChangeEvent(StateChangeEvent.XPOS, sprite, pos.x()));
		sprite.emit(new StateChangeEvent(StateChangeEvent.YPOS, sprite, pos.y()));
		return;
	}

	@Override
	public ComponentType<? extends Component> getType() {
		return TYPE;
	}

	@Override
	public Position clone() {
		return new Position(pos, heading);
	}

	public GamePoint pos() {
		return pos;
	}

	public double xPos() {
		return pos.x();
	}

	public double yPos() {
		return pos.y();
	}

	public double heading() {
		return heading;
	}

	@DeveloperMethod
	private void moveTo(Target target) {
		this.target = target;
		startMoving();
	}

	@DeveloperMethod
	private void startMoving() {
		isMoving = true;
	}

	@DeveloperMethod
	private void stopMoving() {
		isMoving = false;
	}

	public boolean isMoving() {
		return isMoving;
	}

	private void followingSprite() {
		followingSprite = true;
	}

	private boolean isFollowingSprite() {
		return followingSprite;
	}

	private GamePoint getFollowingPoint() {
		if (isFollowingSprite()) {
			if (target.getSprite().isPresent()) {
				Sprite followedSprite = target.getSprite().get();
				if (followedSprite.getComponent(Position.TYPE).isPresent()) {
					return followedSprite.getComponent(Position.TYPE).get().pos();
				}
			}
		}
		return target.getLocation();
	}

	@Override

	public Object[] getGUIParameters() {
		Object[] parameters=new Object[3];
		parameters[0]=pos.x();
		parameters[1]=pos.y();
		parameters[2]=heading;
		return parameters;
	}
}
