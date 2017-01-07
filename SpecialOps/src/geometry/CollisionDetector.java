package geometry;
import utility.Utility;

public class CollisionDetector {

		public static boolean areColliding(Shape s1, Shape s2) {
			if(s1 instanceof Circle) {
				if(s2 instanceof Circle){
					return circleCircle((Circle) s1, (Circle) s2);
				} else if(s2 instanceof Rectangle){
					return circleRectangle((Circle) s1, (Rectangle) s2);
				}	
				
			} else if(s1 instanceof Rectangle){
				if(s2 instanceof Circle){
					return circleRectangle((Circle) s2, (Rectangle) s1);
				} else if(s2 instanceof Rectangle){
					return rectangleRectangle((Rectangle) s1, (Rectangle) s2);//TODO: Need to implement
				}
			}
			return false;
		}
		
		//NOTE: This only works for non-rotated rectangles
		private static boolean circleRectangle(Circle c, Rectangle r){
			//http://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
			// Find the closest point to the circle within the rectangle
			double closestX = Utility.clamp(c.getCenter().getX(), r.getCoords()[0].getX(), r.getCoords()[1].getX());
			double closestY = Utility.clamp(c.getCenter().getY(), r.getCoords()[0].getY(), r.getCoords()[2].getY());

			// Calculate the distance between the circle's center and this closest point
			double distanceX = c.getCenter().getX() - closestX;
			double distanceY = c.getCenter().getY() - closestY;

			// If the distance is less than the circle's radius, an intersection occurs
			double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
			return distanceSquared < (c.getRadius() * c.getRadius());
		}
		
		private static boolean circleCircle(Circle c1, Circle c2){
			double distance = Utility.getDistance(c1.getCenter(), c2.getCenter());
			if(distance < (c1.getRadius() + c2.getRadius())){
				return true;
			} else {
				return false;
			}
		}
		
		private static boolean rectangleRectangle(Rectangle r1, Rectangle r2){
			return false; //TODO: Need to implement
		}
		
		
			
		
	
}
