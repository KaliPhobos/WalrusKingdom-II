package dinosws.walruskingdom.visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import dinosws.walruskingdom.engine.GameEngine;

/** Class representing the game window GUI. */
public class GameWindow {
	/** The Java window. */
	private final JFrame window;
	
	/** The drawing canvas. */
	private final JPanel canvas;
	
	/** The rendering timer. */
	private final Timer timer;
	
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
	
	/** The timestamp of the beginning of the previous update. */
	private long lastUpdateTimestamp;
	
	/** The creation timestamp of the last rendered frame. */
	private long lastFrameTimestamp;
	
	/** The duration in milliseconds, that the last frame rendered for. */
	private int lastFrameDuration;
	
	/** The interval between engine updates. */
	private int updateInterval = 40;
	
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
		
		// Initialize the display stats field and the timestamps
		displayStats = false;
		lastUpdateTimestamp = 0;
		lastFrameTimestamp = 0;
		
		// Configure the window
		if (minimumSize != null)
			window.setMinimumSize(minimumSize);
		
		// Update the background color
		window.setBackground(backgroundColor);
		
		// Handle the close operation
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Avoid draw events caused by the OS to keep the framerate stable
		window.setIgnoreRepaint(true);
		
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
				window.pack();
				start();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// Stop the rendering, de-register the events and shut-down the engine
				stop();
				removeEngineEvents();
				engine.onDisable(GameWindow.this);
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
			
			@Override
			public void windowClosed(WindowEvent e) { }
		});
		
		// Set up the draw area
		canvas = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paintComponent(Graphics graphics) {
				// Only render, if the frame is not null
				if (frame == null)
					return;
				
				// Draw the image
				graphics.drawImage(frame, 0, 0, null);
			}
		};
		canvas.setPreferredSize(initialSize);
		canvas.setSize(initialSize);
		window.setContentPane(canvas);
		
		// Finally, set up the timer
		timer = new Timer(updateInterval, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get the current timestamp
				long currentTimestamp = System.currentTimeMillis();
				
				// Update the game logic
				engine.onUpdate(GameWindow.this, (int)(currentTimestamp - lastUpdateTimestamp));
				
				// Update the last update timestamp
				lastUpdateTimestamp = currentTimestamp;
			}
		});
	}
	
	/** Gets the title of the game. */
	public String getGameTitle() {
		return engine.getTitle();
	}
	
	/** Gets the current title string that the window would be displaying now. */
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
	
	/** Gets whether the engine is currently running. */
	public boolean isRunning() {
		return timer.isRunning();
	}
	
	/** Gets the width of the . */
	public int getWindowWidth() {
		return window.getWidth();
	}
	
	/** Gets the height of the window. */
	public int getWindowHeight() {
		return window.getHeight();
	}
	
	/** Gets the width of the buffer. */
	public int getWidth() {
		return frame != null ? frame.getWidth() : 0;
	}
	
	/** Gets the height of the buffer. */
	public int getHeight() {
		return frame != null ? frame.getHeight() : 0;
	}
	
	/** Gets the duration of the previous frame in milliseconds. */
	public int getFrameDuration() {
		return Math.max(lastFrameDuration, 0);
	}
	
	/** Gets the current framerate. */
	public int getFrameRate() {
		return 1000 / Math.max(lastFrameDuration, 1);
	}
	
	/** Returns the current update interval in milliseconds. */
	public int getUpdateInterval()
	{
		return updateInterval;
	}
	
	/** Sets the currently active game engine. */
	public void setEngine(GameEngine engine) {
		// Sanity check the engine
		if (engine == null)
			throw new IllegalArgumentException("The engine may not be null.");
		
		// Handle any existing engine
		if (this.engine != null) {
			// Remove the events
			removeEngineEvents();
			
			// Disable the engine
			engine.onDisable(this);
		}
		
		// Copy the new engine
		this.engine = engine;
		
		// Register the new events
		addEngineEvents();
		
		// Enable the engine
		engine.onEnable(this);
	}
	
	/** Adds all engine events. */
	private void addEngineEvents() {
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
	private void removeEngineEvents() {
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
	
	/** Sets the update interval in milliseconds. */
	public void setUpdateInterval(int interval) {
		// Sanity check the interval
		if (interval < 1)
			return;
		
		// Update the interval
		updateInterval = interval;
		timer.setDelay(interval);
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
		final int width = canvas.getWidth(), height = canvas.getHeight();
		
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
	
	/** Starts the regular engine updates. */
	public void start() {
		// Only do something if currently not running
		if (isRunning())
			return;
		
		// Get the current timestamp
		long currentTimestamp = System.currentTimeMillis();
		
		// Change the timestamps to absolute ones (so that they resume counting)
		lastUpdateTimestamp += currentTimestamp;
		lastFrameTimestamp += currentTimestamp;
		
		// Start the timer
		timer.start();
	}
	
	/** Stops the regular engine updates. */
	public void stop() {
		// Only do something if currently running
		if (!isRunning())
			return;
		
		// Get the current timestamp
		long currentTimestamp = System.currentTimeMillis();
		
		// Change the timestamps to relative ones (so that they stop counting)
		lastUpdateTimestamp -= currentTimestamp;
		lastFrameTimestamp -= currentTimestamp;
		
		// Stop the timer
		timer.stop();
	}
}
