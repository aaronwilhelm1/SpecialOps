package gameobjects.projectiles;

import geometry.Coordinate;
import gameobjects.Block;
import gameobjects.GameObject;
import gameobjects.World;
import gameobjects.Players.Medic;
import gameobjects.Players.Player;
import gameobjects.Players.PlayerStats;
import gameobjects.Players.Sniper;
import geometry.Circle;
import geometry.Shape;
import utility.Utility;

public abstract class Projectile extends GameObject{
	
	private double speed;
	public double rotation;
	private Circle circle;
	public Coordinate point;
	public double damage;
	private String playerName;
	public int team;
	public Player self;
	public Coordinate start;
	private double bulletRange;
	
	public Projectile(Coordinate p, Circle s, double r, String player, int t, Player sel, Coordinate startingPoint, World w) {
		super(p,s, w);
		circle = s;
		rotation = r;
		speed = PlayerStats.getPlayerBulletSpeed(sel);
		point = p;
		
		if (sel instanceof Medic == true) {//not a sniper
			damage = sel.damage + (sel.damage * sel.score / 7);
		}
		else if (sel instanceof Sniper == true){
			damage = sel.damage + (sel.damage * sel.score / 17);
		}
		else {
			damage = PlayerStats.getPlayerDamage(sel);

		}
		
		playerName = player;
		team = t;
		self = sel;
		start = startingPoint;
		bulletRange = PlayerStats.getPlayerRange(sel) * 1.5;
	}
	
	public Coordinate getNextMove() {
		return new Coordinate(Math.cos(rotation)*speed+point.getX(),-Math.sin(rotation)*speed+point.getY());

	}
	
	public void move() {

			point = new Coordinate(Math.cos(rotation)*speed+point.getX(),-Math.sin(rotation)*speed+point.getY());
			circle = new Circle(point,(int)Tracer.TRACER_RADIUS);
			super.setPoint(point);
			super.setShape(circle);
		
	}
	
	public void haveCollision(Block b) {
		
	}
	
	public void haveCollision(Player p) {
		
	}
	
	public Circle getCircle(){
		return circle;
	}
	
	public Coordinate getStart(){
		return start;
	}
	
	public boolean isOutOfRange(){
		if(Utility.getDistance(start, point) > (bulletRange)){
			return true;
		} else {
			return false;
		}
	}
	
}
