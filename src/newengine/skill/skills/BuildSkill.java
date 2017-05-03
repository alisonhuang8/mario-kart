package newengine.skill.skills;

import java.util.ArrayList;
import java.util.List;

import bus.EventBus;
import data.SpriteMakerModel;
import gamedata.AuthDataTranslator;
import helperAnnotations.ConstructorForDeveloper;
import helperAnnotations.VariableName;
import newengine.events.SpriteModelEvent;
import newengine.events.skill.CheckCostAndBuildEvent;
import newengine.events.stats.ChangeWealthEvent;
import newengine.model.PlayerStatsModel.WealthType;
import newengine.player.Player;
import newengine.skill.Skill;
import newengine.skill.SkillType;
import newengine.sprite.Sprite;
import newengine.sprite.components.Cost;
import newengine.sprite.components.GameBus;
import newengine.sprite.components.Images;
import newengine.sprite.components.Owner;
import newengine.sprite.components.Position;
import newengine.utils.Target;

public class BuildSkill extends Skill {

	public static final SkillType<BuildSkill> TYPE = new SkillType<>(BuildSkill.class.getName());
	//private Sprite spriteToCreate;
	private SpriteMakerModel mySpriteMakerModel;

	private SkillType<BuildSkill> skillType;
	
	@ConstructorForDeveloper
	public BuildSkill(@VariableName(name = "Sprite") SpriteMakerModel spriteMakerModel) {
		this(spriteMakerModel, TYPE);
	}
	
	public BuildSkill(SpriteMakerModel spriteMakerModel, SkillType<BuildSkill> skillType) {
		this.mySpriteMakerModel = spriteMakerModel;
		this.skillType = skillType;
		if (spriteMakerModel.getComponentByType(Images.TYPE) != null) {
			Images imageComponent = (Images) spriteMakerModel.getComponentByType(Images.TYPE);
			this.icon = imageComponent.image();
		} else {
			System.out.println("sprite maker model has no images component");
		}
	}
	
	public SpriteMakerModel getSpriteMakerModel(){
		return mySpriteMakerModel;
	}
	
	public void setModel(SpriteMakerModel model){
		this.mySpriteMakerModel = model;
	}
	
	@Override                                                           
	public void trigger() {
//<<<<<<< HEAD
//		
//		AuthDataTranslator translator = new AuthDataTranslator(mySpriteModel);
//		Sprite spriteToCreate = translator.getSprite();
//		System.out.println("Build skill triggered");
//		Target target = this.getTarget().get();
//		// can override previous Position component
//		spriteToCreate.addComponent(new Position(target.getLocation(), 0));
//		if (this.getSource().get().getComponent(GameBus.TYPE).isPresent()) {
//			List<Sprite> spritesToCreate = new ArrayList<>();
//			spritesToCreate.add(spriteToCreate.clone());
//			this.getSource().get().getComponent(GameBus.TYPE).get().getGameBus()
//					.emit(new SpriteModelEvent(SpriteModelEvent.ADD, spritesToCreate));
//=======
		AuthDataTranslator translator = new AuthDataTranslator(mySpriteMakerModel);
		Sprite spriteToCreate = translator.getSprite();
		
		Player player = getSource().get().getComponent(Owner.TYPE).get().player();
		if (spriteToCreate.getComponent(Cost.TYPE).isPresent()) {
			System.out.println("cost monney");
			int cost = spriteToCreate.getComponent(Cost.TYPE).get().getCost();
			getSource().get().getComponent(GameBus.TYPE).ifPresent((gameBusComponent) -> {
				gameBusComponent.getGameBus().emit(
						new CheckCostAndBuildEvent(cost, player, () -> {
							buildSprite(spriteToCreate, player, cost);
						}));
			});
		} else {
			System.out.println("CALL BUILD SPRITE FOR FREE");
			buildSprite(spriteToCreate, player, 0); // cost 0
		}

		
//		AuthDataTranslator translator = new AuthDataTranslator(mySpriteModel);
//		Sprite spriteToCreate = translator.getSprite();
//		if (spriteToCreate.getComponent(Cost.TYPE).isPresent()) {
//			int cost = spriteToCreate.getComponent(Cost.TYPE).get().getCost();
//			Player player = getSource().get().getComponent(Owner.TYPE).get().player();
//			getSource().get().getComponent(GameBus.TYPE).ifPresent((gameBusComponent) -> {
//				gameBusComponent.getGameBus().emit(
//						new CheckCostAndBuildEvent(cost, player, () -> {
//							buildSprite(spriteToCreate, player, cost);
//						}));
//			});
//		}
	}
	
	private void buildSprite(SpriteMakerModel mySpriteMakerModel, Player player, int cost) {
		Target target = this.getTarget().get();
		// can override previous Position component
		mySpriteMakerModel.addComponent(new Position(target.getLocation(), 0));
		if (this.getSource().get().getComponent(GameBus.TYPE).isPresent()) {
			List<Sprite> spritesToCreate = new ArrayList<>();
			AuthDataTranslator translator = new AuthDataTranslator(mySpriteMakerModel);
			Sprite spriteToCreate = translator.getSprite();
			spritesToCreate.add(spriteToCreate);
			System.out.println("Size of spritesToCreate: " + spritesToCreate.size());
			EventBus bus = this.getSource().get().getComponent(GameBus.TYPE).get().getGameBus();
			// emit change wealth event besides check cost and build event, 
			// only to utilize the already existing change wealth event handling 
			bus.emit(new ChangeWealthEvent(ChangeWealthEvent.CHANGE, player, WealthType.GOLD, -cost));
			System.out.println("SOURCE: BUILD SKILL");
			bus.emit(new SpriteModelEvent(SpriteModelEvent.ADD, spritesToCreate));
		}
		
	}

	private void buildSprite(Sprite spriteToCreate, Player player, int cost) {
		Target target = this.getTarget().get();
		// can override previous Position component
		spriteToCreate.addComponent(new Position(target.getLocation(), 0));
		if (this.getSource().get().getComponent(GameBus.TYPE).isPresent()) {
			List<Sprite> spritesToCreate = new ArrayList<>();
			spritesToCreate.add(spriteToCreate.clone());
			System.out.println("Size of spritesToCreate: " + spritesToCreate.size());
			EventBus bus = this.getSource().get().getComponent(GameBus.TYPE).get().getGameBus();
			// emit change wealth event besides check cost and build event, 
			// only to utilize the already existing change wealth event handling 
			bus.emit(new ChangeWealthEvent(ChangeWealthEvent.CHANGE, player, WealthType.GOLD, -cost));
			System.out.println("SOURCE: BUILD SKILLL");
			bus.emit(new SpriteModelEvent(SpriteModelEvent.ADD, spritesToCreate));
		}
	}


	@Override
	public SkillType<? extends Skill> getType() {
		return skillType;
	}

	@Override
	public Object[] getGUIParameters() {
		Object[] parameters=new Object[1];
		parameters[0]=mySpriteMakerModel;
		return parameters;
	}

}
