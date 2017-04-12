package engine.spritecreation.keping.attributes;

import engine.sprite.attacker.Attacker;

public class AttackerBuilder {

	public Attacker buildAttacker(AttackerConfig attackerConfig) {
		Attacker attacker = new Attacker();
		attacker.setReloadPeriod(attackerConfig.getReloadTime());
		attacker.setRange(attackerConfig.getDetectionRange());
		return attacker;
	}
	
	
}
