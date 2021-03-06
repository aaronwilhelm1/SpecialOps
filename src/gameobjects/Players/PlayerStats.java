package gameobjects.Players;

import gameobjects.Block;
import gameobjects.World;

public class PlayerStats {

	public static final double SPECIAL_RATE = 3000;
	
	public static final double OVERHEAL_RATIO = 1.33333333;
	
	public static final double UNHEAL_RATE = 1000;

	//Scout
	public static final double SCOUT_HEALTH = 125;
	public static final double SCOUT_DAMAGE = 15;//15 original
	public static final double SCOUT_FIRE_RATE = 1000;// 1000 original
	public static final double SCOUT_SPEED = .22 * Block.BLOCK_SIZE;//.22 original
	public static final double SCOUT_RANGE = Block.BLOCK_SIZE * 4;
	public static final double SCOUT_ROTATE_AMOUNT = Math.PI/15;// /15 original
	public static final double SCOUT_ACCURACY = .25;
	public static final double SCOUT_BULLET_SPEED = Block.BLOCK_SIZE * .4;
	public static final double SCOUT_LATERAL_RANGE = Math.PI / 8;
	public static final int SCOUT_BULLET_MOVES = 1;
	public static final double SCOUT_CRITICAL_CHANCE = .05;


	public static final double SCOUT_S_CRITICAL_CHANCE = .50;

	//Heavy
	public static final double HEAVY_HEALTH = 250;
	public static final double HEAVY_DAMAGE = 15;
	public static final double HEAVY_FIRE_RATE = 150;
	public static final double HEAVY_SPEED = .12 * Block.BLOCK_SIZE;
	public static final double HEAVY_RANGE = Block.BLOCK_SIZE * 6;
	public static final double HEAVY_ROTATE_AMOUNT = Math.PI/32;
	public static final double HEAVY_ACCURACY = .55;
	public static final double HEAVY_BULLET_SPEED = Block.BLOCK_SIZE * .6;
	public static final double HEAVY_LATERAL_RANGE = Math.PI / 8;
	public static final int HEAVY_BULLET_MOVES = 2;
	public static final double HEAVY_CRITICAL_CHANCE = .05;

	public static final double HEAVY_S_RANGE = Block.BLOCK_SIZE * 8;
	public static final double HEAVY_S_ACCURACY = .85;




	//Assault
	public static final double ASSAULT_HEALTH = 200;
	public static final double ASSAULT_DAMAGE = 20;
	public static final double ASSAULT_FIRE_RATE = 1000;
	public static final double ASSAULT_SPEED = .17 * Block.BLOCK_SIZE;
	public static final double ASSAULT_RANGE = Block.BLOCK_SIZE * 10;
	public static final double ASSAULT_ROTATE_AMOUNT = Math.PI/25;
	public static final double ASSAULT_ACCURACY = .80;
	public static final double ASSAULT_BULLET_SPEED = Block.BLOCK_SIZE * .5;
	public static final double ASSAULT_LATERAL_RANGE = Math.PI / 10;
	public static final int ASSAULT_BULLET_MOVES = 1;
	public static final double ASSAULT_CRITICAL_CHANCE = .05;


	public static final long ASSAULT_S_FIRE_RATE = 250;
	public static final double ASSAULT_S_DAMAGE = 20;
	public static final double ASSAULT_S_RANGE = Block.BLOCK_SIZE * 6;
	public static final double ASSAULT_S_ACCURACY = .60;
	public static final double ASSAULT_S_BULLET_SPEED = Block.BLOCK_SIZE * .4;
	public static final double ASSAULT_S_LATERAL_RANGE = Math.PI / 8;





	//Sniper
	public static final double SNIPER_HEALTH = 125;
	public static final double SNIPER_DAMAGE = 85;
	public static final double SNIPER_FIRE_RATE = 2000;//2000
	public static final double SNIPER_SPEED = .08 * Block.BLOCK_SIZE; //.08
	public static final double SNIPER_RANGE = Block.BLOCK_SIZE * 15;
	public static final double SNIPER_ROTATE_AMOUNT = Math.PI/44;//44
	public static final double SNIPER_ACCURACY = .99;
	public static final double SNIPER_BULLET_SPEED = Block.BLOCK_SIZE * .75;//.75
	public static final double SNIPER_LATERAL_RANGE = Math.PI / 25;
	public static final int SNIPER_BULLET_MOVES = 4;
	public static final double SNIPER_CRITICAL_CHANCE = .05;


	public static final long SNIPER_S_FIRE_RATE = 200;
	public static final double SNIPER_S_DAMAGE = 10;
	public static final double SNIPER_S_RANGE = Block.BLOCK_SIZE * 5;
	public static final double SNIPER_S_ACCURACY = .70;
	public static final double SNIPER_S_BULLET_SPEED = Block.BLOCK_SIZE * .1;
	public static final double SNIPER_S_LATERAL_RANGE = Math.PI / 8;

	public static final double SNIPER_S_SPEED = .17 * Block.BLOCK_SIZE;
	public static final double SNIPER_S_ROTATE_AMOUNT = Math.PI/24;



	//Shield
	public static final double SHIELD_HEALTH = 175;
	public static final double SHIELD_DAMAGE = 17;
	public static final double SHIELD_FIRE_RATE = 1000;
	public static final double SHIELD_SPEED = .15 * Block.BLOCK_SIZE;
	public static final double SHIELD_RANGE = Block.BLOCK_SIZE * 5;
	public static final double SHIELD_ROTATE_AMOUNT = Math.PI/22;
	public static final double SHIELD_ACCURACY = .55;
	public static final double SHIELD_BULLET_SPEED = Block.BLOCK_SIZE * .4;
	public static final double SHIELD_LATERAL_RANGE = Math.PI / 10;
	public static final int SHIELD_BULLET_MOVES = 1;
	public static final double SHIELD_CRITICAL_CHANCE = .05;

	public static final double SHIELD_S_DAMAGE = 50;
	public static final double SHIELD_S_FIRE_RATE = 1250;
	public static final double SHIELD_S_SPEED = .11 * Block.BLOCK_SIZE;
	public static final double SHIELD_S_RANGE = Block.BLOCK_SIZE * 1;
	public static final double SHIELD_S_ROTATE_AMOUNT = Math.PI/30;
	public static final double SHIELD_S_ACCURACY = 1.0;
	public static final double SHIELD_S_BULLET_SPEED = Block.BLOCK_SIZE * .4;
	public static final double SHIELD_S_LATERAL_RANGE = Math.PI / 4;


	//Explosives
	public static final double EXPLOSIVES_HEALTH = 175;
	public static final double EXPLOSIVES_DAMAGE = 50;
	public static final double EXPLOSIVES_FIRE_RATE = 1200;
	public static final double EXPLOSIVES_SPEED = .17 * Block.BLOCK_SIZE;
	public static final double EXPLOSIVES_RANGE = Block.BLOCK_SIZE * 10;
	public static final double EXPLOSIVES_ROTATE_AMOUNT = Math.PI/25;
	public static final double EXPLOSIVES_ACCURACY = .80;
	public static final double EXPLOSIVES_BULLET_SPEED = Block.BLOCK_SIZE * .23;
	public static final double EXPLOSIVES_LATERAL_RANGE = Math.PI / 10;
	public static final int EXPLOSIVES_BULLET_MOVES = 3;
	public static final double EXPLOSIVES_CRITICAL_CHANCE = .05;

	public static final double MINE_DAMAGE = 60;
	public static final double MINE_NUM_LIMIT = 2;
	public static final long MINE_TIMER = 2000;
	public static final long MINE_FUSE = 550;
	public static final double MINE_EXPLOSION_RADIUS = Block.BLOCK_SIZE * 1.5;
	public static final double MINE_RADIUS = Block.BLOCK_SIZE * .35;


	public static final double GRENADE_EXPLOSION_RADIUS = Block.BLOCK_SIZE * 1.5;
	public static final double EXPLOSIVES_MIN_DAMAGE = 15;


	//Medic
	public static final double MEDIC_HEALTH = 150;
	public static final double MEDIC_DAMAGE = 4;
	public static final double MEDIC_HEAL = 3;
	public static final double MEDIC_FIRE_RATE = 100;
	public static final double MEDIC_SPEED = .16 * Block.BLOCK_SIZE;
	public static final double MEDIC_RANGE = Block.BLOCK_SIZE * 5;
	public static final double MEDIC_ROTATE_AMOUNT = Math.PI/24;
	public static final double MEDIC_ACCURACY = .80;
	public static final double MEDIC_BULLET_SPEED = Block.BLOCK_SIZE * .4;
	public static final double MEDIC_LATERAL_RANGE = Math.PI / 8;
	public static final int MEDIC_BULLET_MOVES = 1;
	public static final double MEDIC_CRITICAL_CHANCE = .05;

	public static final double MEDIC_S_DAMAGE = 25;
	public static final double MEDIC_S_FIRE_RATE = 1000;
	public static final double MEDIC_S_SPEED = .14 * Block.BLOCK_SIZE;
	public static final double MEDIC_S_RANGE = Block.BLOCK_SIZE * 3;
	public static final double MEDIC_S_ROTATE_AMOUNT = Math.PI/24;
	public static final double MEDIC_S_ACCURACY = 1.0;
	public static final double MEDIC_S_BULLET_SPEED = Block.BLOCK_SIZE * .4;
	public static final double MEDIC_S_LATERAL_RANGE = Math.PI;
	public static final double MEDIC_SELF_HEAL_RATE = 1000;
	public static final double MEDIC_SELF_HEAL_AMOUNT = 4;

	//Spy
	public static final double SPY_HEALTH = 125;
	public static final double SPY_DAMAGE = 30;//base damage
	public static final double SPY_FIRE_RATE = 1000;
	public static final double SPY_SPEED = .16 * Block.BLOCK_SIZE;
	public static final double SPY_RANGE = Block.BLOCK_SIZE * 3;
	public static final double SPY_ROTATE_AMOUNT = Math.PI/20;
	public static final double SPY_ACCURACY = .90;
	public static final double SPY_BULLET_SPEED = Block.BLOCK_SIZE * .5;
	public static final double SPY_LATERAL_RANGE = Math.PI / 7;
	public static final int SPY_BULLET_MOVES = 1;
	public static final double SPY_CRITICAL_CHANCE = .05;

	public static final double SPY_STAB_RANGE = Block.BLOCK_SIZE * 1.1;

	//Engineer
	public static final double ENGINEER_HEALTH = 150;
	public static final double ENGINEER_DAMAGE = 15;
	public static final double ENGINEER_FIRE_RATE = 1000;
	public static final double ENGINEER_SPEED = .16 * Block.BLOCK_SIZE;
	public static final double ENGINEER_RANGE = Block.BLOCK_SIZE * 9;
	public static final double ENGINEER_ROTATE_AMOUNT = Math.PI/22;
	public static final double ENGINEER_ACCURACY = .90;
	public static final double ENGINEER_BULLET_SPEED = Block.BLOCK_SIZE * .5;
	public static final double ENGINEER_LATERAL_RANGE = Math.PI / 10;
	public static final int ENGINEER_BULLET_MOVES = 1;
	public static final double ENGINEER_CRITICAL_CHANCE = .05;
	public static final double ENGINEER_SPECIAL_RATE = 10000;



	//Sentry
	public static final double SENTRY_HEALTH = 175;
	public static final double SENTRY_DAMAGE = 15;
	public static final double SENTRY_FIRE_RATE = 150;
	public static final double SENTRY_SPEED = 0;
	public static final double SENTRY_RANGE = Block.BLOCK_SIZE * 6;
	public static final double SENTRY_ROTATE_AMOUNT = Math.PI/32;
	public static final double SENTRY_ACCURACY = .65;
	public static final double SENTRY_BULLET_SPEED = Block.BLOCK_SIZE * .3;
	public static final double SENTRY_LATERAL_RANGE = Math.PI;
	public static final int SENTRY_BULLET_MOVES = 3;
	public static final double SENTRY_CRITICAL_CHANCE = .05;

	public static final double SENTRY_SMALL_LATERAL_RANGE = Math.PI / 8;
	public static final double SENTRY_REPAIR_RANGE = Block.BLOCK_SIZE * 1;
	public static final double SENTRY_REPAIR_AMOUNT = 5;
	public static final double SENTRY_REPAIR_RATE = 1000;


	public PlayerStats() {

	}

	public static void setPlayerStats(String className, Player p) {
		if (className.equals("scout")) {
			p.health = SCOUT_HEALTH;
			p.damage = SCOUT_DAMAGE;
			p.fireRate = SCOUT_FIRE_RATE;
			p.speed = SCOUT_SPEED;
			p.range = SCOUT_RANGE;
			p.rotateAmount = SCOUT_ROTATE_AMOUNT;
			p.accuracy = SCOUT_ACCURACY;
			p.lateralRange = SCOUT_LATERAL_RANGE;
			p.bulletMoves = SCOUT_BULLET_MOVES;
			p.criticalChance = SCOUT_CRITICAL_CHANCE;
		}
		else if (className.equals("heavy")) {
			p.health = HEAVY_HEALTH;
			p.damage = HEAVY_DAMAGE;
			p.fireRate = HEAVY_FIRE_RATE;
			p.speed = HEAVY_SPEED;
			p.range = HEAVY_RANGE;
			p.rotateAmount = HEAVY_ROTATE_AMOUNT;
			p.accuracy = HEAVY_ACCURACY;
			p.lateralRange = HEAVY_LATERAL_RANGE;
			p.bulletMoves = HEAVY_BULLET_MOVES;
			p.criticalChance = HEAVY_CRITICAL_CHANCE;


		}
		else if (className.equals("assault")) {
			p.health = ASSAULT_HEALTH;
			p.damage = ASSAULT_DAMAGE;
			p.fireRate = ASSAULT_FIRE_RATE;
			p.speed = ASSAULT_SPEED;
			p.range = ASSAULT_RANGE;
			p.rotateAmount = ASSAULT_ROTATE_AMOUNT;
			p.accuracy = ASSAULT_ACCURACY;
			p.lateralRange = ASSAULT_LATERAL_RANGE;
			p.bulletMoves = ASSAULT_BULLET_MOVES;
			p.criticalChance = ASSAULT_CRITICAL_CHANCE;


		}
		else if (className.equals("sniper")) {
			p.health = SNIPER_HEALTH;
			p.damage = SNIPER_DAMAGE;
			p.fireRate = SNIPER_FIRE_RATE;
			p.speed = SNIPER_SPEED;
			p.range = SNIPER_RANGE;
			p.rotateAmount = SNIPER_ROTATE_AMOUNT;
			p.accuracy = SNIPER_ACCURACY;
			p.lateralRange = SNIPER_LATERAL_RANGE;
			p.bulletMoves = SNIPER_BULLET_MOVES;
			p.criticalChance = SNIPER_CRITICAL_CHANCE;


		}
		else if (className.equals("shield")) {
			p.health = SHIELD_HEALTH;
			p.damage = SHIELD_DAMAGE;
			p.fireRate = SHIELD_FIRE_RATE;
			p.speed = SHIELD_SPEED;
			p.range = SHIELD_RANGE;
			p.rotateAmount = SHIELD_ROTATE_AMOUNT;
			p.accuracy = SHIELD_ACCURACY;
			p.lateralRange = SHIELD_LATERAL_RANGE;
			p.bulletMoves = SHIELD_BULLET_MOVES;
			p.criticalChance = SHIELD_CRITICAL_CHANCE;
		}
		else if (className.equals("explosives")) {
			p.health = EXPLOSIVES_HEALTH;
			p.damage = EXPLOSIVES_DAMAGE;
			p.fireRate = EXPLOSIVES_FIRE_RATE;
			p.speed = EXPLOSIVES_SPEED;
			p.range = EXPLOSIVES_RANGE;
			p.rotateAmount = EXPLOSIVES_ROTATE_AMOUNT;
			p.accuracy = EXPLOSIVES_ACCURACY;
			p.lateralRange = EXPLOSIVES_LATERAL_RANGE;
			p.bulletMoves = EXPLOSIVES_BULLET_MOVES;
			p.criticalChance = EXPLOSIVES_CRITICAL_CHANCE;
		}
		else if (className.equals("medic")) {
			p.health = MEDIC_HEALTH;
			p.damage = MEDIC_DAMAGE;
			p.fireRate = MEDIC_FIRE_RATE;
			p.speed = MEDIC_SPEED;
			p.range = MEDIC_RANGE;
			p.rotateAmount = MEDIC_ROTATE_AMOUNT;
			p.accuracy = MEDIC_ACCURACY;
			p.lateralRange = MEDIC_LATERAL_RANGE;
			p.bulletMoves = MEDIC_BULLET_MOVES;
			p.criticalChance = MEDIC_CRITICAL_CHANCE;
		}
		else if (className.equals("spy")) {
			p.health = SPY_HEALTH;
			p.damage = SPY_DAMAGE;
			p.fireRate = SPY_FIRE_RATE;
			p.speed = SPY_SPEED;
			p.range = SPY_RANGE;
			p.rotateAmount = SPY_ROTATE_AMOUNT;
			p.accuracy = SPY_ACCURACY;
			p.lateralRange = SPY_LATERAL_RANGE;
			p.bulletMoves = SPY_BULLET_MOVES;
			p.criticalChance = SPY_CRITICAL_CHANCE;
		}
		else if (className.equals("engineer")) {
			p.health = ENGINEER_HEALTH;
			p.damage = ENGINEER_DAMAGE;
			p.fireRate = ENGINEER_FIRE_RATE;
			p.speed = ENGINEER_SPEED;
			p.range = ENGINEER_RANGE;
			p.rotateAmount = ENGINEER_ROTATE_AMOUNT;
			p.accuracy = ENGINEER_ACCURACY;
			p.lateralRange = ENGINEER_LATERAL_RANGE;
			p.bulletMoves = ENGINEER_BULLET_MOVES;
			p.criticalChance = ENGINEER_CRITICAL_CHANCE;
		}
		else if (className.equals("sentry")) {
			p.health = SENTRY_HEALTH;
			p.damage = SENTRY_DAMAGE;
			p.fireRate = SENTRY_FIRE_RATE;
			p.speed = SENTRY_SPEED;
			p.range = SENTRY_RANGE;
			p.rotateAmount = SENTRY_ROTATE_AMOUNT;
			p.accuracy = SENTRY_ACCURACY;
			p.lateralRange = SENTRY_LATERAL_RANGE;
			p.bulletMoves = SENTRY_BULLET_MOVES;
			p.criticalChance = SENTRY_CRITICAL_CHANCE;
		}

	}

	public static double getPlayerDamage(Player player) {
		if (player.inSpecial == true) {
			if (player.getClassName().equals("assault")) {
				return ASSAULT_S_DAMAGE;
			}
			else if (player.getClassName().equals("scout")) {
				return SCOUT_DAMAGE;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_DAMAGE;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_S_DAMAGE;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_S_DAMAGE;
			}	
			else if (player.getClassName().equals("explosives")) {
				return MINE_DAMAGE;
			}	
			else if (player.getClassName().equals("medic")) {
				return MEDIC_S_DAMAGE;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_DAMAGE;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_DAMAGE;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_DAMAGE;
			}
		}
		else {
			if (player.getClassName().equals("scout")) {
				return SCOUT_DAMAGE;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_DAMAGE;
			}
			else if (player.getClassName().equals("assault")) {
				return ASSAULT_DAMAGE;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_DAMAGE;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_DAMAGE;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_DAMAGE;
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_DAMAGE;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_DAMAGE;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_DAMAGE;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_DAMAGE;
			}
		}
		return 0;
	}

	public static double getPlayerRange(Player player) {
		if (player.inSpecial == true) {
			if (player.getClassName().equals("assault")) {
				return ASSAULT_S_RANGE;
			}
			else if (player.getClassName().equals("scout")) {
				return SCOUT_RANGE;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_S_RANGE;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_S_RANGE;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_S_RANGE;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_RANGE;
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_S_RANGE;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_RANGE;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_RANGE;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_RANGE;
			}
		}
		else{
			if (player.getClassName().equals("scout")) {
				return SCOUT_RANGE;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_RANGE;
			}
			else if (player.getClassName().equals("assault")) {
				return ASSAULT_RANGE;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_RANGE;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_RANGE;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_RANGE;
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_RANGE;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_RANGE;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_RANGE;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_RANGE;
			}
		}
		return 0;
	}

	public static double getPlayerAccuracy(Player player) {
		if (player.inSpecial == true) {
			if (player.getClassName().equals("assault")) {
				return ASSAULT_S_ACCURACY;
			}
			else if (player.getClassName().equals("scout")) {
				return SCOUT_ACCURACY;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_S_ACCURACY;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_S_ACCURACY;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_S_ACCURACY;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_ACCURACY;
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_S_ACCURACY;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_ACCURACY;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_ACCURACY;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_ACCURACY;
			}
		}
		else{
			if (player.getClassName().equals("scout")) {
				return SCOUT_ACCURACY;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_ACCURACY;
			}
			else if (player.getClassName().equals("assault")) {
				return ASSAULT_ACCURACY;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_ACCURACY;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_ACCURACY;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_ACCURACY;
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_ACCURACY;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_ACCURACY;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_ACCURACY;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_ACCURACY;
			}
		}
		System.out.println("The accuracy is returning zero!");
		return 0;
	}

	public static double getPlayerNumOfBullets(Player player){
		if(player.inSpecial == true){
			if (player.getClassName().equals("assault")) {
				return 1;
			}
			else if (player.getClassName().equals("scout")) {
				return 6;
			}
			else if (player.getClassName().equals("heavy")) {
				return 1;
			}
			else if (player.getClassName().equals("sniper")) {
				return 1;
			}
			else if (player.getClassName().equals("shield")) {
				return 1;
			}
			else if (player.getClassName().equals("explosives")) {
				return 1;
			}
			else if (player.getClassName().equals("medic")) {
				return 1;
			}
			else if (player.getClassName().equals("spy")) {
				return 1;
			}
			else if (player.getClassName().equals("engineer")) {
				return 2;
			}
			else if (player.getClassName().equals("sentry")) {
				return 1;
			}
		}
		else {
			if (player.getClassName().equals("scout")) {
				return 1;
			}
			else if (player.getClassName().equals("heavy")) {
				return 1;
			}
			else if (player.getClassName().equals("assault")) {
				return 3;
			}
			else if (player.getClassName().equals("sniper")) {
				return 1;
			}
			else if (player.getClassName().equals("shield")) {
				return 4;
			}
			else if (player.getClassName().equals("explosives")) {
				return 1;
			}
			else if (player.getClassName().equals("medic")) {
				return 1;
			}
			else if (player.getClassName().equals("spy")) {
				return 1;
			}
			else if (player.getClassName().equals("engineer")) {
				return 2;
			}
			else if (player.getClassName().equals("sentry")) {
				return 1;
			}
		}
		System.out.println("returning 1 for num of bullets");
		return 1;
	}

	public static double getPlayerSpeed(Player player) {
		if (player.inSpecial == true) {
			if (player.getClassName().equals("assault")) {
				return ASSAULT_SPEED;
			}
			else if (player.getClassName().equals("scout")) {
				return SCOUT_SPEED;
			}
			else if (player.getClassName().equals("heavy")) {
				return 0; //heavies can't move while in special
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_S_SPEED;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_S_SPEED;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_SPEED; //remains the same
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_S_SPEED;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_SPEED;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_SPEED;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_SPEED;
			}
		}
		else {
			if (player.getClassName().equals("scout")) {
				return SCOUT_SPEED;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_SPEED;
			}
			else if (player.getClassName().equals("assault")) {
				return ASSAULT_SPEED;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_SPEED;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_SPEED;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_SPEED;
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_SPEED;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_SPEED;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_SPEED;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_SPEED;
			}
		}
		System.out.println("Can't find a player's speed");
		return ASSAULT_SPEED;
	}
	
	public static double getPlayerRotateAmount(Player player) {
		if (player.inSpecial == true) {
			if (player.getClassName().equals("assault")) {
				return ASSAULT_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("scout")) {
				return SCOUT_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_S_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_S_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_S_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_ROTATE_AMOUNT;
			}
		}
		else {
			if (player.getClassName().equals("scout")) {
				return SCOUT_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("assault")) {
				return ASSAULT_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_ROTATE_AMOUNT;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_ROTATE_AMOUNT;
			}
		}
		System.out.println("Can't find rotation amount for a player");
		return ASSAULT_ROTATE_AMOUNT;
	}
	
	public static double getPlayerBulletSpeed(Player player) {
		if (player.inSpecial == true) {
			if (player.getClassName().equals("assault")) {
				return ASSAULT_S_BULLET_SPEED;
			}
			else if (player.getClassName().equals("scout")) {
				return SCOUT_BULLET_SPEED;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_BULLET_SPEED;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_S_BULLET_SPEED;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_S_BULLET_SPEED;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_BULLET_SPEED;
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_S_BULLET_SPEED;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_BULLET_SPEED;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_BULLET_SPEED;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_BULLET_SPEED;
			}
		}
		else {
			if (player.getClassName().equals("scout")) {
				return SCOUT_BULLET_SPEED;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_BULLET_SPEED;
			}
			else if (player.getClassName().equals("assault")) {
				return ASSAULT_BULLET_SPEED;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_BULLET_SPEED;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_BULLET_SPEED;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_BULLET_SPEED;
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_BULLET_SPEED;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_BULLET_SPEED;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_BULLET_SPEED;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_BULLET_SPEED;
			}
		}
		System.out.println("I'm returning 0");
		return 0;
	}

	public static double getCritChance(Player player) {
		if (player.inSpecial == true) {
			if (player.getClassName().equals("assault")) {
				return ASSAULT_CRITICAL_CHANCE;//the same as non special
			}
			else if (player.getClassName().equals("scout")) {
				return SCOUT_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_CRITICAL_CHANCE;//the same as non special
			}
			else if (player.getClassName().equals("shield")) {
				return 0;//no crit chance
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("medic")) {
				return 0;//no crit chance
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_CRITICAL_CHANCE;
			}
		}
		else {
			if (player.getClassName().equals("scout")) {
				return SCOUT_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("assault")) {
				return ASSAULT_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_CRITICAL_CHANCE;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_CRITICAL_CHANCE;
			}
		}
		System.out.println("Crit chance is returning 0");
		return 0;
	}

	public static double getPlayerFireRate(Player player) {
		if (player.inSpecial == true) {
			if (player.getClassName().equals("assault")) {
				return ASSAULT_S_FIRE_RATE;
			}
			else if (player.getClassName().equals("scout")) {
				return SCOUT_FIRE_RATE;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_FIRE_RATE;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_S_FIRE_RATE;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_S_FIRE_RATE;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_FIRE_RATE;
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_S_FIRE_RATE;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_FIRE_RATE;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_FIRE_RATE;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_FIRE_RATE;
			}
		}
		if (player.getClassName().equals("scout")) {
			return SCOUT_FIRE_RATE;
		}
		else if (player.getClassName().equals("heavy")) {
			return HEAVY_FIRE_RATE;
		}
		else if (player.getClassName().equals("assault")) {
			return ASSAULT_FIRE_RATE;
		}
		else if (player.getClassName().equals("sniper")) {
			return SNIPER_FIRE_RATE;
		}
		else if (player.getClassName().equals("shield")) {
			return SHIELD_FIRE_RATE;
		}
		else if (player.getClassName().equals("explosives")) {
			return EXPLOSIVES_FIRE_RATE;
		}
		else if (player.getClassName().equals("medic")) {
			return MEDIC_FIRE_RATE;
		}
		else if (player.getClassName().equals("spy")) {
			return SPY_FIRE_RATE;
		}
		else if (player.getClassName().equals("engineer")) {
			return ENGINEER_FIRE_RATE;
		}
		else if (player.getClassName().equals("sentry")) {
			return SENTRY_FIRE_RATE;
		}
		return 0;
	}

	public static double getPlayerLateralRange(Player player) {
		if (player.inSpecial == true) {
			if (player.getClassName().equals("assault")) {
				return ASSAULT_S_LATERAL_RANGE;
			}
			else if (player.getClassName().equals("scout")) {
				return SCOUT_LATERAL_RANGE;
			}
			else if (player.getClassName().equals("heavy")) {
				return HEAVY_LATERAL_RANGE;
			}
			else if (player.getClassName().equals("sniper")) {
				return SNIPER_S_LATERAL_RANGE;
			}
			else if (player.getClassName().equals("shield")) {
				return SHIELD_S_LATERAL_RANGE;
			}
			else if (player.getClassName().equals("explosives")) {
				return EXPLOSIVES_LATERAL_RANGE;
			}
			else if (player.getClassName().equals("medic")) {
				return MEDIC_S_LATERAL_RANGE;
			}
			else if (player.getClassName().equals("spy")) {
				return SPY_LATERAL_RANGE;
			}
			else if (player.getClassName().equals("engineer")) {
				return ENGINEER_LATERAL_RANGE;
			}
			else if (player.getClassName().equals("sentry")) {
				return SENTRY_LATERAL_RANGE;
			}
		}
		if (player.getClassName().equals("scout")) {
			return SCOUT_LATERAL_RANGE;
		}
		else if (player.getClassName().equals("heavy")) {
			return HEAVY_LATERAL_RANGE;
		}
		else if (player.getClassName().equals("assault")) {
			return ASSAULT_LATERAL_RANGE;
		}
		else if (player.getClassName().equals("sniper")) {
			return SNIPER_LATERAL_RANGE;
		}
		else if (player.getClassName().equals("shield")) {
			return SHIELD_LATERAL_RANGE;
		}
		else if (player.getClassName().equals("explosives")) {
			return EXPLOSIVES_LATERAL_RANGE;
		}
		else if (player.getClassName().equals("medic")) {
			return MEDIC_LATERAL_RANGE;
		}
		else if (player.getClassName().equals("spy")) {
			return SPY_LATERAL_RANGE;
		}
		else if (player.getClassName().equals("engineer")) {
			return ENGINEER_LATERAL_RANGE;
		}
		else if (player.getClassName().equals("sentry")) {
			return SENTRY_LATERAL_RANGE;
		}
		return 0;
	}

	public static double getPlayerMaxHealth(Player player) {
		if (player.getClassName().equals("assault")) {
			return ASSAULT_HEALTH;
		}
		else if (player.getClassName().equals("scout")) {
			return SCOUT_HEALTH;
		}
		else if (player.getClassName().equals("heavy")) {
			return HEAVY_HEALTH;
		}
		else if (player.getClassName().equals("sniper")) {
			return SNIPER_HEALTH;
		}
		else if (player.getClassName().equals("shield")) {
			return SHIELD_HEALTH;
		}	
		else if (player.getClassName().equals("explosives")) {
			return EXPLOSIVES_HEALTH;
		}
		else if (player.getClassName().equals("medic")) {
			return MEDIC_HEALTH;
		}
		else if (player.getClassName().equals("spy")) {
			return SPY_HEALTH;
		}
		else if (player.getClassName().equals("engineer")) {
			return ENGINEER_HEALTH;
		}
		else if (player.getClassName().equals("sentry")) {
			return SENTRY_HEALTH;
		}
		System.out.println("No max health for the class with the name " + player.getClassName());
		return ASSAULT_HEALTH;
	}


	public static Player createRandomPlayer(int playerNum,int team, World w) {
		double random = Math.random() * 6;
		if (random < 1) {
			return new Scout(playerNum,team,w);
		}
		else if (random < 2) {
			return new Assault(playerNum,team,w);
		}
		else if (random < 3) {
			return new Heavy(playerNum,team,w);
		}
		else if (random < 4) {
			return new Sniper(playerNum,team,w);
		}
		else if (random < 5) {
			return new Shield(playerNum,team,w);
		}
		else if (random < 6) {
			return new Explosives(playerNum,team,w);
		}
		return null;
	}
	
	public static double getSpecialRate(Player player) {
		if (player.getClassName().equals("assault")) {
			return SPECIAL_RATE;
		}
		else if (player.getClassName().equals("scout")) {
			return SPECIAL_RATE;
		}
		else if (player.getClassName().equals("heavy")) {
			return SPECIAL_RATE;
		}
		else if (player.getClassName().equals("sniper")) {
			return SPECIAL_RATE;
		}
		else if (player.getClassName().equals("shield")) {
			return SPECIAL_RATE;
		}	
		else if (player.getClassName().equals("explosives")) {
			return SPECIAL_RATE;
		}
		else if (player.getClassName().equals("medic")) {
			return SPECIAL_RATE;
		}
		else if (player.getClassName().equals("spy")) {
			return SPECIAL_RATE;
		}
		else if (player.getClassName().equals("engineer")) {
			return ENGINEER_SPECIAL_RATE;
		}
		else if (player.getClassName().equals("sentry")) {
			return SPECIAL_RATE;
		}
		System.out.println("No max health for the class with the name " + player.getClassName());
		return ASSAULT_HEALTH;
	}
}
