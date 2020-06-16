package gameobjects.projectiles;

import java.awt.Image;

import gameobjects.Block;
import gameobjects.World;
import gameobjects.Players.Player;
import gameobjects.Players.PlayerStats;
import geometry.Circle;
import geometry.Coordinate;
import visuals.ImageLoader;

public class Syringe extends Projectile{
	boolean isCritical;
	private Image image;
	private double healAmount;
	public static final double SYRINGE_RADIUS = Block.BLOCK_SIZE * .1;
	
	public Syringe(Coordinate p, Circle s, double r, String player, int t, Player sel, Coordinate startingPoint,
			World w) {
		super(p, s, r, player, t, sel, startingPoint, w);
		
		if (Math.random() < self.criticalChance) {
			isCritical = true;
		}
		if (isCritical) {
			super.damage = super.damage * 2;
		}
		
		healAmount = PlayerStats.MEDIC_HEAL;
		
		setImages();
	}
	
	public void haveCollision(Block b) {

	}
	
	public void haveCollision(Player p) {
		if (p.getTeam() == super.team) {
			p.loseHealth(healAmount * -1, this,self,true);
		}
		else {
			p.loseHealth(damage, this,self,true);
		}
	}
	

	@Override
	public void setImages() {
		if(isCritical){
			image = ImageLoader.getCritTracerImage();
		} else{

			image = ImageLoader.getSyringeImage();
		}
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public Coordinate getCoordinates() {
		/*//TRANSLATE TO ORIGIN
				double x1 = -Player.PLAYER_RADIUS;
				double y1 = -Player.PLAYER_RADIUS;

				//APPLY ROTATION
				double temp_x1 = x1 * Math.cos(-rotation) - y1 * Math.sin(-rotation);
				double temp_y1 = x1 * Math.sin(-rotation) + y1 * Math.cos(-rotation);

				//TRANSLATE BACK
				return new Coordinate(temp_x1 + getCircle().getCenter().getX(), temp_y1 + getCircle().getCenter().getY());*/
		Circle circle = getCircle();
		return new Coordinate(circle.getCenter().getX() - circle.getRadius(), circle.getCenter().getY() - circle.getRadius());
	}

	@Override
	public double getRotation() {
		return 0;
	}

	@Override
	public boolean needsToFlip() {
		return false;
	}
}
