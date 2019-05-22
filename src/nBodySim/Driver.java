package nBodySim;

import gui.NBodyGUI;
import javafx.application.Application;
import lib.StdAudio;

/**
 * Andrew Wiedenmann. NBody Lab, due 5/21/2019. An n-body simulator and accompanying GUI 
 * @author andre
 *
 */
public class Driver {

	public static void main(String[] args) {
		final String audioClipPath = "src\\data\\2001.wav";
		StdAudio.play(audioClipPath);
		Application.launch(NBodyGUI.class, args);
	}
}
