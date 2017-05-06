package gamedata;
import java.io.File;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import data.SerializableDeveloperData;
import data.SpriteMakerModel;
import utilities.XStreamHandler;

/**
 * @author tahiaemran
 * 
 * This class is part of my masterpiece. 
 * 
 * I think that this code is well designed because it has the flexibility to accommodate translation of 
 * any data format to another data format. This is achieved through Composition, where the translator component of this class
 * is defined based on the type of translation required and provides the concrete implementation of the translation. Within our own project
 * the only type of translation we implemented was the translation of SpriteMakerModels to Sprites, which is concretly 
 * performed in the AuthDataTranslator class. 
 * 
 * 
 * This is class used to facilitate translation from one data format to another, where the genetic parameter K represents 
 * the object being translated to 
 * 
 * to use this class: instantiate the class with a type, set its translator according to a concrete translator specific to the type
 * being translated to, and call translate to translate the information and getTranslated() to retrieve the data 
 *
 *
 */
public class TranslationController implements TowerDefenseTranslator, GenericFileTranslator {
	private Translator<?> translator;

	private File fileToTranslate; 
	private XStreamHandler handler; 

	public TranslationController(String filepath){
		fileToTranslate = new File(filepath);	
	}
	
	public TranslationController(File file){
		fileToTranslate = file;
	}
	
	public TranslationController (List<SpriteMakerModel> sprites){
		translator = new AuthDataTranslator(sprites);
	}

	/* (non-Javadoc)
	 * @see gamedata.TowerDefenseTranslator#setTranslatorForAuthFile()
	 */
	public void setTranslatorForAuthFile(){
		XStream xstream = new XStream(new DomDriver());
		SerializableDeveloperData data = (SerializableDeveloperData) xstream.fromXML(fileToTranslate);
		translator = new AuthDataTranslator(data.getSprites());
	}

	/* (non-Javadoc)
	 * @see gamedata.TowerDefenseTranslator#setTranslatorForGameFile()
	 */
	public void setTranslatorForGameFile(){
		translator = new EngineDataTranslator(handler.getSpriteModel(fileToTranslate));
	}


	/* (non-Javadoc)
	 * @see gamedata.GenericFileTranslator#translate()
	 */
	@Override 
	public void translate(){
		translator.translate();
	}

	
	/* (non-Javadoc)
	 * @see gamedata.GenericFileTranslator#getTranslated()
	 */
	@Override
	public List<?> getTranslated() {
		return translator.getTranslated();
	}

	/* (non-Javadoc)
	 * @see gamedata.GenericFileTranslator#setTranslator(gamedata.Translator)
	 */
	@Override
	public void setTranslator(Translator<?> translator) {
		this.translator = translator;
		
	}

}

