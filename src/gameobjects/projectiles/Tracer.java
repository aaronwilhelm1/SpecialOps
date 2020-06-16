package gameobjects.projectiles;

import java.awt.Image;

import gameobjects.Block;
import gameobjects.World;
import gameobjects.Players.Assault;
import gameobjects.Players.Player;
import geometry.Circle;
import geometry.Coordinate;
import visuals.ImageLoader;

public class Tracer extends Projectile{
	
	Image image;
	boolean isCritical;
	
	public static final double TRACER_RADIUS = Block.BLOCK_SIZE * .1;

	public Tracer(Coordinate p, Circle s, double r, String player, int t, Player self, Coordinate startingPoint, World w) {
		super(p,s, r, player, t, self, startingPoint, w);
		
		if (self instanceof Assault == false) {
			if (Math.random() < self.criticalChance) {
				isCritical = true;
			}
			if (isCritical) {
				super.damage = super.damage * 2;
			}
		}
		else {
			if (Math.random() < self.criticalChance + (self.criticalChance * self.score)) {
				isCritical = true;
			}
			if (isCritical) {
				super.damage = super.damage * 2;
			}
		}
		
		setImages();
	}
	
	
	@Override
	public void setImages() {
		if(isCritical){
			image = ImageLoader.getCritTracerImage();
		} else{

			image = ImageLoader.getTracerImage();
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
	
	public void haveCollision(Block b) {
		
	}//top of bullets: good
	//right of bullets: bad
	//bottom fo bullets: bad
	//left of bullets: good
	
	public void haveCollision(Player p) {
		//System.out.println("the correct method is called");
		if (super.team != p.getTeam()) {
			//System.out.println("Sees if on the same team");
			p.loseHealth(super.damage,this, self,true);
		}
	}
}
