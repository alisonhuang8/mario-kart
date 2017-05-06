package gamedata;
import java.util.List;

/**
 * @author tahiaemran
 * 
 * class used to facilitate translation from one data format to another, where the genetic parameter K represents 
 * the object being translated to 
 * 
 * to use this class: instantiate the class with a type, set its translator according to a concrete translator specific to the type
 * being translated to, and call translate to translate the information and getTranslated() to retrieve the data 
 *
 */
public class TranslationController<K> implements FileTranslator<K>, Translator<K> {
	private Translator<K> translator;
	
	public void setTranslator(Translator<K> T){
		this.translator = T; 
	}

	public void translate(){
		translator.translate();
	}

	@Override
	public List<K> getTranslated() {
		return translator.getTranslated();
	}

}
