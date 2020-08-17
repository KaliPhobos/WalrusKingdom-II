package dinosws.walruskingdom.visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dinosws.walruskingdom.engine.GameEngine;

/** Class representing the game window GUI. */
public class GameWindow {
	/** The Java window. */
	private final JFrame window;
	
	/** Whether the window is in full screen mode. */
	private final boolean fullScreen;
	
	/** The background color of the window. */
	private final Color backgroundColor = new Color(0, 0, 0);
	
	/** The game engine. */
	private GameEngine engine;
	
	/** The currently rendering frame. */
	private BufferedImage frame;
	
	/** The graphics of the currently rendering frame. */
	private Graphics frameGraphics;
	
	/** Whether to display statistics in the title. */
	private boolean displayStats;
	
	/** The creation timestamp of the last rendered frame. */
	private long lastFrameTimestamp;
	
	/** The duration in milliseconds, that the last frame rendered for. */
	private int lastFrameDuration;
	
	/** The constructor. */
	public GameWindow(GameEngine engine, Dimension initialSize, Dimension minimumSize, boolean fullScreen) {
		// Sanity check the sizes
		if (initialSize == null || initialSize.width < 1 || initialSize.height < 1)
			throw new IllegalArgumentException("One of the sizes is invalid.");
		
		// Sanity check the engine
		if (engine == null)
			throw new IllegalArgumentException("The engine may not be null.");
		
		// Copy the full screen flag
		this.fullScreen = fullScreen;
		
		// Create the new window
		window = new JFrame();
		
		// Initialize the display stats field and last frame timestamp
		displayStats = false;
		lastFrameTimestamp = System.currentTimeMillis();
		
		// Configure the window
		if (minimumSize != null)
			window.setMinimumSize(minimumSize);
		
		// Set the initial size
		window.setPreferredSize(initialSize);
		window.setSize(initialSize);
		
		// Update the background color
		window.setBackground(backgroundColor);
		
		// Handle the close operation
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set the engine and register the events
		setEngine(engine);
		
		// Update the title
		updateTitle();
		
		// Handle full-screen mode
		if (fullScreen) {
			// Undecorate, maximize the window and make it always be ontop and not resizeable
			window.setUndecorated(true);
			window.setResizable(false);
			window.setExtendedState(JFrame.MAXIMIZED_BOTH);
			window.setAlwaysOnTop(true);
			
			// Also store the maximized size as the minimum size, if none is specified
			if (minimumSize == null)
				window.setMinimumSize(window.getSize());
		}
		
		// Handle window events
		window.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				// Start the rendering
				start();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// Stop the rendering and de-register the events
				stop();
				removeEngineEvents();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// Shut-down the engine
				engine.onDisable();
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// Stop the rendering
				stop();
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// Resume the rendering
				start();
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// Resume the rendering
				start();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// Stop the rendering
				stop();
			}
		});
		
		// Set up the draw area
		window.setContentPane(new JPanel() {
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics graphics) {
				if (frame != null)
					graphics.drawImage(frame, 0, 0, backgroundColor, null);
			}
		});
	}
	
	/** Returns the title of the game. */
	public String getGameTitle() {
		return engine.getTitle();
	}
	
	/** Returns the current title string that the window would be displaying now. */
	public String getTitle() {
		return window.getTitle();
	}
	
	/** Updates the title and returns the current one. */
	public String updateTitle() {
		// Get the game title
		String gameTitle = getGameTitle();
		
		// If no debug info is shown, just return the game title
		if (!displayStats || gameTitle == "")
		{
			// Update the window if the current title is not the same
			if (window.getTitle() != gameTitle)
				window.setTitle(gameTitle);
			
			// Return the game title
			return gameTitle;
		}
		
		// Otherwise assemble the detailed string
		String newTitle = String.format("%s (%d fps)", gameTitle, getFrameRate());
		
		// Update the window
		window.setTitle(newTitle);
		
		// Return the new string
		return newTitle;
	}
	
	/** Gets the currently active game engine. */
	public GameEngine getEngine() {
		return engine;
	}
	
	/** Gets whether the window is in full screen mode. */
	public boolean isFullScreen() {
		return fullScreen;
	}
	
	/** Gets whether the window is displaying stats. */
	public boolean isDisplayingStats() {
		return displayStats;
	}
	
	/** Gets the width of the window. */
	public int getWidth() {
		return window.getWidth();
	}
	
	/** Gets the height of the window. */
	public int getHeight() {
		return window.getHeight();
	}
	
	/** Returns the duration of the previous frame in milliseconds. */
	public int getFrameDuration() {
		return Math.max(lastFrameDuration, 0);
	}
	
	/** Returns the current framerate. */
	public int getFrameRate() {
		return 1000 / Math.max(lastFrameDuration, 1);
	}
	
	/** Sets the currently active game engine. */
	public void setEngine(GameEngine engine) {
		// Sanity check the engine
		if (engine == null)
			throw new IllegalArgumentException("The engine may not be null.");
		
		// Handle any existing engine
		if (engine != null) {
			// Remove the events
			removeEngineEvents();
			
			// Disable the engine
			engine.onDisable();
		}
		
		// Copy the new engine
		this.engine = engine;
		
		// Register the new events
		addEngineEvents();
		
		// Enable the engine
		engine.onEnable();
	}
	
	/** Adds all engine events. */
	private void addEngineEvents()
	{
		// Add the key events
		if (engine instanceof KeyListener)
			window.addKeyListener((KeyListener)engine);
		// Add the mouse events
		if (engine instanceof MouseListener)
			window.addMouseListener((MouseListener)engine);
		if (engine instanceof MouseMotionListener)
			window.addMouseMotionListener((MouseMotionListener)engine);
		if (engine instanceof MouseWheelListener)
			window.addMouseWheelListener((MouseWheelListener)engine);
	}
	
	/** Removes all engine events. */
	private void removeEngineEvents()
	{
		// Remove the key events
		if (engine instanceof KeyListener)
			window.removeKeyListener((KeyListener)engine);
		// Remove the mouse events
		if (engine instanceof MouseListener)
			window.removeMouseListener((MouseListener)engine);
		if (engine instanceof MouseMotionListener)
			window.removeMouseMotionListener((MouseMotionListener)engine);
		if (engine instanceof MouseWheelListener)
			window.removeMouseWheelListener((MouseWheelListener)engine);
	}
	
	/** Sets whether the window is displaying stats. */
	public void setDisplayingStats(boolean state) {
		displayStats = state;
	}
	
	/** Shows the window. */
	public void show() {
		window.setVisible(true);
	}
	
	/** Hides the window. */
	public void hide() {
		window.setVisible(false);
	}
	
	/** Returns the graphics object for the next frame to be rendered. */
	public Graphics next() {
		// Update the timestamp
		lastFrameTimestamp = System.currentTimeMillis();
		
		// Fetch the dimensions of the window
		final int width = getWidth(), height = getHeight();
		
		// Dispose of any previous frame
		if (frameGraphics != null)
			frameGraphics.dispose();
		
		// Create the new image
		frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		// Create and return the graphics
		return (frameGraphics = frame.getGraphics());
	}
	
	/** Draws the current frame and returns the result. */
	public BufferedImage draw() {
		// Update the graphics
		window.repaint();
		
		// Calculate the frame time
		lastFrameDuration = (int)(System.currentTimeMillis() - lastFrameTimestamp);
		
		// And return the drawn frame
		return frame;
	}
	
	public void start() {
		
	}
	
	public void stop() {
		
	}
}
