package engine.sprite.movable;

import commons.MathUtils;
import commons.RunningMode;
import data.AttributeData;
import engine.camera.GamePoint;
import engine.skill.Target;
import engine.sprite.Attribute;
import engine.sprite.Sprite;

public class Movable implements Attribute {
	
//	private Sprite sprite;
	private double speed;
	private GamePoint pDest;
	private GamePoint currPos;

//	public Movable(AttributeData data){
//		this.speed = Double.parseDouble(data.getVariable("speed"));	
//	}
//	
	public Movable(double speed){
		this.speed = speed;
	}
	
	public Movable(){
		
	}
//	
//	public Movable(Sprite sprite) {
//		this.sprite = sprite;
//		isMovable = false;
//	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setDest(GamePoint pDest) {
		this.pDest = pDest;
	}
	public void moveTo(Target target) {
		// TODO
	}

	public double update(double dt, GamePoint initialPos) {
		if (pDest == null){
			return dt;
		}
		double xDest = pDest.x();
		double yDest = pDest.y();
		currPos = initialPos;
		double x = initialPos.x();
		double y = initialPos.y();
		if (MathUtils.doubleEquals(x, xDest) && MathUtils.doubleEquals(y, yDest)) {
			return dt;
		}
		double xDiff = xDest - x;
		double yDiff = yDest - y;
		double dist = initialPos.distFrom(pDest);

		if (speed * dt > dist) {
			// arrives at destination at this frame.
			currPos = new GamePoint(xDest, yDest);
			return dist - (speed * dt);
		}

		// not arrived at destination, move by time and speed.
		double vx = 0;
		double vy = 0;
		if (!MathUtils.doubleEquals(x, xDest) && MathUtils.doubleEquals(y, yDest)) {
			vx = xDiff > 0 ? speed : -speed;
		} else if (MathUtils.doubleEquals(x, xDest) && !MathUtils.doubleEquals(y, yDest)) {
			vy = yDiff > 0 ? speed : -speed;
		} else {
			vx = speed / dist * xDiff;
			vy = speed / dist * yDiff;
		}
		currPos = new GamePoint(x + vx * dt, y + vy * dt);
		return 0.0;

	}
	
	public GamePoint getCurrPos(){
		return currPos;
	}

}
