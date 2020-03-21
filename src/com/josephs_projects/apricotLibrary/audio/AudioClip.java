package com.josephs_projects.apricotLibrary.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioClip {
	File file;

	public AudioClip(String path) throws IOException {
		file = new File(path);
	}

	public AudioInputStream getAudioStream() {
		try {
			return AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
