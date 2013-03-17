package uk.co.marshmallow_zombies.rj2dgl.framework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame implements WindowListener {

	private static final long serialVersionUID = 1L;

	public final class Panel extends JPanel {
		private static final long serialVersionUID = 1L;

		private Game game;
		BufferedImage canvas;
		long lastRender = System.nanoTime();

		Panel(Game game) {
			this.game = game;
			canvas = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);

			setBackground(Color.BLACK);
			setOpaque(true);
			setLocation(0, 0);
		}

		void startRepaintThread() {
			final Game finalGame = game;

			Thread repaintThread = new Thread() {
				@Override
				public void run() {
					long lastTick = System.nanoTime();

					finalGame.start();

					while (finalGame.running) {
						long now = System.nanoTime();
						long delta = now - lastTick;

						finalGame.tick(delta);
						repaint();

						try {
							Thread.sleep(20);
						} catch (Exception e) {
						}

						lastTick = System.nanoTime();
					}

					finalGame.stop();
				}
			};
			repaintThread.start();
		}

		@Override
		public void paintComponent(Graphics g) {
			long now = System.nanoTime();
			long delta = now - lastRender;

			if (canvas == null || game == null)
				return;

			setOpaque(true);
			super.paintComponent(g);
			g.drawImage(canvas, 0, 0, null);

			game.render(canvas.getGraphics(), delta);

			lastRender = System.nanoTime();
		}
	}

	Panel panel;
	private Game game;

	Window(Game game) {
		this.game = game;
		panel = new Panel(game);
	}

	void init() {
		add(panel);
		setContentPane(panel);

		Keyboard.listen();

		setLocationRelativeTo(null);
		addWindowListener(this);
		addKeyListener(Keyboard.getListener());

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}

	void centre(boolean tk) {
		if (tk) {
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - getHeight()) / 2);
			setLocation(x, y);
		} else {
			setLocationRelativeTo(null);
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		game.running = false;
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
};
