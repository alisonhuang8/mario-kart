//This entire file is part of my masterpiece.
//DANIEL LI

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
 * @author Daniel
 * A GUI component that allows the user to instantiate any object with the proper annotations.
 * Looks for a method annotated by ConstructorForDeveloper, and and creates 
 * GUI setters to instantiate a class using the constructor's parameters.
 * 
 * I believe this class is well designed because it is flexible enough to be used to instantiate every Component subclass our project ended up using. It makes use of the factory
 * design pattern.
 * @param <T>
 */
public class DefaultObjectSetter<T extends GUISettableObject> extends ObjectSetter<T>{
	private Constructor<? extends T> ctor;
	private Parameter[] parameters;
	private DeveloperData data;
	private List<VariableSetter<?>> variableSetters;
	private VariableSetterFactory setterFactory;
	
	public DefaultObjectSetter(Class<? extends T> myComponent, DeveloperData data) throws UnsupportedTypeException{
		super(myComponent);
		instantiate(myComponent,data);
		
		for(int i=0;i<parameters.length;i++){
			VariableSetter<?> fieldSetter=setterFactory.setterFromParameter(parameters[i]);
			variableSetters.add(fieldSetter);
			this.getChildren().add(fieldSetter);
		}
	}

	public DefaultObjectSetter(T component, DeveloperData data) throws UnsupportedTypeException{
		super((Class<? extends T>) component.getClass());
		instantiate(getObjectType(),data);
		Object[] currentFields=component.getGUIParameters();
		
		for(int i=0;i<parameters.length;i++){
			VariableSetter fieldSetter = setterFactory.setterFromParameter(parameters[i]);
			variableSetters.add(fieldSetter);
			this.getChildren().add(fieldSetter);
			fieldSetter.setField(currentFields[i]);
		}
	}
	
	
	private void instantiate(Class<? extends T> clazz, DeveloperData data){
		this.data=data;
		addDefaultLabel();
		variableSetters=new ArrayList<>();
		Constructor<? extends T>[] ctors=(Constructor<? extends T>[]) clazz.getConstructors();
		for(Constructor<? extends T> constructor:ctors){
			if(constructor.isAnnotationPresent(ConstructorForDeveloper.class)){
				ctor=constructor;
				break;
			}
		}	
		if(ctor==null){
			AlertHandler.showError("Constructor not found");
			return;
		}
		parameters=ctor.getParameters();
		setterFactory=new VariableSetterFactory(data);
	}

	@Override
	public T produceObject() throws Exception{
		List<Object> parameters=new ArrayList<>();
		for(VariableSetter<?> setter:variableSetters){
			if(setter.getValue()==null){
				AlertHandler.showError("Incomplete field");
			}
			parameters.add(setter.getValue());
		} 
		return ctor.newInstance(parameters.toArray());
	}
}
