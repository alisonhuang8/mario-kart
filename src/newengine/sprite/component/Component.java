package newengine.sprite.component;

import newengine.sprite.Sprite;

public abstract class Component {

	protected Sprite sprite = new Sprite();
	
	public final void onAdded(Sprite sprite) {
		this.sprite = sprite;  
		afterAdded();
	}
	
	public void afterAdded() {
		
	}
	
	public void onUpdated(double dt) {
		
	}
	
	public void beforeRemoved() {
		
	}

	public abstract ComponentType<? extends Component> getType();
	

	public abstract Component clone();
	
	/**
	 * 应该这么implement这个method：。。。
	 * @return
	 */
	public abstract Object[] getParameters();
	
}
