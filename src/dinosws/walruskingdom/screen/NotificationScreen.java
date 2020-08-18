package dinosws.walruskingdom.screen;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import dinosws.walruskingdom.input.KeyCommand;
import dinosws.walruskingdom.input.KeyTranslator;
import dinosws.walruskingdom.visual.GameWindow;

public class NotificationScreen implements GameScreen, KeyListener, MouseListener {

	private final String message;
	private final KeyTranslator keyTranslator;
	private final Font font;
	private boolean isRunning;
	
	/** The constructor. */
	public NotificationScreen(String message, KeyTranslator keyTranslator) {
		// Repair the input
		if (message == null)
			message = "Click or press ACTION to continue.";
		if (keyTranslator == null)
			keyTranslator = new KeyTranslator();
		
		// Initialize the font
		font = new Font("Arial", Font.BOLD, 18);
		
		// Copy the values
		this.message = message;
		this.keyTranslator = keyTranslator;
	}

	@Override
	public void keyTyped(KeyEvent e) { }
	
	@Override
	public void keyPressed(KeyEvent e) { }
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (keyTranslator.find(e.getKeyCode()) == KeyCommand.ACTION)
			isRunning = false;
	}
	
	@Override
	public void onEnable(GameWindow window) {
		// Initialize the running flag
		isRunning = true;
	}
	
	@Override
	public void onDisable(GameWindow window) { }
	
	@Override
	public void onUpdate(GameWindow window, int delta) {
		// Check, whether to disable the screen
		if (!isRunning)
		{
			window.popScreen();
			return;
		}
		
		// Get the graphics
		Graphics2D graphics = window.next(true);
		
		// Set the font
		graphics.setFont(font);
		
		// Draw the text
		graphics.drawString(message, 0, window.getHeight() - 5);
		graphics.drawString(String.format("Delta: %d", delta), 0, 20);
		
		// Render the frame
		window.draw();
	}

	@Override
	public String getTitle() {
		return "Notification";
	}
	
	public String getMessage() {
		return message;
	}
	
	public KeyTranslator getKeyTranslator() {
		return keyTranslator;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		isRunning = false;
	}

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

}
