package newengine.sprite.components;

import commons.point.GamePoint;
import data.SpriteMakerModel;
import helperAnnotations.ConstructorForDeveloper;
import helperAnnotations.VariableName;
import newengine.events.skill.TriggerSkillEvent;
import newengine.events.spawner.SpawnerDoneEvent;
import newengine.events.timer.DelayedEvent;
import newengine.events.timer.PeriodicEvent;
import newengine.skill.skills.BuildSkill;
import newengine.sprite.component.Component;
import newengine.sprite.component.ComponentType;
import newengine.utils.Target;

public class Spawner extends Component {
	public static final ComponentType<Spawner> TYPE = new ComponentType<>(Spawner.class.getName());
	private double secondsBetween;
	private int totalNumber;
	private boolean needToSpawn = true;
	private GamePoint startingPosition;

	@ConstructorForDeveloper
	public Spawner(@VariableName(name = "Monsters") int spritesToSpawn, double secondsBetween, SpriteMakerModel spriteToSpawn) {
		this.secondsBetween = secondsBetween;
		this.totalNumber = spritesToSpawn;
		this.startingPosition = spriteToSpawn.getComponent(PathFollower.TYPE).get().getPath().getPath().peek();
	}

	public int getNum() {
		return totalNumber;
	}

	public void onUpdated(double dt) {		
		if (needToSpawn) {
			sprite.getComponent(GameBus.TYPE).get().getGameBus().emit(new PeriodicEvent(totalNumber, secondsBetween, () -> 
			sprite.emit(new TriggerSkillEvent(BuildSkill.TYPE, new Target(startingPosition)))));
			needToSpawn = false;
		}
	}

	@Override
	public ComponentType<? extends Component> getType() {
		return TYPE;
	}

	@Override
	public Component clone() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object[] getGUIParameters() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setSpawnTime(double spawnTime){
		this.secondsBetween = spawnTime;
	}

}