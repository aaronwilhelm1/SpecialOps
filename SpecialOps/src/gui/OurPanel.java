package gui;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import gameobjects.MapSelection;
import gameobjects.PlayerSelection;
import gameobjects.World;
import gui.GUI.State;

public class OurPanel extends JPanel{
	
	private World world;
	private PlayerSelection playerSelection;
	private MapSelection mapSelection;
	private GUI gui;
	
	public OurPanel(GUI g, World w , PlayerSelection ps, MapSelection mp){
		gui = g;
		world = w;
		playerSelection = ps;
		mapSelection = mp;
	}
	
	
	public void paint(Graphics g){
		//super.paintComponent(g);
    	Image offImage = createImage(this.getWidth(), this.getHeight());

    // Creates an off-screen drawable image to be used for

    // double buffering; XSIZE, YSIZE are each of type ‘int’ 

    Graphics buffer = offImage.getGraphics();

    // Creates a graphics context for drawing to an 

    // off-screen image
    if (gui.state == State.GAME) {
    	world.paintOffScreen(buffer);
    }
    else if (gui.state == State.PLAYER_SELECTION) {
    	playerSelection.paintOffScreen(buffer);
    }
    else if (gui.state == State.MAP_SELECTION) {
    	mapSelection.paintOffScreen(buffer);
    }

    g.drawImage(offImage, 3, 37, null); 
    
	}
}
