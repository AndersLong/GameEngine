package gfx;

import java.awt.Canvas;

import javax.swing.JFrame;

/**
 * This initializes the JFrame object and creates
 * a display for the app
 * @author Ander
 * @version 1.0
 */
public class Window {

	public Window(Canvas canvas, String title, int width, int height) {
		JFrame frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(canvas);
		canvas.setSize(width,height);
		canvas.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}
}
