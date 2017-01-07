package visuals;

import java.awt.Image;
import java.util.ArrayList;

import gameobjects.Players.Player;
import gameobjects.Players.PlayerStats;
import geometry.Coordinate;

public class Explosion implements Drawable{
	
	//a collection of all of the previously loaded animations for explosion
	private static ArrayList<Animation> library;
	
	private Coordinate point; //the center of the explosion
	private double rotation;
	private boolean isFlipped;
	private String type;
	private Animation anim;
	private double radius;
	private Player player;
	
	public Explosion(Coordinate p, String t, Player play) {
		point = p;
		player = play;
		rotation = Math.random() * (Math.PI * 2);
		if(Math.random() >= .5){
			isFlipped = true;
		} else {
			isFlipped = false;
		}
		type = t;
		if(type.equals("grenade")){
			radius = (int)((PlayerStats.GRENADE_EXPLOSION_RADIUS + (player.score * PlayerStats.GRENADE_EXPLOSION_RADIUS / 10)));
		} else if(type.equals("mine")){
			radius = (int)((PlayerStats.MINE_EXPLOSION_RADIUS + (player.score * PlayerStats.MINE_EXPLOSION_RADIUS / 10)));
		} else {
			System.out.println("Error loading radius for explosion with type " + type);
			radius = PlayerStats.GRENADE_EXPLOSION_RADIUS;
		}
		setImages();
	}

	@Override
	public void setImages() {
		if(type.equals("grenade")){
			anim = AnimationLoader.getGrenadeExplosion(point, player);
		} else if(type.equals("mine")){
			anim = AnimationLoader.getMineExplosion(point, player);
		} else {
			System.out.println("Error setting images for explosion with type " + type);
			anim = AnimationLoader.getGrenadeExplosion(point, player);
		}
		
	}

	@Override
	public Image getImage() {
		return anim.getCurrentImage();
	}

	@Override
	public Coordinate getCoordinates() {
		//TRANSLATE TO ORIGIN
				double x1 = -radius;
				double y1 = -radius;

				//APPLY ROTATION
				double temp_x1 = x1 * Math.cos(-rotation) - y1 * Math.sin(-rotation);
				double temp_y1 = x1 * Math.sin(-rotation) + y1 * Math.cos(-rotation);

				//TRANSLATE BACK
				return new Coordinate(temp_x1 + point.getX(), temp_y1 + point.getY());
				
	}

	@Override
	public double getRotation() {
		return rotation;
	}

	@Override
	public boolean needsToFlip() {
		return this.isFlipped;
	}
	
	public boolean isValid(){
		return anim.isValid();
	}

}
