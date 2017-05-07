// This entire file is part of my masterpiece.
// Alison Huang

/**
 * this is an example of a weapon type. the parameters like weaponImage, damageStrength, attackRange, and
 * travelSpeed will be set by the game developer using the authoring environment.
 * 
 * This shows how simple it is to create a new weapon. Just make sure to assign it a type so you
 * can add it to the composition map.
 */

package newengine.sprite.weapons;

import newengine.sprite.component.ComponentType;
import newengine.utils.image.LtubImage;

public class Projectile extends WeaponObject{

	public static final WeaponType<Projectile> TYPE = new WeaponType<>(Projectile.class.getName());

	public Projectile(LtubImage weaponImage, int damageStrength, double attackRange, double travelSpeed) {
		super(weaponImage, damageStrength, attackRange, travelSpeed);
	}

	@Override
	public WeaponType<? extends WeaponObject> getType() {
		return TYPE;
	}

}
