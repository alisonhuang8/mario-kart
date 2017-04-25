package newengine.sprite.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bus.BusEventHandler;
import helperAnnotations.ConstructorForDeveloper;
import newengine.events.SpriteModelEvent;
import newengine.events.sprite.FireProjectileEvent;
import newengine.skill.Skill;
import newengine.skill.SkillType;
import newengine.skill.skills.MoveSkill;
import newengine.sprite.Sprite;
import newengine.sprite.component.Component;
import newengine.sprite.component.ComponentType;
import newengine.sprite.components.Collidable.CollisionBoundType;
import newengine.utils.Target;
import newengine.utils.image.ImageSet;
import newengine.utils.image.LtubImage;

public class Attacker extends Component {

	public static final ComponentType<Attacker> TYPE = new ComponentType<>(Attacker.class.getName());
	private Sprite weapon;
	private double reloadPeriod = 1;
	private double timeRemaining = 1;
	
//	public Attacker(Sprite weapon) {
//		this.weapon = weapon;
//	}
	
	@ConstructorForDeveloper
	public Attacker(){
		
	}

	@Override
	public void afterAdded() {
		sprite.on(FireProjectileEvent.SPECIFIC,  e -> {
//			if (!weaponReloaded()){
//				return;
//			}
//			resetTimeRemaining();
			Sprite source = e.getSprite();
			Sprite target = e.getTarget();
//			Target target = e.getTarget();

			Sprite weapon = new Sprite();
			LtubImage image1 = new LtubImage("images/skills/bullet.png");
			ImageSet imageSet1 = new ImageSet(image1);
			Map<SkillType<? extends Skill>, Skill> skillMap = new HashMap<>();
			MoveSkill moveSkill = new MoveSkill();
			skillMap.put(MoveSkill.TYPE, moveSkill);
			weapon.addComponent(new SkillSet(skillMap));
			weapon.addComponent(new Owner(source.getComponent(Owner.TYPE).get().player()));
			weapon.addComponent(new Position(source.getComponent(Position.TYPE).get().pos(), source.getComponent(Position.TYPE).get().heading()));
			weapon.addComponent(new Images("images/skills/bullet.png"));
			weapon.addComponent(new Speed(100));
			weapon.addComponent(new Collidable(CollisionBoundType.IMAGE));
			weapon.addComponent(new DamageStrength(25));
			weapon.addComponent(new GameBus());			
			
			List<Sprite> spritesToAdd = new ArrayList<Sprite>();
			spritesToAdd.add(weapon);
			
			sprite.getComponent(GameBus.TYPE).get().getGameBus()
				.emit(new SpriteModelEvent(SpriteModelEvent.ADD, spritesToAdd));

			moveSkill.setTarget(new Target(target));
			moveSkill.trigger();
		});
	}

	@Override
	public ComponentType<? extends Component> getType() {
		return TYPE;
	}

	private boolean weaponReloaded() {
		return (timeRemaining < 0);
	}
	
	private void resetTimeRemaining(){
		timeRemaining = reloadPeriod;
	}
	
	
	public void onUpdate(double dt){
		timeRemaining -= dt;
	}

	@Override
	public Component clone() {
		Attacker clone = new Attacker();
		clone.reloadPeriod = this.reloadPeriod;
		clone.timeRemaining = this.timeRemaining;
		return clone;
	}


}
