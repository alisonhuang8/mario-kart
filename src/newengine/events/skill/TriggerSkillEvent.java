package newengine.events.skill;

import java.util.Optional;

import bus.BusEvent;
import bus.BusEventType;
import newengine.skill.Skill;
import newengine.skill.SkillType;
import newengine.sprite.IDGenerator;
import newengine.sprite.SpriteID;
import newengine.utils.Target;

public class TriggerSkillEvent extends BusEvent {
	private SpriteID ID = IDGenerator.generateID();
	
	public static final BusEventType<TriggerSkillEvent> ANY = new BusEventType<>(
			TriggerSkillEvent.class.getName() + "ANY");

	private SkillType<? extends Skill> type;
	private Target target;
	
	public TriggerSkillEvent(SkillType<? extends Skill> type) {
		this(type, null);
	}
	
	public TriggerSkillEvent(SkillType<? extends Skill> type, Target target) {
		super(ANY);
		this.type = type;
		this.target = target;
	}
	
	public SkillType<? extends Skill> getType() {
		return type;
	}
	
	public Optional<Target> getTarget() {
		return Optional.ofNullable(target);	
	}
	
	public SpriteID getID(){
		return ID;
	}

}
