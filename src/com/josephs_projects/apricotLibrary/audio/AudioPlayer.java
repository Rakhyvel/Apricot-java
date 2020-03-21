package com.josephs_projects.apricotLibrary.audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class AudioPlayer {
	public static synchronized void play(AudioClip sfx) {
		new Thread() {
			public void run() {
				try {
					AudioInputStream stream = sfx.getAudioStream();
					Clip clip = AudioSystem.getClip();
					clip.open(stream);
					clip.start();
				} catch (IOException | LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
