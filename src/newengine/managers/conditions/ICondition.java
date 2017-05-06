package newengine.managers.conditions;

import newengine.model.PlayerRelationModel;
import newengine.model.PlayerStatsModel;
import newengine.model.SpriteModel;
//This entire file is part of my masterpiece.
//YOUR NAME
/**
 * This interface governs the expectation for a condition. A condition is used to determine whether a certain condition is met 
 * based on the data encapsulated in the condition. This is needed for the strategy design pattern of the ConditionManager
 * @author Matthew Tribby
 */
public interface ICondition {
	
	/**
	 * Returns true if condition is met, false if not met. This forms the basis of the our ConditionManager skills
	 * @return boolean representing if condition is met
	 */
	public boolean check(); 
	
	/**
	 * Needed method to set the models for the condition upon which it will draw information to act upon
	 * @param sprite SpriteModel
	 * @param stats PlayerStatsModel
	 * @param relations PlayerRelationModel
	 */
	public void setModels(SpriteModel sprite, PlayerStatsModel stats, PlayerRelationModel relations);
	
}
