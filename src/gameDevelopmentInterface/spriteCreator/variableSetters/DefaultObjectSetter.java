// This entire file is part of my masterpiece.
// DANIEL LI

package gameDevelopmentInterface.spriteCreator.variableSetters;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import data.DeveloperData;
import exception.UnsupportedTypeException;
import gameDevelopmentInterface.developerdata.ObjectSetter;
import gameDevelopmentInterface.spriteCreator.variableSetters.VariableSetter;
import helperAnnotations.ConstructorForDeveloper;
import newengine.sprite.component.GUISettableObject;
import utilities.AlertHandler;

/**
 * 
 * @author Daniel A GUI component that allows the user to instantiate any object
 *         with the proper annotations. Looks for a method annotated by
 *         ConstructorForDeveloper, and and creates GUI setters to instantiate a
 *         class using the constructor's parameters.
 * 
 *         Type casts were necessary in this class due to type erasure, but I 
 *         believe this class is well designed because it is flexible enough
 *         to be used to instantiate every Component subclass our project ended
 *         up using. It makes use of the delegation design pattern by making
 *         variableSetter classes handle the setting of variables, and uses the
 *         factory design pattern.
 *         
 *         
 * @param <T>
 */
public class DefaultObjectSetter<T extends GUISettableObject> extends ObjectSetter<T> {
	private Constructor<? extends T> ctor;
	private Parameter[] parameters;
	private DeveloperData data;
	private List<VariableSetter<?>> variableSetters;
	private VariableSetterFactory setterFactory;

	/**
	 * Loads a GUI object consisting of a list of variable setters corresponding to the input class' constructor.
	 * @param myComponent
	 * @param data
	 * @throws UnsupportedTypeException
	 */
	public DefaultObjectSetter(Class<? extends T> myComponent, DeveloperData data) throws UnsupportedTypeException {
		super(myComponent);
		instantiate(myComponent, data);
	
		if(loadConstructorParameters(myComponent)){
			for (int i = 0; i < parameters.length; i++) {
				VariableSetter<?> fieldSetter = setterFactory.setterFromParameter(parameters[i]);
				variableSetters.add(fieldSetter);
				this.getChildren().add(fieldSetter);
			}	
		}
	}

	/**
	 * Loads a GUI object consisting of a list of variable setters corresponding to the input class' constructor,
	 * but also sets the fields within the variable setters while being created. Thus this allows the details of
	 * a previously saved object to be loaded back to the development interface
	 * @param component
	 * @param data
	 * @throws UnsupportedTypeException
	 */
	@SuppressWarnings("unchecked")
	public DefaultObjectSetter(T component, DeveloperData data) throws UnsupportedTypeException {
		super((Class<? extends T>) component.getClass());
		instantiate(getObjectType(), data);
		if(loadConstructorParameters((Class<? extends T>)component.getClass())){
			Object[] currentFields = component.getGUIParameters();
	
			for (int i = 0; i < parameters.length; i++) {
				VariableSetter fieldSetter = setterFactory.setterFromParameter(parameters[i]);
				variableSetters.add(fieldSetter);
				this.getChildren().add(fieldSetter);
				fieldSetter.setField(currentFields[i]);
			}
		}
	}

	private void instantiate(Class<? extends T> clazz, DeveloperData data) {
		this.data = data;
		addDefaultLabel();
		variableSetters = new ArrayList<>();
		setterFactory = new VariableSetterFactory(data);
	}
	
	/**
	 * Tries to find the parameter array of a constructor with the 
	 * ConstructorForDeveloper annotation. Returns false if unsuccessful.
	 * @param clazz
	 * @return
	 */
	private boolean loadConstructorParameters(Class<? extends T> clazz){
		Constructor<? extends T>[] ctors = (Constructor<? extends T>[]) clazz.getConstructors();
		for (Constructor<? extends T> constructor : ctors) {
			if (constructor.isAnnotationPresent(ConstructorForDeveloper.class)) {
				ctor = constructor;
				break;
			}
		}
		if (ctor == null) {
			AlertHandler.showError("Constructor not found");
			return false;
		}
		parameters = ctor.getParameters();
		return true;
	}

	/**
	 * Produce an instance of the class using the constructor that was found.
	 */
	@Override
	public T produceObject() throws Exception {
		List<Object> parameters = new ArrayList<>();
		for (VariableSetter<?> setter : variableSetters) {
			if (setter.getValue() == null) {
				AlertHandler.showError("Incomplete field");
				return null;
			}
			parameters.add(setter.getValue());
		}
		return ctor.newInstance(parameters.toArray());
	}
}
