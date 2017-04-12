package engine.camera;

import commons.MathUtils;

abstract class Point {

	private double x;
	private double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double x() {
		return x;
	}
	public double y() {
		return y;
	}
	
	public double distFrom(Point other) {
		double xDiff = x - other.x;
		double yDiff = y - other.y;
		return Math.sqrt(xDiff*xDiff + yDiff*yDiff);
	}
	
	public boolean approxEquals(Point other) {
		return (MathUtils.doubleEquals(distFrom(other), 0));
	}
	
}
