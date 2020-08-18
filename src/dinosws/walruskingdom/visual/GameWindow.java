package dinosws.walruskingdom.visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import dinosws.walruskingdom.engine.GameScreen;

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
	
	/** The game screen stack. */
	private final Stack<GameScreen> screenStack;
	
	/** The game frame stack. */
	private final Stack<BufferedImage> frameStack;
	
	/** The graphics of the currently rendering frame. */
	private Graphics2D frameGraphics;
	
	/** Whether to display statistics in the title. */
	private boolean displayStats;
	
	/** The timestamp of the beginning of the previous update. */
	private long lastUpdateTimestamp;
	
	/** The creation timestamp of the last rendered frame. */
	private long lastFrameTimestamp;
	
	/** The duration in milliseconds, that the last frame rendered for. */
	private int lastFrameDuration;
	
	/** The interval between screen updates. */
	private int updateInterval = 40;
	
	/** The constructor. */
	public GameWindow(GameScreen screen, Dimension initialSize, Dimension minimumSize, boolean fullScreen) {
		// Sanity check the sizes
		if (initialSize == null || initialSize.width < 1 || initialSize.height < 1)
			throw new IllegalArgumentException("One of the sizes is invalid.");
		
		// Sanity check the screen
		if (screen == null)
			throw new IllegalArgumentException("The screen may not be null.");
		
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
		
		// Set up the draw area
		canvas = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paintComponent(Graphics graphics) {
				// Only render, if the frame is not null
				if (getFrame() == null)
					return;
				
				// Draw the image
				graphics.drawImage(getFrame(), 0, 0, null);
			}
		};
		canvas.setPreferredSize(initialSize);
		canvas.setSize(initialSize);
		window.setContentPane(canvas);
		
		// Initialize the screen and frame stacks and push the new screen and register its events
		screenStack = new Stack<GameScreen>();
		frameStack = new Stack<BufferedImage>();
		pushScreen(screen);
		
		// Update the title
		updateTitle();
		
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
				// Stop the rendering, de-register the events and shut-down the screens
				stop();
				while (!screenStack.isEmpty()) {
					// Get the next screen
					GameScreen screen = screenStack.pop();
		
					// Remove the events
					removeScreenEvents(screen);
		
					// Disable the screen
					screen.onDisable(GameWindow.this);
				}
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
		
		// Finally, set up the timer
		timer = new Timer(updateInterval, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get the current timestamp
				long currentTimestamp = System.currentTimeMillis();
				
				// Update the game logic
				getScreen().onUpdate(GameWindow.this, (int)(currentTimestamp - lastUpdateTimestamp));
				
				// Update the last update timestamp
				lastUpdateTimestamp = currentTimestamp;
			}
		});
	}
	
	/** Gets the title of the game screen. */
	public String getGameTitle() {
		return getScreen().getTitle();
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
	
	/** Gets the currently active game screen. */
	public GameScreen getScreen() {
		return screenStack.empty() ? null : screenStack.peek();
	}
	
	/** Gets the currently active frame. */
	private BufferedImage getFrame() {
		return frameStack.empty() ? null : frameStack.peek();
	}
	
	/** Gets whether the window is in full screen mode. */
	public boolean isFullScreen() {
		return fullScreen;
	}
	
	/** Gets whether the window is displaying stats. */
	public boolean isDisplayingStats() {
		return displayStats;
	}
	
	/** Gets whether the screen is currently running. */
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
		return getFrame() != null ? getFrame().getWidth() : 0;
	}
	
	/** Gets the height of the buffer. */
	public int getHeight() {
		return getFrame() != null ? getFrame().getHeight() : 0;
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
	
	/** Sets the currently active game screen by pushing it on top of the screen stack. */
	public void pushScreen(GameScreen screen) {
		// Sanity check the screen
		if (screen == null)
			throw new IllegalArgumentException("The screen may not be null.");
		
		// Handle any existing screen
		GameScreen previousScreen = getScreen();
		if (previousScreen != null) {
			// Remove the events
			removeScreenEvents(previousScreen);
			
			// Disable the screen
			previousScreen.onDisable(this);
		}
		
		// Push the new screen
		screenStack.push(screen);
		
		// Also create and push a new frame
		frameStack.push(new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB));
		
		// Update the graphics
		if (frameGraphics != null)
			frameGraphics.dispose();
		frameGraphics = (Graphics2D)getFrame().getGraphics();
		
		// Enable the screen
		screen.onEnable(this);
		
		// And register its events, if this is still the same screen
		if (getScreen() == screen)
			addScreenEvents(screen);
	}
	
	/** Pops the current screen from the screen stack. */
	public void popScreen() {
		// Make sure there are at least two screens on the stack
		if (screenStack.size() < 2)
			return;
		
		// Get the current screen
		GameScreen screen = getScreen();
		
		// Remove the events
		removeScreenEvents(screen);
		
		// Disable the screen
		screen.onDisable(this);
		
		// Drop the current screen and frame
		screenStack.pop();
		frameStack.pop();
		
		// Update the graphics
		frameGraphics.dispose();
		frameGraphics = (Graphics2D)getFrame().getGraphics();
		
		// Fetch the next screen
		screen = getScreen();
		
		// Enable it
		screen.onEnable(this);
		
		// And register its events, if this is still the same screen
		if (getScreen() == screen)
			addScreenEvents(screen);
	}
	
	/** Adds all screen events. */
	private void addScreenEvents(GameScreen screen) {
		// Add the key events
		if (screen instanceof KeyListener)
			window.addKeyListener((KeyListener)screen);
		// Add the mouse events
		if (screen instanceof MouseListener)
			window.addMouseListener((MouseListener)screen);
		if (screen instanceof MouseMotionListener)
			window.addMouseMotionListener((MouseMotionListener)screen);
		if (screen instanceof MouseWheelListener)
			window.addMouseWheelListener((MouseWheelListener)screen);
	}
	
	/** Removes all screen events. */
	private void removeScreenEvents(GameScreen screen) {
		// Remove the key events
		if (screen instanceof KeyListener)
			window.removeKeyListener((KeyListener)screen);
		// Remove the mouse events
		if (screen instanceof MouseListener)
			window.removeMouseListener((MouseListener)screen);
		if (screen instanceof MouseMotionListener)
			window.removeMouseMotionListener((MouseMotionListener)screen);
		if (screen instanceof MouseWheelListener)
			window.removeMouseWheelListener((MouseWheelListener)screen);
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
	public Graphics2D next(boolean overlay) {
		// Update the timestamp
		lastFrameTimestamp = System.currentTimeMillis();
		
		// Discard the existing graphics
		frameGraphics.dispose();
		
		// Create the new image
		BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		// Pop the old and push the new
		frameStack.pop();
		frameStack.push(image);
		
		// Create the graphics
		frameGraphics = (Graphics2D)image.getGraphics();
		
		// Draw the previous frame if desired and possible
		if (overlay && frameStack.size() > 1)
			frameGraphics.drawImage(frameStack.get(frameStack.size() - 2), 0, 0,
					image.getWidth(), image.getHeight(), null);
		
		// Return the graphics
		return frameGraphics;
	}
	
	/** Draws the current frame and returns the result. */
	public BufferedImage draw() {
		// Update the graphics
		canvas.repaint();
		
		// Calculate the frame time
		lastFrameDuration = (int)(System.currentTimeMillis() - lastFrameTimestamp);
		
		// And return the drawn frame
		return getFrame();
	}
	
	/** Starts the regular screen updates. */
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
	
	/** Stops the regular screen updates. */
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
