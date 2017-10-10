/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

/** Resets the display so that only the scaffold appears */
	public void reset(int windowWidth, int windowHeight, String hint) {
		this.width = windowWidth/2;
		this.height = windowHeight;
		this.centerX = this.width/2;
		this.centerY = this.height/2;
		this.drawScaffold();
		this.initHintLabel(this.centerX - BEAM_LENGTH, this.centerY + (this.height/4), 
				hint, "SansSerif-26");
		this.initBadLettersLabel(this.centerX - BEAM_LENGTH, this.centerY + (this.height/4) + ROPE_LENGTH*2, 
				this.badLetters, "SansSerif-18");
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		this.resetHintLabel(word);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter, String hint) {
		this.numFails++;
		this.drawNextBodyPart(this.numFails);
		this.badLetters = this.badLetters + letter;
		this.resetBadLettersLable(this.badLetters);
	}
	
/** draws the scaffolding for the hanging man */
	private void drawScaffold() {
		this.drawLine(this.centerX - BEAM_LENGTH, this.centerY + (this.height/8),
					this.centerX - BEAM_LENGTH, this.centerY + (this.height/8) - SCAFFOLD_HEIGHT);
		this.drawLine(this.centerX - BEAM_LENGTH, this.centerY + (this.height/8) - SCAFFOLD_HEIGHT,
					this.centerX, this.centerY + (this.height/8) - SCAFFOLD_HEIGHT);
		this.topOfHead = this.centerY + (this.height/8) - SCAFFOLD_HEIGHT + ROPE_LENGTH;
		this.drawLine(this.centerX, this.centerY + (this.height/8) - SCAFFOLD_HEIGHT,
					this.centerX, this.topOfHead);
		this.bottomOfHead = this.topOfHead + HEAD_RADIUS*2;
	}
	
/** draws a body part for the hanging man based on the number of failures indicated by user */
	private void drawNextBodyPart(int numFails) {
		switch (numFails) {
		case 1:
			drawHead();
			break;
		case 2:
			drawTorso();
			break;
		case 3:
			drawLeftArm();
			break;
		case 4:
			drawRightArm();
			break;
		case 5:
			drawLeftHand();
			break;
		case 6:
			drawRightHand();
			break;
		case 7:
			drawLeftLeg();
			break;
		case 8:
			drawRightLeg();
			break;
		case 9:
			drawLeftFoot();
			break;
		case 10:
			drawRightFoot();
			break;
		}
	}

	private void drawHead() {
		this.drawCircle(this.centerX - HEAD_RADIUS, this.topOfHead, HEAD_RADIUS);
	}

	private void drawTorso() {
		this.drawLine(this.centerX, this.bottomOfHead, 
					this.centerX, this.bottomOfHead + BODY_LENGTH);
	}

	private void drawLeftArm() {
		this.drawLine(this.centerX, this.bottomOfHead + ARM_OFFSET_FROM_HEAD,
					this.centerX - UPPER_ARM_LENGTH, this.bottomOfHead + ARM_OFFSET_FROM_HEAD);
	}

	private void drawRightArm() {
		this.drawLine(this.centerX, this.bottomOfHead + ARM_OFFSET_FROM_HEAD,
				this.centerX + UPPER_ARM_LENGTH, this.bottomOfHead + ARM_OFFSET_FROM_HEAD);
	}
	
	private void drawLeftHand() {
		this.drawLine(this.centerX - UPPER_ARM_LENGTH, this.bottomOfHead + ARM_OFFSET_FROM_HEAD, 
					this.centerX - UPPER_ARM_LENGTH, this.bottomOfHead + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
	}
	
	private void drawRightHand() {
		this.drawLine(this.centerX + UPPER_ARM_LENGTH, this.bottomOfHead + ARM_OFFSET_FROM_HEAD, 
				this.centerX + UPPER_ARM_LENGTH, this.bottomOfHead + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
	}
	
	private void drawLeftLeg() {
		this.drawLine(this.centerX, this.bottomOfHead + BODY_LENGTH,
					this.centerX - HIP_WIDTH, this.bottomOfHead + BODY_LENGTH);
		this.drawLine(this.centerX - HIP_WIDTH, this.bottomOfHead + BODY_LENGTH,
					this.centerX - HIP_WIDTH, this.bottomOfHead + BODY_LENGTH + LEG_LENGTH);
	}
	
	private void drawRightLeg() {
		this.drawLine(this.centerX, this.bottomOfHead + BODY_LENGTH,
				this.centerX + HIP_WIDTH, this.bottomOfHead + BODY_LENGTH);
		this.drawLine(this.centerX + HIP_WIDTH, this.bottomOfHead + BODY_LENGTH,
				this.centerX + HIP_WIDTH, this.bottomOfHead + BODY_LENGTH + LEG_LENGTH);
	}
	
	private void drawLeftFoot() {
		this.drawLine(this.centerX - HIP_WIDTH, this.bottomOfHead + BODY_LENGTH + LEG_LENGTH,
					this.centerX - HIP_WIDTH - FOOT_LENGTH, this.bottomOfHead + BODY_LENGTH + LEG_LENGTH);
	}
	
	private void drawRightFoot() {
		this.drawLine(this.centerX + HIP_WIDTH, this.bottomOfHead + BODY_LENGTH + LEG_LENGTH,
				this.centerX + HIP_WIDTH + FOOT_LENGTH, this.bottomOfHead + BODY_LENGTH + LEG_LENGTH);
	}

/** initializes the hint label displaying what the user has guessed so far */
	private void initHintLabel(int x, int y, String str, String font) {
		this.hintLabel = new GLabel(str, x, y);
		this.hintLabel.setFont(font);
		add(this.hintLabel);
	}

/** resets the hint label to the provided string */
	private void resetHintLabel(String str) {
		this.hintLabel.setLabel(str);
		add(this.hintLabel);
	}

/** initialized the label showing all the players guesses that didn't work */
	private void initBadLettersLabel(int x, int y, String str, String font) {
		this.badLettersLabel = new GLabel(str, x, y);
		this.badLettersLabel.setFont(font);
		add(this.badLettersLabel);
	}

/** resets the bad guesses label with provided string */
	private void resetBadLettersLable(String str) {
		this.badLettersLabel.setLabel(str);
		add(this.badLettersLabel);
	}
	
/** new GLine wrapper function */
	private void drawLine(int x1, int y1, int x2, int y2) {
		GLine line = new GLine(x1, y1, x2, y2);
		line.setColor(Color.BLACK);
		add(line);
	}

/** new GOval wrapper function */
	private void drawCircle(int x, int y, int r) {
		GOval circle = new GOval(r*2, r*2);
		circle.setFilled(false);
		circle.setColor(Color.BLACK);
		add(circle, x, y);
	}
	
	

/** Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

/** private instance variables */
	private int width = 0;
	private int height = 0;
	private int centerX = 0;
	private int centerY = 0;
	private int topOfHead = 0;
	private int bottomOfHead = 0;
	
	private int numFails = 0;
	
	private String hint = "";
	private String badLetters = "";
	
	GLabel hintLabel;
	GLabel badLettersLabel;
}
