package gameobjects;

import java.awt.Image;

import gameobjects.Players.Player;
import gameobjects.Players.Sentry;
import gameobjects.Players.Spy;
import geometry.Coordinate;
import geometry.Rectangle;
import visuals.ImageLoader;

public class Flag extends GameObject{

	private Rectangle rect;
	private boolean isBeingHeld;
	private Image image;
	private int team;
	private Table table;
	public static final double WIDTH = .71698 *Block.BLOCK_SIZE;
	public static final double HEIGHT = .65283 *Block.BLOCK_SIZE;
	
	
	public Flag(Coordinate p, Rectangle r, int teamColor, Table t,World w) {
		super(p,r,w);
		rect = r;
		team = teamColor;
		table = t;
		isBeingHeld = false;
		isVisible = false;
		setImages();
	}
	
	public void haveInteraction(Player p) {
		if (isBeingHeld == false) {
			if (p.getTeam() != team) {//touched by different team, so pick it up
				if (p instanceof Sentry == false) {
					super.setPoint(p.getPoint());
					isBeingHeld = true;
					Coordinate[] b = new Coordinate[4];
					Coordinate tlb = p.getPoint();
					b[0] = tlb;
					b[1] = new Coordinate(tlb.getX() + WIDTH, tlb.getY());
					b[2] = new Coordinate(tlb.getX() + WIDTH, tlb.getY() + HEIGHT);
					b[3] = new Coordinate (tlb.getX(), tlb.getY() + HEIGHT);
					rect = new Rectangle(b, WIDTH, HEIGHT);
					super.setShape(rect);
					super.setIsVisible(false);
					if (p instanceof Spy == true) {
						Spy sp = (Spy) p;
						sp.changeSpecial(false, true);
					}
					
					p.setHasFlag(true);
					p.setFlag(this);
				}
	
			}
			else if (p.getTeam() == team) {//touched by same team
				if (p instanceof Sentry == false) {
					returnFlag();
					table.setHasFlag(true);
				}
			}
		}

	}

	public Table getTable() {
		return table;
	}
	
	public void returnFlag() {
		super.setPoint(table.getPoint());//returns flag to table
		
		isBeingHeld = false;
		Coordinate[] b = new Coordinate[4];
		Coordinate tlb = table.getPoint();
		b[0] = tlb;
		b[1] = new Coordinate(tlb.getX() + WIDTH, tlb.getY());
		b[2] = new Coordinate(tlb.getX() + WIDTH, tlb.getY() + HEIGHT);
		b[3] = new Coordinate (tlb.getX(), tlb.getY() + HEIGHT);
		rect = new Rectangle(b, WIDTH, WIDTH);
		super.setShape(rect);
		isVisible = false;
	}

	@Override
	public void setImages() {
		image = ImageLoader.getBriefcaseImage();
		
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public Coordinate getCoordinates() {
		return rect.getCoords()[0];
	}

	@Override
	public double getRotation() {
		return 0;
	}

	@Override
	public boolean needsToFlip() {
		return false;
	}
	
	public String toString() {
		return "Team: " + team;
	}
	
	public int getTeam() {
		return team;
	}
	
	public void setRect(Rectangle r) {
		rect = r;
		super.setShape(r);
	}
	
	public void setIsBeingHeld(boolean b) {
		isBeingHeld = b;
	}
	
	public boolean getIsBeingHeld() {
		return isBeingHeld;
	}

}
