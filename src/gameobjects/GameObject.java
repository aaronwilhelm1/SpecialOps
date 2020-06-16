package gameobjects;

import gameobjects.Players.Player;
import geometry.Coordinate;
import geometry.Shape;
import visuals.Drawable;

public abstract class GameObject implements Drawable{
	private Coordinate point;
	private Shape shape;
	protected boolean isVisible;
	protected World world;
	
	public GameObject(Coordinate p, Shape s, World w) {
		point = p;
		shape = s;
		isVisible = true;
		world = w;
	}
	
	public void move(){
		
	}
	
	public Coordinate getPoint() {
		return point;
	}
	
	public void setPoint(Coordinate p) {
		point = p;
	}
	
	public Shape getShape() {
		return shape;
	}
	
	public void setShape(Shape s) {
		shape = s;
	}
	
	public void haveInteraction(Player p) {
		
	}
	
	public void setIsVisible(boolean b) {
		isVisible = b;
	}
	
	public boolean getIsVisible() {
		return isVisible;
	}
	}
