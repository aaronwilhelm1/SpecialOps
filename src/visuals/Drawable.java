package visuals;
import java.awt.Image;

import geometry.Coordinate;


/**
 * The interface so that the game objects can be drawn with their graphics
 * 
 * @version 1.0
 * @author Aaron Wilhelm
 */
public interface Drawable {

	
	/**
	 * Creates the array for all of the images for the object.
	 */
	public void setImages();
	
	/**
	 * Retrieves the current image for the object.
	 * 
	 * @return The image for the game object.
	 */
	public Image getImage();
	
	/**
	 * Retrieves the current top left coordinate where the image should be drawn.
	 * 
	 * @return The coordinates of where the image should be drawn.
	 */
	public Coordinate getCoordinates();
	
	/**
	 * Retrieves the rotation of the object.
	 * 
	 * @return The current rotation of the object.
	 */
	public double getRotation();
	
	/**
	 * Used by the GUI to know if the image will need to be flipped before drawn.
	 * 
	 * @return True if the image needs to be flipped.
	 */
	public boolean needsToFlip();
}