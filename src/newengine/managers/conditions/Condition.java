package newengine.managers.conditions;

import newengine.model.PlayerRelationModel;
import newengine.model.PlayerStatsModel;
import newengine.model.SpriteModel; 
//This entire file is part of my masterpiece.
//Matthew Tribby
/**
 * Condition which is to be extended to fit a game's winning or losing condition. The goal of a condition is basically
 * to be a complex boolean that returns true when the condition is met. This can then be used by the condition manager
 * to trigger events to either progress the game or let the user know that they have lost. This code is well designed
 * because it provides a basic abstract class through which all future conditions can be easily made. Although there
 * are a lot of getter and setters in this class, they are necessary to set the models and allow the extender of this
 * class is to access the models to then check conditions powerfully.
 * The interface of ICondition allows us to utilize the strategy design pattern.
 * @author Matthew Tribby
 */
public abstract class Condition implements ICondition {
	private SpriteModel spriteModel;
	private PlayerStatsModel playerStatsModel;
	private PlayerRelationModel playerRelationModel;
	
	/**
	 * This blank constructor is built in to allow users to initialize a Condition without having to worry about the models
	 */
	public Condition(){ }
	
	/**
	 * Allows the user to set the models
	 * @param sprite SpriteModel
	 * @param stats PlayerStatsModel
	 * @param relations PlayerRelationsModel
	 */
	public void setModels(SpriteModel sprite, PlayerStatsModel stats, PlayerRelationModel relations){
		this.spriteModel = sprite;
		this.playerStatsModel = stats;
		this.playerRelationModel = relations;
	}
	
	/**
	 * Gets player stats model
	 * @return PlayerStatsModel
	 */
	public PlayerStatsModel getPlayerStatsModel(){
		return playerStatsModel;
	}
	
	/**
	 * Gets sprite Model 
	 * @return SpriteModel
	 */
	public SpriteModel getSpriteModel(){
		return spriteModel;
	}

	/**
	 * Gets current player relations model
	 * @return player relations model
	 */
	public PlayerRelationModel getPlayerRelationModel() {
		return playerRelationModel;
	}
}
