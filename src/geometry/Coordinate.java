package geometry;

import gameobjects.Players.Player;
import utility.Utility;

public class Coordinate {
		
	private double x;
	private double y;
	
	public Coordinate(double xtobe, double ytobe) {
		x = xtobe;
		y = ytobe;
	}
	
	public void setX(double xtobe) {
		x = xtobe;
	}
	
	public void setY(double ytobe) {
		y = ytobe;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public double getDistanceBetween(Coordinate c) {
		return (Math.sqrt(Math.pow(x-c.getX(), 2) + Math.pow(y - c.getY(), 2)));
	}
	
	public double getAngleBetween(Coordinate c) {//gets angle via arctan between two points and determines angle between them from the x-axis with this coordinate as the origin
		double baseAngle = (Math.atan(Math.abs(y - c.getY())/Math.abs(x - c.getX())));
		//System.out.println("Angle: " + baseAngle);
		if (x <= c.getX() && y >= c.getY()) {//In first quadrant
			return baseAngle;
		}
		else if (x >= c.getX() && y >= c.getY()) {//In second quadrant
			return Math.PI - baseAngle;
		}
		else if (x >= c.getX() && y <= c.getY()) {//In third quadrant
			return Math.PI + baseAngle;
		}
		else {//In fourth quadrant
			return (Math.PI * 2) - baseAngle;
		}
	}
	
	//gets the formula for line between (x1,y1) and (x2,y2). Then plugs in (x3,y3) to see answer
	public double getLineFunction(double x1, double y1, double x2, double y2, double x3, double y3) {
		return (y2-y1)* x3 + (x1-x2) * y3 + (x2*y1-x1*y2);
	}
	
	public boolean equals(Coordinate c){
		return ((c.getX()==this.getX()) && (c.getY()==this.getY()));
	}
	
	public boolean isClose(Coordinate c){
		if(Utility.getDistance(this, c) < Player.PLAYER_RADIUS / 2){
			return true;
		} else {
			return false;
		}
	}
}
