package geometry;

public class Circle implements Shape{
	
	private Coordinate center;
	private int radius;
	
	public Circle(Coordinate c, int r){
		center = c;
		radius = r;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public Coordinate getCenter(){
		return center;
	}
	
	
}
