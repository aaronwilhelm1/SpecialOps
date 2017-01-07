package gameobjects;

import geometry.Rectangle;
import visuals.Drawable;
import visuals.ImageLoader;

import java.awt.Image;
import java.awt.geom.Rectangle2D;

import geometry.Coordinate;

public class Block implements Drawable{
	//changing this may cause concatenation for Player.PLAYER_RADIUS
	public static int BLOCK_SIZE;
	public Coordinate topLeft;
	public Coordinate topRight;
	public Coordinate botRight;
	public Coordinate botLeft;
	public Rectangle rectangle;
	public java.awt.geom.Rectangle2D javaRectangle;
	private Image image;
	private int size;
	
	public Block(Coordinate topLeftCorner, int blockSize) {
		topLeft = topLeftCorner;
		topRight = new Coordinate(topLeftCorner.getX() + BLOCK_SIZE * blockSize,topLeftCorner.getY());
		botRight = new Coordinate(topLeftCorner.getX() + BLOCK_SIZE * blockSize,topLeftCorner.getY() + BLOCK_SIZE * blockSize);
		botLeft = new Coordinate(topLeftCorner.getX(),topLeftCorner.getY() + BLOCK_SIZE * blockSize);
		Coordinate[] v = new Coordinate[4];
		v[0] = topLeft;
		v[1] = topRight;
		v[2] = botRight;
		v[3] = botLeft;
		rectangle = new Rectangle(v,BLOCK_SIZE,BLOCK_SIZE);
		size = blockSize;
		javaRectangle = new Rectangle2D.Double((double)(topLeftCorner.getX()),(double)(topLeftCorner.getY()),(double)(BLOCK_SIZE * blockSize),(double)(BLOCK_SIZE * blockSize));
		setImages();
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}
	
	public Coordinate getTopLeft() {
		return topLeft;
	}
	
	public Coordinate getBotRight() {
		return botRight;
	}
	
	public Coordinate getMiddle() {
		return new Coordinate(topLeft.getX() + BLOCK_SIZE / 2 * size,topLeft.getY() + BLOCK_SIZE / 2 * size);
	}
	
	
	public int getSize(){
		return size;
	}

	@Override
	public void setImages() {
		image = ImageLoader.getBoxImage(size);
		
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public Coordinate getCoordinates() {
		return topLeft;
	}

	@Override
	public double getRotation() {
		return 0;
	}

	@Override
	public boolean needsToFlip() {
		return false;
	}
	
	public Rectangle2D getJavaRectangle(){
		return javaRectangle;
	}
}
