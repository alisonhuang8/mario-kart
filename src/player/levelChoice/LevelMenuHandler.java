package player.levelChoice;

import javafx.scene.control.MenuItem;

/**
 * @author Zhiyong
 * Create the level handler of a game
 * This is used in {@code player.levelChoice.LevelMenu}.
 *
 */
public class LevelMenuHandler implements MenuItemHandler{
	private int levelID;
	private MenuItem level;
	public LevelMenuHandler(String source, int levelID){
		level = new MenuItem(source);
		//level.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
		level.setOnAction(e -> handle());
		this.levelID = levelID;
	}

	@Override
	public void handle() {
		
	}

	@Override
	public MenuItem getMenuItem() {

		return level;
	}
	public int getLevelID(){
		return levelID;
	}
	
}