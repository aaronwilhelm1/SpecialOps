package utility;
import gameobjects.Block;
import geometry.Coordinate;

public class Utility {
	
	public static double getDistance(Coordinate c1, Coordinate c2){
		return Math.sqrt(Math.pow(c1.getX() - c2.getX(), 2) + Math.pow(c1.getY() - c2.getY(), 2));
	}
	
	public static double clamp(double value, double min, double max){
		if(value < min){
			return min;
		}else if(value > max){
			return max;
		} else{
			return value;
		}
	}
	
	public static double getRandomInRange(double middle, double range){
		double change = Math.random() * range;
		if(Math.random() >= .5){
			return middle + change;
		} else {
			return middle - change;
		}
	}
	
	public static double round(double low, double toRound, double high){
		double lowDifference = toRound - low;
		double highDifference = high - toRound;
		if(highDifference < lowDifference){
			return high;
		} else {
			return low;
		}
	}
	
	//
	public static Block[][] RCToXY(Block[][] b){
		Block[][] toReturn = new Block[b[0].length][b.length];
		for(int j = 0; j < b.length; j++){
			for(int k = 0; k < b[0].length; k++){
				toReturn[k][j] = b[j][k];
			}
		}
		return toReturn;
	}
	
	/*
	 * returns true if angles are close to each other within maxDifference
	 */
	public static boolean areAnglesClose(double a1, double a2, double maxDifference) {
		double angleDifference = Math.abs(a1 - a2);

		if (angleDifference < maxDifference || (2 * Math.PI) - angleDifference < maxDifference)
		{
			return true;
		}
		return false;
	}
	
	}
