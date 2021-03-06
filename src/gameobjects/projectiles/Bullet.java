package gameobjects.projectiles;

import java.awt.Image;

import gameobjects.Block;
import geometry.Coordinate;
import visuals.Animation;
import visuals.Drawable;
import visuals.ImageLoader;

public class Bullet implements Drawable{
	
	public static double MAX_LINSPEED = Block.BLOCK_SIZE * .002;
	public static double MIN_LINSPEED = Block.BLOCK_SIZE * .001;
	public static double MAX_ANGSPEED = Math.PI;
	
	private double x;
	private double y;
	private double rotation;
	private Image image;
	private Animation anim;

	public Bullet(double initX, double initY, double initRot, double pathAng, double linearSpeed, double angularSpeed){
		x = initX;
		y = initY;
		rotation = initRot;
		setImages();
		Image[] temp = new Image[1];
		temp[0] = image;
		anim = new Animation(temp, 100, 500, linearSpeed, pathAng, angularSpeed);
			
		
	}

	@Override
	public void setImages() {
		image = ImageLoader.getBulletImage();
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public Coordinate getCoordinates() {
		return new Coordinate(x + anim.getPosAdjustment().getX(), y + anim.getPosAdjustment().getY());
	}

	@Override
	public double getRotation() {
		return rotation + anim.getRotAdjustment();
	}

	@Override
	public boolean needsToFlip() {
		return false;
	}
}
