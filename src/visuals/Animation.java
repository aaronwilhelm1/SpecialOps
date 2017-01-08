package visuals;

import java.awt.Image;

import geometry.Coordinate;

public class Animation {
	
	private int interval;
	private long startTime;
	private Image[] images;
	private double pathAngle;
	private double linSpeed;
	private double angSpeed;
	private int length;
	private Coordinate point;

	public Animation(Image[] desiredImages, int interv){
		startTime = System.currentTimeMillis();
		images = desiredImages;
		interval = interv;
	}
	
	public Animation(Image[] desiredImages, int interv, int animLength, Coordinate start){
		startTime = System.currentTimeMillis();
		images = desiredImages;
		interval = interv;
		point = start;
		length = animLength;
	}
	
	public Animation(Image[] desiredImages, int interv, int animLength, double linearSpeed, double trajectoryAngle, double angularSpeed){
		startTime = System.currentTimeMillis();
		images = desiredImages;
		interval = interv;
		linSpeed = linearSpeed;
		pathAngle = trajectoryAngle;
		angSpeed = angularSpeed;
		length = animLength;
	}
	
	
	
	public Image getCurrentImage(){
		long timeSince = System.currentTimeMillis() - startTime;
		int index = (int) ((timeSince % (interval * images.length)) / interval);
		return images[index];
	}
	
	public Coordinate getPosAdjustment(){
		long timeSince = System.currentTimeMillis() - startTime;
		long time;
		if(timeSince > length){
			time = length;
		} else{
			time = timeSince;
		}
		double x = Math.cos(pathAngle) * linSpeed * time;
		double y = -1 * Math.sin(pathAngle) * linSpeed * time; //mult by neg since y axis is flipped
		return new Coordinate(x, y);
	}
	
	public double getRotAdjustment(){
		long timeSince = System.currentTimeMillis() - startTime;
		long time;
		if(timeSince > length){
			time = length;
		} else{
			time = timeSince;
		}
		return angSpeed * time;
	}
	
	public boolean isValid(){
		long timeSince = System.currentTimeMillis() - startTime;
		long time;
		if(timeSince > length){
			return false;
		} else{
			return true;
		}
	}
	
	//only works for the constructor that defines point
	public Coordinate getPoint(){
		return point;
	}
	
}
