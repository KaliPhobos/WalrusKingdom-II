package dinosws.walruskingdom.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dinosws.walruskingdom.visual.GameWindow;

public class TestScreen implements GameScreen {
	final Font font;
	
	public TestScreen() {
		// TODO Auto-generated constructor stub
		font = new Font("Times New Romans", Font.BOLD, 24);
	}

	@Override
	public void onEnable(GameWindow window) {
		System.out.println("Started the screen!");
	}

	@Override
	public void onDisable(GameWindow window) {
		System.out.println("Disabled the screen!");
	}

	@Override
	public void onUpdate(GameWindow window, int delta) {
		System.out.println("Update!");
		Graphics g = window.next(false);
		g.setColor(new Color(255, 255, 255));
		g.setFont(font);
		g.drawString(String.format("Delta: %d", delta), 1, 20);
		window.draw();
		window.pushScreen(new OverlayScreen());
	}

	@Override
	public String getTitle() {
		return "WalrusKingdom";
	}
}
