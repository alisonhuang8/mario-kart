// This entire file is part of my masterpiece.
// Alison Huang

/**
 * This is the abstract class that will be used for all weapon types.
 * 
 * The object hierarchy used here is good for adding new weapons. They all have similar features like speed,
 * range, strength, and image so it makes sense to put them all in one object.
 */

package newengine.sprite.weapons;

import newengine.utils.image.LtubImage;

public abstract class WeaponObject {
	
	private LtubImage weaponImage;
	private int damageStrength;
	private double attackRange;
	private double travelSpeed;
	
	public WeaponObject(LtubImage weaponImage, int damageStrength, double attackRange, double travelSpeed){
		this.damageStrength = damageStrength;
		this.attackRange = attackRange;
		this.travelSpeed = travelSpeed;
	}
	
	public abstract WeaponType<? extends WeaponObject> getType();
	
	public LtubImage getWeaponImage(){
		return weaponImage;
	}
	
	public int getDamageStrength(){
		return damageStrength;
	}
	
	public double getAttackRange(){
		return attackRange;
	}
	
	public double getTravelSpeed(){
		return travelSpeed;
	}

}
