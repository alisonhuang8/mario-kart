package newengine.sprite.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bus.BusEventHandler;
import helperAnnotations.ConstructorForDeveloper;
import helperAnnotations.VariableName;
import newengine.events.SpriteModelEvent;
import newengine.events.sprite.FireProjectileEvent;
import newengine.events.sprite.StateChangeEvent;
import newengine.events.sprite.UpgradeEvent;
import newengine.skill.Skill;
import newengine.skill.SkillType;
import newengine.skill.skills.MoveSkill;
import newengine.sprite.Sprite;
import newengine.sprite.component.Component;
import newengine.sprite.component.ComponentType;
import newengine.sprite.components.Collidable.CollisionBoundType;
import newengine.sprite.weapons.Projectile;
import newengine.sprite.weapons.Weapon;
import newengine.utils.Target;
import newengine.utils.image.ImageSet;
import newengine.utils.image.LtubImage;

public class Attacker extends Component {

	public static final ComponentType<Attacker> TYPE = new ComponentType<>(Attacker.class.getName());
	private double reloadPeriod = 1;
	private double timeRemaining = 1;
	private int damageStrength;
	private LtubImage ltubImage;
	
//	public Attacker(Sprite weapon) {
//		this.weapon = weapon;
//	}
	
	@ConstructorForDeveloper
	public Attacker(@VariableName(name="damageStrength")int damageStrength, @VariableName(name="LtubImage")LtubImage ltubImage){
		this.damageStrength = damageStrength;
		this.ltubImage = ltubImage;
	}

	@Override
	public void afterAdded() {
		sprite.emit(new StateChangeEvent(StateChangeEvent.ATTACKER, sprite, true));
		sprite.on(UpgradeEvent.DOUBLE, e -> {
			System.out.println("before upgrade damage strength " + damageStrength);
			damageStrength = damageStrength*2;
			System.out.println("after upgrade damage strength " + damageStrength);
		});
		
		sprite.on(FireProjectileEvent.SPECIFIC, e -> {
			Sprite source = e.getSprite();
			Sprite target = e.getTarget();

			Sprite weapon = new Sprite();
			weapon.addComponent(new Weapon(source, target, Projectile.TYPE));
			
			List<Sprite> spritesToAdd = new ArrayList<Sprite>();
			spritesToAdd.add(weapon);
			
			sprite.getComponent(GameBus.TYPE).get().getGameBus()
				.emit(new SpriteModelEvent(SpriteModelEvent.ADD, spritesToAdd));
			
			sprite.getComponent(GameBus.TYPE).ifPresent((gameBus) -> {
				System.out.println(gameBus.getGameBus() == null);
			});

			Skill moveSkill = weapon.getComponent(SkillSet.TYPE).get().getSkill(MoveSkill.TYPE);
			
			moveSkill.setTarget(new Target(target));
			moveSkill.trigger();
		});
	}
	
	@Override
	public ComponentType<? extends Component> getType() {
		return TYPE;
	}
	
	public int getDamageStrength(){
		return damageStrength;
	}

	public void onUpdate(double dt){
		timeRemaining -= dt;
	}

	@Override
	public Component clone() {
		Attacker clone = new Attacker(damageStrength, ltubImage);
		clone.reloadPeriod = this.reloadPeriod;
		clone.timeRemaining = this.timeRemaining;
		return clone;
	}

	@Override
	public Object[] getGUIParameters() {
		Object[] parameters=new Object[2];
		parameters[0]=damageStrength;
		parameters[1]=ltubImage;
		return parameters;
	}


}
