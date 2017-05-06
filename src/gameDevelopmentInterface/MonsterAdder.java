// This entire file is part of my masterpiece.
// Jake Conroy
package gameDevelopmentInterface;

import java.util.ResourceBundle;
import data.SpriteMakerModel;
import gameauthorgui.inputhelpers.IntegerInputText;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import utilities.XStreamHandler;
/**
 * The purpose of this class is to allow the user to load in monsters
 * that they have saved out to xml files in the sprite creation screen
 * and then enter the total number of monsters that they wish the spawner
 * to spawn. I think it is well designed because it uses a resource bundle
 * to get all of the strings it displays on the screen, it makes use of a
 * custom input text class, IntegerInputText, that only allows the user
 * to input numbers so that errors cannot possibly be thrown for invalid
 * input, it uses a custom xstream handler so that it does not need to worry
 * about the DOM driver boilerplate code, and it surrounds the xml reading
 * with a try catch so that if the user chooses an incorrect file type, a 
 * useful error message will tell them what went wrong.
 * 
 * @author Jake Conroy (jrc63) Started April 25, 2017
 *
 */
public class MonsterAdder extends HBox {
	private static final String SELECTED_FILE_NOT_OF_CORRECT_TYPE = "SELECTED_FILE_NOT_OF_CORRECT_TYPE";
	private static final String CREATE_A_SPAWNER = "CREATE_A_SPAWNER";
	private static final String LOAD_A_MONSTER_FROM_FILE = "LOAD_A_MONSTER_FROM_FILE";
	private static final String HOW_MANY_MONSTERS = "HOW_MANY_MONSTERS";
	private Button loadMonster;
	private AllPossibleMonsters myPossibleMonsters;
	private IntegerInputText numberOfMonsters;
	private ResourceBundle myResources;

	public MonsterAdder(AllPossibleMonsters possibleMonsters, ResourceBundle resources) {
		myResources = resources;
		numberOfMonsters = new IntegerInputText(myResources.getString(HOW_MANY_MONSTERS));
		myPossibleMonsters = possibleMonsters;
		makeButton();		
		this.getChildren().addAll(new Text(myResources.getString(CREATE_A_SPAWNER)), loadMonster, numberOfMonsters);		
	}

	private void makeButton() {
		loadMonster = new Button(myResources.getString(LOAD_A_MONSTER_FROM_FILE));
		loadMonster.setOnAction(click -> {
			try {
				XStreamHandler xstream = new XStreamHandler();
				SpriteMakerModel monster = (SpriteMakerModel) xstream.getSpriteModelFromFile();
				myPossibleMonsters.loadFromFile(monster);
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, myResources.getString(SELECTED_FILE_NOT_OF_CORRECT_TYPE));
				alert.show();
			}
		});
	}
	
	public int getNumMonsters() {
		return Integer.parseInt(numberOfMonsters.getValue());
	}

}
