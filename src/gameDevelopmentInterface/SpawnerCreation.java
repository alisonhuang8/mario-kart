// This entire file is part of my masterpiece.
// Jake Conroy
package gameDevelopmentInterface;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import commons.point.GamePoint;
import data.DeveloperData;
import data.SpriteMakerModel;
import gamecreation.level.SpawnerLevelEditorHolder;
import javafx.scene.layout.BorderPane;
import newengine.player.Player;
import newengine.skill.Skill;
import newengine.skill.SkillType;
import newengine.skill.skills.BuildSkill;
import newengine.sprite.components.GameBus;
import newengine.sprite.components.Owner;
import newengine.sprite.components.PathFollower;
import newengine.sprite.components.Position;
import newengine.sprite.components.SkillSet;
import newengine.sprite.components.Spawner;
/**
 * I included this class as part of my masterpiece because I think it shows how
 * I have learned to split my methods up into small functional pieces, how I 
 * have learned to extend classes for ease of use and inheritance of useful features,
 * and how I have learned to use resource files in the front end for setting the
 * areas in my program that have text. This is the top level class for creating a
 * spawner from our GUI, so it instantiates other components I have also included
 * in my masterpiece. Together they provide the user with a robust and easy to 
 * use interface to load up a spawner object with monsters that can follow a path.
 * 
 * @author Jake Conroy (jrc63) Started April 25, 2017
 *
 */
public class SpawnerCreation extends BorderPane {
	private static final int DEFAULT_HEADING = 0;
	private static final int LEVEL_SPAWNER_HEIGHT = 600;
	private static final String SPAWNER = "SPAWNER";
	private SpriteMakerModel monsterToSpawn;
	private DeveloperData myModel;
	private SpawnerInfoPane mySpawnerInfo;
	private AllPossibleMonsters myPossibleMonsters;
	private MonsterAdder myMonsterAdder;
	private static final String RESOURCE_FILE_PATH = "resources" + File.separator + "gameAuthoringEnvironment";
	private ResourceBundle myResources;
	
	public SpawnerCreation(DeveloperData model) {
		this.myModel = model;
		myResources = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
		instantiate();
	}
	/**
	 * 
	 * @param monster The monster that the spawner is supposed to spawn
	 */
	public void setCurrentMonsterToSpawn(SpriteMakerModel monster) {
		monsterToSpawn = monster;
	}
	/**
	 * 
	 * @param spawnBetweenTime how many seconds to wait in between
	 * spawning monsters 
	 * @return the info needed to xstream into a spawner object that
	 * the back end can use
	 */
	public SpriteMakerModel getSpawner(double spawnBetweenTime) {
		SpriteMakerModel spawnerData = new SpriteMakerModel();
		Map<SkillType<? extends Skill>, Skill> spawnerSkills = new HashMap<>();
		spawnerSkills.put(BuildSkill.TYPE, new BuildSkill(monsterToSpawn));
		spawnerData.addComponent(new GameBus());
		spawnerData.addComponent(new SkillSet(spawnerSkills));
		spawnerData.addComponent(new Owner(new Player(myResources.getString(SPAWNER))));
		spawnerData.addComponent(new Position(getStartingPosition(), DEFAULT_HEADING));
		spawnerData.addComponent(new Spawner(myMonsterAdder.getNumMonsters(), spawnBetweenTime, monsterToSpawn));
		return spawnerData;
	}
	/**
	 * 
	 * @return the first coordinate of the path the monster is 
	 * supposed to follow
	 */
	private GamePoint getStartingPosition() {
		return monsterToSpawn.getComponent(PathFollower.TYPE).get().getStartingPosition();
	}
	/**
	 * Set up the class with new instances of SpawnerInfoPane,
	 * AllPossibleMonster, and MonsterAdder and then put them
	 * into the BorderPane in the correct places
	 */
	private void instantiate() {
		mySpawnerInfo = new SpawnerInfoPane();
		myPossibleMonsters = new AllPossibleMonsters(this, mySpawnerInfo, myResources);
		myMonsterAdder = new MonsterAdder(myPossibleMonsters, myResources);
		this.setLeft(myPossibleMonsters);
		this.setCenter(mySpawnerInfo);
		this.setTop(myMonsterAdder);
		this.setRight(new SpawnerLevelEditorHolder(myModel.getLevelData(), LEVEL_SPAWNER_HEIGHT, this));
	}
	
}
