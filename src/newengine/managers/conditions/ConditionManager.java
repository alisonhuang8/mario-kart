package newengine.managers.conditions;

import java.util.ArrayList;
import java.util.List;

import bus.EventBus;
import newengine.events.conditions.EndConditionTriggeredEvent;
import newengine.events.conditions.SetEndConditionEvent;
import newengine.model.PlayerRelationModel;
import newengine.model.PlayerStatsModel;
import newengine.model.SpriteModel;
//This entire file is part of my masterpiece.
//YOUR NAME
/**
 * This is a class that manages winning and losing conditions for the game and is between into the framework of the
 * gameloop which calls the condition manager to check each iteration of the game loop. This ConditionManager showcases
 * good design because of its easy API, simply checkConditions() and its encapsulation.  
 * 
 * My masterpiece as whole: I chose the component that has the conditions that then trigger level transitions.
 * It showcases good design because both managers have very clean APIs with easy to extend conditions to use to 
 * work with them. Here is how the pieces interact: The user defines conditions in the front-end which are then set
 * through events on the back-end and then are checked in the ConditionManager. If the conditions are true they will
 * then trigger the LevelManager through events which are easy to use and completely encapsulate the level changing
 * process. The ConditionManager utilizes the Strategy design pattern for the conditions and allows the user to 
 * set any sort of condition they want, making the possibilities endless and therefore making this component non-genre
 * specific, extensible, and encapsulated. The interaction between the major pieces of the components, the ConditionManager
 * and the LevelManager, is done through simple events, making sure that they don't even have references to each other
 * and thus no coupling. By using events it means that if we wanted we could switch one of these major modules out
 * for another implementation and as long as they still listen / respond to the event then the process would still 
 * work. I implemented every piece of this code that I put as part of this masterpiece (did not write Path creating
 * code that I refactored out and stuck in another class). 
 * @author Matthew Tribby
 */
public class ConditionManager {
	private EventBus bus;
	private SpriteModel spriteModel;
	private PlayerStatsModel playerStatsModel;
	private PlayerRelationModel playerRelationModel;
	private List<ICondition> winning;
	private List<ICondition> losing;

	/**
	 * Creates a ConditionManager and gives it the initial models with which the conditions will rely on. The models
	 * are necessary because they are what the conditions need to access to check if they are true
	 * @param bus EventBus to hook up to event-based system
	 * @param spriteModel Sprite Model for the game
	 * @param playerStatsModel Player Stats Model for the game
	 * @param playerRelationModel Model which tells how players relate
	 */
	public ConditionManager(EventBus bus, SpriteModel spriteModel, PlayerStatsModel playerStatsModel, PlayerRelationModel playerRelationModel){
		this.bus = bus;
		this.spriteModel = spriteModel;
		this.playerStatsModel = playerStatsModel;
		this.playerRelationModel = playerRelationModel;
		this.winning = new ArrayList<ICondition>();
		this.losing = new ArrayList<ICondition>();
		initHandlers();
	} 
	
	private void initHandlers() {
		bus.on(SetEndConditionEvent.SETWIN, (e) -> {this.winning.add(setModels(e.getCondition()));});
		bus.on(SetEndConditionEvent.SETLOSE, (e) -> {this.losing.add(setModels(e.getCondition()));});
	}

	/**
	 * Checks both the winning and losing conditions of a game if they have already been set through events.
	 * Utilizes the strategy design pattern.
	 */
	public void checkConditions() {
		checkWinningCondition();
		checkLosingCondition();
	}
	
	private void checkWinningCondition(){
		for(ICondition condition : winning){
			if(condition != null && condition.check()){
				bus.emit(new EndConditionTriggeredEvent(EndConditionTriggeredEvent.WIN));
			}
		}
	}
	
	private void checkLosingCondition(){
		for(ICondition condition : losing){
			if(losing != null && condition.check()){
				bus.emit(new EndConditionTriggeredEvent(EndConditionTriggeredEvent.LOSE));
			}
		}
	}
	
	private ICondition setModels(ICondition condition) {
		condition.setModels(spriteModel, playerStatsModel, playerRelationModel);
		return condition;
	}
	
}
