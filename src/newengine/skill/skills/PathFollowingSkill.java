package newengine.skill.skills;

import commons.point.GamePoint;
import gameDevelopmentInterface.Path;
import newengine.events.QueueEvent;
import newengine.events.skill.TriggerSkillEvent;
import newengine.events.sprite.MoveEvent;
//import newengine.events.sprite.MoveEvent;
import newengine.skill.Skill;
import newengine.skill.SkillType;
import newengine.sprite.Sprite;
import newengine.utils.ActionMode;
import newengine.utils.Target;

public class PathFollowingSkill extends Skill {
	public static final SkillType<MoveSkill> TYPE = new SkillType<>(MoveSkill.class.getName());
	private Path myPath;

	public PathFollowingSkill(Path path) {
		myPath = path;
		// no image necessary? Will only be a monster-like sprite following an
		// invisible path
	}

	@Override
	public void trigger() {
		System.out.println("Being triggered");
		Sprite source = this.getSource().get();
		GamePoint gp = myPath.getPath().poll();
		MoveEvent event = new MoveEvent(MoveEvent.TYPE, source, new Target(gp));
		source.emit(new QueueEvent(QueueEvent.ADD, event));
	}

	@Override
	public SkillType<? extends Skill> getType() {
		return TYPE;
	}

}
