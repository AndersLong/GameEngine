package fio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
	
	private Clip clip;
	
	public void setURL(URL soundURL) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			System.out.println("oop");
		}
		
	}

	
	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
		clip.close();
	}
}
