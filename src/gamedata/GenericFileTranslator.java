package gamedata;

import java.util.List;

/**
 * @author tahiaemran
 *
 *this interface defines the methods needed to facilitate translation by a specific object in a generic concrete implementation
 *
 */
public interface GenericFileTranslator {
	
	/**
	 * @param translator the Translator object that is to perform the translation
	 * 
	 * this method sets the Translator object within the class that coordinates and directs Translation. 
	 */
	public void setTranslator(Translator<?> translator);
	
	/**
	 * method called by implementing director classes to begin the translation of the file using the specified translator
	 */
	public void translate(); 
	
	/**
	 * @return a list of the translated data objects
	 */
	public List<?> getTranslated(); 
	
	

}
