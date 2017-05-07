// This entire file is part of my masterpiece.
// Alison Huang
/**
 * @author AlisonHuang
 * Concepts applied in this masterpiece are object hierarchies and composition.
 * This is the component that the actual monster sprite will need in order to launch 
 * any kind of attack concerning weapons.
 * The weapons map is used to store the different types of weapons.
 * When you assign the weapon component to a sprite that will be the weapon, you must tell it
 * who the source of the attack is, who the target is, and what type of weapon you want to use.
 * This assignment of information will be done by the monster sprite that is launching the attack.
 * For this, see line 65 in the attacker component.
 * The map will keep track of all the weapons that this sprite has and will add and remove based on weapon types.
 * 
 * The composition used here is good so that you can easily change the weapon arsenal without changing the 
 * fundamental aspect of being an attacker. It allows for flexibility of adding new weapons.
 */
package newengine.sprite.weapons;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import newengine.skill.Skill;
import newengine.skill.SkillType;
import newengine.skill.skills.MoveSkill;
import newengine.sprite.Sprite;
import newengine.sprite.component.Component;
import newengine.sprite.component.ComponentType;
import newengine.sprite.components.Collidable;
import newengine.sprite.components.DamageStrength;
import newengine.sprite.components.GameBus;
import newengine.sprite.components.Images;
import newengine.sprite.components.Owner;
import newengine.sprite.components.Position;
import newengine.sprite.components.Range;
import newengine.sprite.components.SkillSet;
import newengine.sprite.components.Speed;
import newengine.sprite.components.Collidable.CollisionBoundType;

public class Weapon extends Component {
	public static final ComponentType<Weapon> TYPE = new ComponentType<>(Weapon.class.getName());
	
	private Map<WeaponType<? extends WeaponObject>, WeaponObject> weapons = new HashMap<>();
	private Sprite source;
	private Sprite target;
	private WeaponType type;
	private WeaponObject weaponObject;
	
	public Weapon(Sprite source, Sprite target, WeaponType type){
		this.source = source;
		this.target = target;
		this.type = type;
		this.weaponObject = weapons.get(type);
		configureWeapon();
	}
	
	private void configureWeapon(){
		Map<SkillType<? extends Skill>, Skill> skillMap = new HashMap<>();
		MoveSkill moveSkill = new MoveSkill();
		skillMap.put(MoveSkill.TYPE, moveSkill);
		sprite.addComponent(new SkillSet(skillMap));
		sprite.addComponent(new Owner(source.getComponent(Owner.TYPE).get().player()));
		sprite.addComponent(new Position(source.getComponent(Position.TYPE).get().pos(), source.getComponent(Position.TYPE).get().heading()));
		sprite.addComponent(new Images(weaponObject.getWeaponImage()));
		sprite.addComponent(new Speed(weaponObject.getTravelSpeed()));
		sprite.addComponent(new Collidable(CollisionBoundType.IMAGE));
		sprite.addComponent(new DamageStrength(weaponObject.getDamageStrength()));
		sprite.addComponent(new Range(weaponObject.getAttackRange()));
		sprite.addComponent(new GameBus());	
	}
	
	@Override
	public ComponentType<? extends Component> getType() {
		return TYPE;
	}

	public void addWeapon(WeaponObject weapon) {
		if (weapon == null) {
			System.out.println("weapon is null");
		}
		if (weapon.getType().equals(Images.TYPE)) {
			System.out.println("Images component is getting added to the sprite");
		}
		weapons.put(weapon.getType(), weapon);
	}

	@SuppressWarnings("unchecked")
	public <T extends WeaponObject> Optional<T> getWeapon(WeaponType<T> type) {
		return Optional.ofNullable((T) weapons.get(type));
	}

	public <T extends WeaponObject> void removeWeapon(WeaponType<T> type) {
		weapons.remove(type);
	}

	public <T extends WeaponObject> boolean hasWeapon(WeaponType<T> type) {
		return weapons.containsKey(type);
	}	
	
	@Override
	public Component clone() {
		return new Weapon(source, target, type);
	}

	@Override
	public Object[] getGUIParameters() {
		// TODO Auto-generated method stub
		return null;
	}

}
