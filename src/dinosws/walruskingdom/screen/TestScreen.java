package dinosws.walruskingdom.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dinosws.walruskingdom.visual.GameWindow;

public class TestScreen implements GameScreen {
	final Font font;
	
	int deltaSum = 0;
	int overlayCount = 0;
	
	public TestScreen() {
		// TODO Auto-generated constructor stub
		font = new Font("Times New Romans", Font.BOLD, 24);
	}

	@Override
	public void onEnable(GameWindow window) {
		System.out.println("Started the screen!");
		window.cursorDefault();
	}

	@Override
	public void onDisable(GameWindow window) {
		System.out.println("Disabled the screen!");
	}

	@Override
	public void onUpdate(GameWindow window, int delta) {
		System.out.println("Update!");
		Graphics g = window.next(false);
		g.setColor(new Color(0, 255, 0));
		g.setFont(font);
		g.drawString(String.format("Delta: %d", delta), 1, 120);
		window.draw();
		
		deltaSum += delta;
		
		if (deltaSum < 2000)
			return;
		deltaSum = 0;
		
		if (overlayCount >= 2) {
			window.pushScreen(new NotificationScreen(null, null));
			overlayCount = 0;
			return;
		}
		overlayCount++;
		
		window.pushScreen(new OverlayScreen());
	}

	@Override
	public String getTitle() {
		return "WalrusKingdom";
	}
}
