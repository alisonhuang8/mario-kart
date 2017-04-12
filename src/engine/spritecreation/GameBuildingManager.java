package engine.spritecreation;

import java.io.File;
import java.util.List;

import bus.EventBus;
import data.AttributeData;
import utilities.XStreamHandler;

/*
 * The spritecreation package should be moved outside of the engine.
 * It is not part of the engine. It uses the engine.
 * 
 * - Keping
 */


/**
 * @author tahiaemran
 *
 *Class that manages taking all data from the game authoring environment 
 * and creating the game from it 
 *
 */
public class GameBuildingManager {
	/*
	 * Wait, are you trying to build the sprite or the game.
	 * If you want to build the game, this class shouldn't be under spritecreation package,
	 * also, if you want to build a game, you create every game component here including 
	 * the bus. Nobody needs to pass you the bus. 
	 */
	private XStreamHandler fileHandler; 
	private List<AttributeData> fileAttributes; 
	private SpriteBuildingManager spriteBuilder;
	private EventBus bus;
	
	public GameBuildingManager(EventBus bus){
		this.fileHandler = new XStreamHandler();
		this.spriteBuilder = new SpriteBuildingManager(bus);
		this.bus = bus;
	}
	
	/*
	 * The three constructors here are very strange. Constructors should only take
	 * what is necessary for the class. It should be at least something like the following: 
	 */	
//	public GameBuildingManager() {
//		this(new EventBus(), new XStreamHandler());
//	}
//	public GameBuildingManager(XStreamHandler xsh) {
//		this(new EventBus(), xsh);
//	}
//	public GameBuildingManager(EventBus bus, XStreamHandler xsh) {
//		this.bus = bus;
//		this.fileHandler = xsh;
//	}
	/*
	 * Don't make this kind of inconsistent constructors however hurry you are in.
	 * (You can make it if you are sure nobody sees your code.)
	 * Always remember that you are writing code not only for yourself, but also for
	 * others. Be responsible for whoever uses your code.
	 */
	/*
	 * Here if XStreamHandler is only a utility, and contains no mutable state, 
	 * then you shouldn't need to pass
	 */
	
	public GameBuildingManager(XStreamHandler xSH) {
		this.fileHandler = xSH; 
	}
	
	public GameBuildingManager(){
		
	}
	
	/*
	 * Usually I would expect a method with this kind of name to return something.
	 */
	
	public void buildFromFile(File file){
		System.out.println(file.getName());
		this.fileAttributes = fileHandler.getScreenModel(file);
	}
	
//	public void printTestFile() {
//		for (AttributeData a : fileAttributes){
//			System.out.println("attribute name is " + a.getName());
//		}
//	}
	
	
	 
	
	
}
