package gamedata;

import java.util.List;

/**
 * @author tahiaemran
 * 
 * interface defining the methods for data translation
 *
 */

public interface Translator<K> {
	/**
	 * method implemented by all translator classes, allowing the client class to translate the data they input
	 * 
	 */
	public void translate(); 
	
	/**
	 * @return a list of the translated objects
	 */
	public List<K> getTranslated(); 
	
	
}
