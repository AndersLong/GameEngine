package inp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class KeyWatcher implements KeyListener{

	public static Set<Character> pressed = new HashSet<>();
	public static boolean spaceAlreadyPressed;

	public KeyWatcher() {
		spaceAlreadyPressed = false;
	}

	public synchronized void keyPressed(KeyEvent e) {
		pressed.add(e.getKeyChar());
	}


	public synchronized void keyReleased(KeyEvent e) {
		pressed.remove(e.getKeyChar());
		if(e.getKeyChar() == ' ') {
			KeyWatcher.spaceAlreadyPressed = false;
		}
	}

	public void keyTyped(KeyEvent e) {}

}
