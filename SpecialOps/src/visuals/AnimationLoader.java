package visuals;

import java.awt.Image;

import gameobjects.Players.Player;
import gameobjects.Players.PlayerStats;
import gametype.CTF;
import geometry.Coordinate;

public class AnimationLoader {
	
	private static final int GRENADE_EXPLOSION_TIME = 500;
	private static final int MINE_EXPLOSION_TIME = 500;

	private static Animation gsoldierWalking;
	private static Animation bsoldierWalking;

	private static Animation gscoutWalking;
	private static Animation bscoutWalking;

	private static Animation gheavyWalking;
	private static Animation bheavyWalking;
	
	private static Animation gsniperWalking;
	private static Animation bsniperWalking;
	

	private static Animation gshieldgWalking;
	private static Animation bshieldgWalking;
	private static Animation gshieldsWalking;
	private static Animation bshieldsWalking;
	
	private static Animation gexplosivesWalking;
	private static Animation bexplosivesWalking;
	
	private static Animation gmedicWalking;
	private static Animation bmedicWalking;
	
	private static Animation gspyWalking;
	private static Animation bspyWalking;
	
	private static Animation gengineerWalking;
	private static Animation bengineerWalking;

	public static Animation getWalking(String className, int team){
		if(className.equals("assault")){
			return getSoldierWalking(team);
		} else if(className.equals("scout")){
			return getScoutWalking(team);
		} else if(className.equals("heavy")){
			return getHeavyWalking(team);
		} else if(className.equals("sniper")){
			return getSniperWalking(team);
		} else if(className.equals("explosives")){
			return getExplosivesWalking(team);
		} else if(className.equals("medic")){
			return getMedicWalking(team);
		} else if(className.equals("spy")){
			return getSpyWalking(team);
		} else if(className.equals("engineer")){
			return getEngineerWalking(team);
		} else{
			System.out.println("Error loading animation with the class name "+ className);
			return getSoldierWalking(team);
		}
	}
	
	
	public static Animation getSoldierWalking(int team){
		if(team==0){

			if(bsoldierWalking == null){
				setSoldierWalking(team);
			}
			return bsoldierWalking;
		} else {
			if(gsoldierWalking == null){
				setSoldierWalking(team);
			}
			return gsoldierWalking;
		}
	}

	public static Animation getScoutWalking(int team){
		if(team==0){

			if(bscoutWalking == null){
				setScoutWalking(team);
			}
			return bscoutWalking;
		} else {
			if(gscoutWalking == null){
				setScoutWalking(team);
			}
			return gscoutWalking;
		}
	}

	public static Animation getHeavyWalking(int team){
		if(team==0){

			if(bheavyWalking == null){
				setHeavyWalking(team);
			}
			return bheavyWalking;
		} else {
			if(gheavyWalking == null){
				setHeavyWalking(team);
			}
			return gheavyWalking;
		}
	}
	
	public static Animation getSniperWalking(int team){
		if(team==0){

			if(bsniperWalking == null){
				setSniperWalking(team);
			}
			return bsniperWalking;
		} else {
			if(gsniperWalking == null){
				setSniperWalking(team);
			}
			return gsniperWalking;
		}
	}
	
	public static Animation getShieldGWalking(int team){
		if(team==0){

			if(bshieldgWalking == null){
				setShieldgWalking(team);
			}
			return bshieldgWalking;
		} else {
			if(gshieldgWalking == null){
				setShieldgWalking(team);
			}
			return gshieldgWalking;
		}
	}
	
	public static Animation getShieldSWalking(int team){
		if(team==0){

			if(bshieldsWalking == null){
				setShieldsWalking(team);
			}
			return bshieldsWalking;
		} else {
			if(gshieldsWalking == null){
				setShieldsWalking(team);
			}
			return gshieldsWalking;
		}
	}
	
	public static Animation getExplosivesWalking(int team){
		if(team==0){

			if(bexplosivesWalking == null){
				setExplosivesWalking(team);
			}
			return bexplosivesWalking;
		} else {
			if(gexplosivesWalking == null){
				setExplosivesWalking(team);
			}
			return gexplosivesWalking;
		}
	}
	
	public static Animation getMedicWalking(int team){
		if(team==0){

			if(bmedicWalking == null){
				setMedicWalking(team);
			}
			return bmedicWalking;
		} else {
			if(gmedicWalking == null){
				setMedicWalking(team);
			}
			return gmedicWalking;
		}
	}
	
	public static Animation getSpyWalking(int team){
		if(team==0){

			if(bspyWalking == null){
				setSpyWalking(team);
			}
			return bspyWalking;
		} else {
			if(gspyWalking == null){
				setSpyWalking(team);
			}
			return gspyWalking;
		}
	}
	
	public static Animation getEngineerWalking(int team){
		if(team==0){

			if(bengineerWalking == null){
				setEngineerWalking(team);
			}
			return bengineerWalking;
		} else {
			if(gengineerWalking == null){
				setEngineerWalking(team);
			}
			return gengineerWalking;
		}
	}

	public static Animation getDeathMark(int team, Coordinate c){
		return setDeathMark(team, c);//ineffecient design so it restarts everytime
	}
	
	public static Animation getMinePlant(){
		return setMinePlant();//ineffecient design so it flashes from beginning
	}
	
	public static Animation getMineDetonate(){
		return setMineDetonate(); //ineffecient design so it flashes from beginning
	}
	
	public static Animation getMineExplosion(Coordinate point, Player p){
		return setMineExplosion(point, p);//ineffecient design so it explodes from beginning
	}
	
	public static Animation getGrenadeExplosion(Coordinate point, Player p){
		return setGrenadeExplosion(point, p);//ineffecient design so it explodes from beginning
	}

	private static void setSoldierWalking(int team){
		Image[] images = ImageLoader.getPlayerImage("assault", team);
		Image[] temp = new Image[8];
		temp[0] = images[0];
		temp[1] = images[1];
		temp[2] = images[2];
		temp[3] = images[1];
		temp[4] = images[0];
		temp[5] = images[3];
		temp[6] = images[4];
		temp[7] = images[3];
		if(team == 0){
			bsoldierWalking = new Animation(temp, 100);
		} else {
			gsoldierWalking = new Animation(temp, 100);
		}
	}

	private static void setScoutWalking(int team){
		Image[] images = ImageLoader.getPlayerImage("scout", team);
		Image[] temp = new Image[8];
		temp[0] = images[0];
		temp[1] = images[1];
		temp[2] = images[2];
		temp[3] = images[1];
		temp[4] = images[0];
		temp[5] = images[3];
		temp[6] = images[4];
		temp[7] = images[3];
		if(team == 0){
			bscoutWalking = new Animation(temp, 100);
		} else {
			gscoutWalking = new Animation(temp, 100);
		}
	}

	private static void setHeavyWalking(int team){
		Image[] images = ImageLoader.getPlayerImage("heavy", team);
		Image[] temp = new Image[8];
		temp[0] = images[0];
		temp[1] = images[1];
		temp[2] = images[2];
		temp[3] = images[1];
		temp[4] = images[0];
		temp[5] = images[3];
		temp[6] = images[4];
		temp[7] = images[3];
		if(team == 0){
			bheavyWalking = new Animation(temp, 100);
		} else {
			gheavyWalking = new Animation(temp, 100);
		}
	}
	
	private static void setSniperWalking(int team){
		Image[] images = ImageLoader.getPlayerImage("sniper", team);
		Image[] temp = new Image[8];
		temp[0] = images[0];
		temp[1] = images[1];
		temp[2] = images[2];
		temp[3] = images[1];
		temp[4] = images[0];
		temp[5] = images[3];
		temp[6] = images[4];
		temp[7] = images[3];
		if(team == 0){
			bsniperWalking = new Animation(temp, 100);
		} else {
			gsniperWalking = new Animation(temp, 100);
		}
	}
	
	private static void setExplosivesWalking(int team){
		Image[] images = ImageLoader.getPlayerImage("explosives", team);
		Image[] temp = new Image[8];
		temp[0] = images[0];
		temp[1] = images[1];
		temp[2] = images[2];
		temp[3] = images[1];
		temp[4] = images[0];
		temp[5] = images[3];
		temp[6] = images[4];
		temp[7] = images[3];
		if(team == 0){
			bexplosivesWalking = new Animation(temp, 100);
		} else {
			gexplosivesWalking = new Animation(temp, 100);
		}
	}
	
	private static void setMedicWalking(int team){
		Image[] images = ImageLoader.getPlayerImage("medic", team);
		Image[] temp = new Image[8];
		temp[0] = images[0];
		temp[1] = images[1];
		temp[2] = images[2];
		temp[3] = images[1];
		temp[4] = images[0];
		temp[5] = images[3];
		temp[6] = images[4];
		temp[7] = images[3];
		if(team == 0){
			bmedicWalking = new Animation(temp, 100);
		} else {
			gmedicWalking = new Animation(temp, 100);
		}
	}
	
	private static void setSpyWalking(int team){
		Image[] images = ImageLoader.getPlayerImage("spy", team);
		Image[] temp = new Image[8];
		temp[0] = images[0];
		temp[1] = images[1];
		temp[2] = images[2];
		temp[3] = images[1];
		temp[4] = images[0];
		temp[5] = images[3];
		temp[6] = images[4];
		temp[7] = images[3];
		if(team == 0){
			bspyWalking = new Animation(temp, 100);
		} else {
			gspyWalking = new Animation(temp, 100);
		}
	}
	
	private static void setEngineerWalking(int team){
		Image[] images = ImageLoader.getPlayerImage("engineer", team);
		Image[] temp = new Image[8];
		temp[0] = images[0];
		temp[1] = images[1];
		temp[2] = images[2];
		temp[3] = images[1];
		temp[4] = images[0];
		temp[5] = images[3];
		temp[6] = images[4];
		temp[7] = images[3];
		if(team == 0){
			bengineerWalking = new Animation(temp, 100);
		} else {
			gengineerWalking = new Animation(temp, 100);
		}
	}
	
	private static void setShieldgWalking(int team){
		Image[] images = ImageLoader.getPlayerImage("shield", team);
		Image[] temp = new Image[8];
		temp[0] = images[0];
		temp[1] = images[1];
		temp[2] = images[2];
		temp[3] = images[1];
		temp[4] = images[0];
		temp[5] = images[3];
		temp[6] = images[4];
		temp[7] = images[3];
		if(team == 0){
			bshieldgWalking = new Animation(temp, 100);
		} else {
			gshieldgWalking = new Animation(temp, 100);
		}
	}
	
	private static void setShieldsWalking(int team){
		Image[] images = ImageLoader.getPlayerImage("shield", team);
		Image[] temp = new Image[8];
		temp[0] = images[5];
		temp[1] = images[6];
		temp[2] = images[7];
		temp[3] = images[6];
		temp[4] = images[5];
		temp[5] = images[8];
		temp[6] = images[9];
		temp[7] = images[8];
		if(team == 0){
			bshieldsWalking = new Animation(temp, 100);
		} else {
			gshieldsWalking = new Animation(temp, 100);
		}
	}

	private static Animation setDeathMark(int team, Coordinate point){
		if(team == 0){
				Image image = ImageLoader.getbDeathMark();
				Image[] temp = new Image[1];
				temp[0] = image;
				return new Animation(temp, 1, CTF.SPAWN_TIME, point);
			
		} else {
				Image image = ImageLoader.getgDeathMark();
				Image[] temp = new Image[1];
				temp[0] = image;
				return new Animation(temp , 1, CTF.SPAWN_TIME, point);
			
		}
	}
	
	private static Animation setMinePlant(){
		Image[] images = ImageLoader.getMineImages();
		Image[] temp = new Image[2];
		temp[0] = images[0];
		temp[1] = images[1];
		return new Animation(temp, 1000);
	}
	
	private static Animation setMineDetonate(){
		Image[] images = ImageLoader.getMineImages();
		Image[] temp = new Image[2];
		temp[0] = images[1];
		temp[1] = images[2];
		return new Animation(temp, 100);
	}
	
	private static Animation setMineExplosion(Coordinate point, Player p){
		Image[] images = ImageLoader.getMineExplosionImages();
		Image[] toReturn = new Image[images.length];
		for(int j = 0; j < images.length; j++){
			toReturn[j] = images[j].getScaledInstance((int)(2*(PlayerStats.MINE_EXPLOSION_RADIUS + (p.score * PlayerStats.MINE_EXPLOSION_RADIUS / 10))), (int)(2*(PlayerStats.MINE_EXPLOSION_RADIUS + (p.score * PlayerStats.MINE_EXPLOSION_RADIUS / 10))), 10);
		}
		return new Animation(toReturn, AnimationLoader.MINE_EXPLOSION_TIME / 7, AnimationLoader.MINE_EXPLOSION_TIME, point);
	}
	
	private static Animation setGrenadeExplosion(Coordinate point, Player p){
		Image[] images = ImageLoader.getGrenadeExplosionImages();
		Image[] toReturn = new Image[images.length];
		for(int j = 0; j < images.length; j++){
			toReturn[j] = images[j].getScaledInstance((int)(2*(PlayerStats.GRENADE_EXPLOSION_RADIUS + (p.score * PlayerStats.GRENADE_EXPLOSION_RADIUS / 10))), (int)(2*(PlayerStats.GRENADE_EXPLOSION_RADIUS + (p.score * PlayerStats.GRENADE_EXPLOSION_RADIUS / 10))), 10);
		}
		return new Animation(toReturn, AnimationLoader.GRENADE_EXPLOSION_TIME / 7, AnimationLoader.GRENADE_EXPLOSION_TIME, point);
	}

}
