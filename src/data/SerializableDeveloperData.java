package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import gamecreation.level.LevelData;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 * @author tahiaemran
 *
 *serializable version of developer data for saving authored game files 
 *
 */
public class SerializableDeveloperData {
	private Map<String, String> gameData; 
	private List<SpriteMakerModel> mySprites; 
	private List<LevelData> myLevels; 
	private SpritesForScreenUse screenSprites; 
//	private String gameName; 
//	private String gameIconFile; 
		
	public SerializableDeveloperData(DeveloperData data){
		this.screenSprites = data.getScreenSprites();
		configData(data.getAllData()); 
		configSprites(data.getSprites()); 
		configLevels(data.getLevelData()); 
		
	}


	private void configLevels(ObservableList<LevelData> observableList) {
		myLevels = new ArrayList<LevelData>();
		myLevels = observableList.stream().collect(Collectors.toList());
	}


	private void configSprites(ObservableList<SpriteMakerModel> observableList) {
		mySprites = new ArrayList<SpriteMakerModel>();
		mySprites = observableList.stream().collect(Collectors.toList());
	}

	private void configData(ObservableMap<String, String> observableMap) {
		gameData = new HashMap<String, String>();	
		for (String dataName: observableMap.keySet()){
			gameData.put(dataName,observableMap.get(dataName));
		}

	}
	
	public List<SpriteMakerModel> getSprites(){
		return Collections.unmodifiableList(mySprites); 
	}
	
	public List<LevelData> getLevels(){
		return Collections.unmodifiableList(myLevels);
	}
	
	public Map<String, String> getGameData(){
		return Collections.unmodifiableMap(gameData);
	}
	
	public SpritesForScreenUse getScreenSprites(){
		return this.screenSprites;
	}
	

	
}
