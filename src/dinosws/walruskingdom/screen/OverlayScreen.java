package dinosws.walruskingdom.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dinosws.walruskingdom.visual.GameWindow;

public class OverlayScreen implements GameScreen {
	final Font font;
	
	private int deltaSum = 0;
	
	public OverlayScreen() {
		// TODO Auto-generated constructor stub
		font = new Font("Times New Romans", Font.BOLD, 36);
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
		deltaSum += delta;
		System.out.println("Update!");
		Graphics g = window.next(true);
		g.setColor(new Color(255, 255, 0));
		g.setFont(font);
		g.drawString("Test!", 1, 60);
		window.draw();
		if (deltaSum >= 1000)
			window.popScreen();
	}

	@Override
	public String getTitle() {
		return "WalrusKingdom2";
	}
}
