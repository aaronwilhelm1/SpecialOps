package geometry;

public class Rectangle implements Shape{

	private Coordinate[] vertices;
	private double height;
	private double width;
	
	//enter Coordinate[] v in this order:
	//Top Left, Top Right, Bottom Right, Bottom Left
	public Rectangle(Coordinate[] v, double h, double w){
		vertices = v;
		height = h;
		width=w;
	}
	
	//v is top left coordinate
	public Rectangle(Coordinate v, double h, double w){
		vertices = new Coordinate[4];
		vertices[0] = v;
		vertices[1] = new Coordinate(v.getX() + w, v.getY());
		vertices[2] = new Coordinate(v.getX() + w, v.getY() + h);
		vertices[3] = new Coordinate(v.getX(), v.getY() + h);
		height = h;
		width=w;
	}
	
	public Coordinate[] getCoords(){
		return vertices;
	}
	
	public double getHeight(){
		return height;
	}
	
	public double getWidth(){
		return width;
	}
	
	public boolean pointIsInside(Coordinate p){
		if(p.getX() >= vertices[0].getX() && p.getX() <= vertices[1].getX()){
			if(p.getY() >= vertices[0].getY() && p.getY() <= vertices[3].getY()){
				return true;
			}
		}
		return false;
	}
	
}
