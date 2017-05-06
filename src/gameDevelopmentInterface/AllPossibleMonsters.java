// This entire file is part of my masterpiece.
// Jake Conroy
package gameDevelopmentInterface;

import java.util.ResourceBundle;
import data.SpriteMakerModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import newengine.sprite.components.Health;
import newengine.sprite.components.Images;
import newengine.sprite.components.PathFollower;
import newengine.sprite.components.Speed;

/**
 * 
 * The purpose of this code is to hold ImageView objects that represent
 * the monsters that the user could put into the spawner. So that it does
 * not run off the screen, I have made it extend ScrollPane. I think that
 * this class is well designed because it uses a resource bundle to get 
 * the strings that appear on the screen, it handles errors in a robust way
 * by showing alert messages to the user that depict what action they took
 * that caused an error, and the "isMonster" method is quite clean and makes
 * use of optionals to make my code look cleaner than if I was checking to
 * see whether each condition of the boolean was equal to null. Finally, I
 * also believe that the methods in the "loadFromFile" method are particularly
 * well-named and would allow any reader to understand the functionality of
 * this part of the program without knowing the inner workings of our program.
 * 
 * @author Jake Conroy (jrc63) Started April 25, 2017
 *
 */

public class AllPossibleMonsters extends ScrollPane {
	private static final String NO_IMAGE = "NO_IMAGE";
	private static final String SELECTED_FILE_NOT_SPAWNER = "SELECTED_FILE_NOT_SPAWNER";
	private static final int IMAGE_SIZE = 100;
	private SpawnerCreation mySpawnerInfo;
	private SpawnerInfoPane mySpawnerInfoPane;
	private VBox monsterImages;
	private ResourceBundle myResources;

	public AllPossibleMonsters(SpawnerCreation spawnerInfo, SpawnerInfoPane spawnerInfoPane, ResourceBundle resources) {
		myResources = resources;
		mySpawnerInfoPane = spawnerInfoPane;
		mySpawnerInfo = spawnerInfo;
		monsterImages = new VBox();
		this.setContent(monsterImages);
		this.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		this.setPrefSize(IMAGE_SIZE, IMAGE_SIZE);
	}
	/**
	 * Load in the data for a monster so that it can be clicked on and added
	 * to the spawner
	 * @param monster a sprite model
	 */
	public void loadFromFile(SpriteMakerModel monster) {
		if (isMonster(monster)) {
			ImageView monsterImage = new ImageView(monster.getComponent(Images.TYPE).get().image().getFXImage());
			monsterImage.setFitWidth(IMAGE_SIZE);
			monsterImage.setFitHeight(IMAGE_SIZE);
			monsterImage.setOnMouseClicked(click -> {
				setCurrentMonster(monster);
				mySpawnerInfo.setCurrentMonsterToSpawn(monster);
			});
			monsterImages.getChildren().add(monsterImage);
		} else {
			Alert alert = new Alert(AlertType.WARNING, myResources.getString(SELECTED_FILE_NOT_SPAWNER));
			alert.show();
		}
	}
	/**
	 * 
	 * @param monster the monster that has been clicked on
	 */
	private void setCurrentMonster(SpriteMakerModel monster) {
		mySpawnerInfo.setCurrentMonsterToSpawn(monster);
		try {
			mySpawnerInfoPane.setImage(monster.getComponent(Images.TYPE).get());
		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.WARNING, myResources.getString(NO_IMAGE));
			alert.show();
		}
		
	}
	/**
	 * 
	 * @param possibleMonster A sprite model that may or may not have the
	 * components necessary to be a monster
	 * @return if the sprite model is a monster or not
	 */
	private boolean isMonster(SpriteMakerModel possibleMonster) {
		return possibleMonster.getComponent(Images.TYPE).isPresent() &&
		 possibleMonster.getComponent(Speed.TYPE).isPresent() &&
		 possibleMonster.getComponent(Health.TYPE).isPresent() &&
		 possibleMonster.getComponent(PathFollower.TYPE).isPresent();
	}
}
