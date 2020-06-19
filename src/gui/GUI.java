package gui;
import java.awt.Dimension;
import visuals.ImageLoader;
import visuals.Sound;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

import gameobjects.Block;
import gameobjects.LevelIndex;
import gameobjects.MapSelection;
import gameobjects.PlayerSelection;
import gameobjects.World;

public class GUI extends JFrame implements ActionListener{
	// instance variables		
		public static final int TIME_PER_TICK = 55;
		
		public static final boolean DEBUG = true;
		
		public static int WINDOW_WIDTH;
		public static int WINDOW_HEIGHT;
		public static int HUD_WIDTH;
		public static final int HUD_IN_BLOCK_SIZE = 4;
		
		public World world;
		public PlayerSelection playerSelection;
		public MapSelection mapSelection;
		private OurPanel panel;
		
		boolean upPressed;
		boolean downPressed;
		boolean leftPressed;
		boolean rightPressed;
		boolean upReleased;
		
		public enum State {GAME,PLAYER_SELECTION, MAP_SELECTION};
		public State state;
		
		
		private Thread panelPainter;
		private int delay;
		private long previousTime;
		private final int NUM_OF_FRAMES_TO_REMEMBER = 3;
		ArrayList <Long> previousTimes = new ArrayList<Long>(NUM_OF_FRAMES_TO_REMEMBER);
		private int nextIndexToRemove = 0;
		
		/** Creates a new instance of gui_test - sets up GUI */
	    public GUI() {
	        // STEP 1: must call super() first
	        super("SpecialOps");
	        
	      //Start Loading Images
	    	Thread imageLoader = new Thread(){
	    		public void run(){
	    			ImageLoader.loadImages();
	    		}
	    	};
	    	imageLoader.start();
	        
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        double width = screenSize.getWidth();
	        double height = screenSize.getHeight() - 115;
	        if((width / (LevelIndex.LEVEL_WIDTH + HUD_IN_BLOCK_SIZE)) < (height / LevelIndex.LEVEL_HEIGHT)){
	        	Block.BLOCK_SIZE = (int)(width / (LevelIndex.LEVEL_WIDTH + HUD_IN_BLOCK_SIZE));
	        	WINDOW_WIDTH = ((LevelIndex.LEVEL_WIDTH + HUD_IN_BLOCK_SIZE)*(Block.BLOCK_SIZE));
	        	WINDOW_HEIGHT = LevelIndex.LEVEL_HEIGHT * Block.BLOCK_SIZE;
	        } else{
	        	Block.BLOCK_SIZE = (int)((height) / (LevelIndex.LEVEL_HEIGHT));
	        	WINDOW_HEIGHT = (LevelIndex.LEVEL_HEIGHT*(Block.BLOCK_SIZE));
	        	WINDOW_WIDTH = (LevelIndex.LEVEL_WIDTH + HUD_IN_BLOCK_SIZE) * Block.BLOCK_SIZE;
	        }
	        
	        world = new World(this, panel);
	        playerSelection = new PlayerSelection(this, panel, world);
	        mapSelection = new MapSelection(this,panel,world);
	        panel = new OurPanel(this, world, playerSelection,mapSelection);
	        this.getContentPane().add(panel);
	    
	    // STEPS 2-5: not needed here because this example does not
	    // include any GUI "components"...
	        // STEP 2: get content pane and set its layout
	        // STEP 3: construct component(s), such as:     
	        // STEP 4: add all components to the Container;
	        // STEP 5: register any needed event handlers
	        addKeyListener( new KeyHandler());
	        addMouseListener( new MouseClickHandler() );
			
	        // DON'T FORGET TO INCLUDE THIS CODE - otherwise you will not
	        // be able to close your application!!!
	        addWindowListener(new java.awt.event.WindowAdapter() {
	            public void windowClosing(WindowEvent evt) {
	                System.exit(0);
	            }
	        });
	        
	        
	        
	        // STEP 6: set window size and show window
	        
	        setVisible(true);
	        this.setResizable(false);
	        
	        panel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
	        //panel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	        panel.setVisible(true);
	        this.pack();
	        //this.setLocation((int)(width/2-this.getSize().width/2), (int)(height/2-this.getSize().height/2));
	        this.setLocation((int)(width/2-WINDOW_WIDTH/2), (int)(height/2-WINDOW_HEIGHT/2));
	        
	         state = State.MAP_SELECTION;
	        
	        Timer timer = new javax.swing.Timer(TIME_PER_TICK, this);
	        timer.start();		// timer starts here
	        delay = TIME_PER_TICK;
	        previousTime = System.currentTimeMillis();
	        
	        //start the music
	        Sound.menu.loop();
	    }
	    
	    
	     public void paint(Graphics g )
	     {
	    	 panel.paint(g);

	     }

		// this method is called each time the timer goes off     
		public void actionPerformed(ActionEvent evt) 
		{
			long currentTime = System.currentTimeMillis();
			long elapsedTime = currentTime - previousTime;
			if (previousTimes.size() < NUM_OF_FRAMES_TO_REMEMBER) {
				previousTimes.add((elapsedTime - TIME_PER_TICK));
			} else {
				previousTimes.set(nextIndexToRemove, (elapsedTime - TIME_PER_TICK));
				nextIndexToRemove = (nextIndexToRemove + 1) % NUM_OF_FRAMES_TO_REMEMBER;
			}
			
			// decrease our timer by the amount that we are off on average for the last NUM_OF_FRAMES_TO_REMEMBER ticks
			int sum = 0;
			for (Long time : previousTimes) {
		        sum += time;
		    }
			delay -= sum / previousTimes.size();
			if (delay < 0) {
				delay = 0;
			}
			((Timer) evt.getSource()).setInitialDelay(delay);
			((Timer) evt.getSource()).restart();
			previousTime = currentTime;
			
			if(state == State.GAME) {
				world.updateWorld();
			}
	    	
	    	if(panelPainter == null || panelPainter.getState()==Thread.State.TERMINATED) {
	        	//Start painting the screen in a thread
	    		panelPainter = new Thread(){
	        		public void run(){
	        			repaint();
	        		}
	        	};
	        	panelPainter.start();
	    	}
	    	
		}
		
		 /******** PRIVATE INNER CLASSES FOR EVENT HANDLING ***************/
	    /*
	     * - Provide MouseListener event handlers for mouse events
	     * - Provide KeyListener event handlers for key events
	     *
	     */
	    private class KeyHandler implements KeyListener {
	        // the following 3 methods need to be provided in order to
	        // implement the KeyListener interface: 
	        // keyPressed(), keyReleased(), keyTyped();
	        // if you don't need some of these methods, leave method body
	        // empty
	        
	        public void keyPressed ( KeyEvent e )
	        {
	        	if(state == State.GAME) {
	        		world.keyPressed(e);
	        	}

	        	
	        	
	                    // message shows up in text area; note that
	                    // method getKeyCode() gets a 'virtual key code' of the key
	                    // pressed - for a list of virtual key constants, see online
	                    // documentation for class KeyEvent in java.awt.event;
	                    // method getKeyText() converts that code to a String containing
	                    // the name of the key pressed
	            
	            if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
	            	
	            }
	            		
	        }
	        
	        public void keyReleased (KeyEvent e )
	        {
	        	if(state == State.GAME) {
	        		world.keyReleased(e);
	        	}
	        	else if (state == State.PLAYER_SELECTION) {
	        		playerSelection.keyReleased(e);
	        	}
	        	else if (state == State.MAP_SELECTION) {
	        		mapSelection.keyReleased(e);
	        	}
	            // called when key is released after a keyPressed or keyTyped event
	        }
	        

	        
	        public void keyTyped (KeyEvent event )
	        {
	            // only responds to pressing "non-action" keys; (action keys
	            // include arrow key, Home, etc)
	        }
	        
	    }   // end KeyHandler

//	    private class MouseClickHandler implements MouseListener
	            // the following 5 methods are required to implement MouseListener:
	            // mousePressed(), mouseClicked(), mouseReleased(),
	            // mouseEntered(), mouseExited(); to avoid having to provide
	            // all 5, you can extend MouseAdapter instead, and override
	            // only those of the 5 methods that you really need
	    private class MouseClickHandler extends MouseAdapter
	    {
	        public void mouseClicked ( MouseEvent event )
	        {
	            
	        }
	    }   // end MouseClickHandler
	        
	
	     	
	    public static void main(String[] args) {
	        GUI application = new GUI();
	    }
}
