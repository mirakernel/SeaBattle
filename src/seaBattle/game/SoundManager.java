package seaBattle.game;

import javax.sound.sampled.*;
import java.io.*;

public class SoundManager {
	//звуки: попадание, промах, потопление, выигрыш
	private AudioInputStream hit, miss, sunk, win, lose;
	private Clip clip;
	private SoundManager() {
		setup();
	}

	private static SoundManager instance;

	public static synchronized SoundManager getInstance() {
		if (instance == null) {
			instance = new SoundManager();
		}
		return instance;
	}
	
	private void setup() {
		try {
		//инициализировать звуки и все что нужно для их воспроизведения
			String way = "/seaBattle/game/assets/sound/";
			hit = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(way +"hit.wav")));
		miss = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(way +"miss.wav"))); //miss
		sunk = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(way +"sunk.wav"))); //sunk
		win = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(way +"win.wav"))); //win
		lose = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(way +"lose.wav"))); //win

		clip = AudioSystem.getClip();
		}
		catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
		exc.printStackTrace();
		}
	}
	
	private void playSound(AudioInputStream sound) {
		stopSound();
		try {
		clip.open(sound);
		clip.setFramePosition(0);
		clip.start();
		}
		catch (IOException | LineUnavailableException exc) {
			exc.printStackTrace();
		}
	}
	
	private void playShot(boolean b) {
		if(b)
			playHit();
		else
			playMiss();
	}
	
	private void stopSound() {
		clip.stop();
		clip.close();
		setup();
	}
	
	public void playMiss() {
		System.out.println("sound: играет miss");
		playSound(miss);
	}
	
	public void playSunk() {
		System.out.println("sound: играет sunk");
		playSound(sunk);
	}
	
	public void playWin() {
		System.out.println("sound: играет win");
		playSound(win);
	}

	public void playLose() {
		System.out.println("sound: играет lose");
		playSound(lose);
	}
	
	public void playHit() {
		System.out.println("sound: играет hit");
		playSound(hit);
	}
}

