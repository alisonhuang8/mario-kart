package gamedata;

/**
 * @author tahiaemran
 * 
 * interface used to define behavior for classes that translate data from a saved XML file 
 *
 * an implementing class should be a composition of translators, one for each direction in which translation proceeds 
 *
 */
public interface FileTranslator<K>{
	/**
	 * 
	 * method used to set the translator to a specific translator type 
	 * 
	 */
	public void setTranslator(Translator<K> translator);
	

	
}
