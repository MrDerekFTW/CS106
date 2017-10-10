/*
 * File: Hangman.java
 * -------------------
 * Name: Derek Blankenship
 * 
 * This file implements the game Hangman with a console for inputing guesses
 * and a canvas for drawing the hanging man and the player's guesses. 
 * 
 * The lexicon for this game includes over 121,000 words! A true challenge.
 */

//import acm.graphics.*;
import acm.program.*;
import acm.util.*;
//import java.awt.*;

public class Hangman extends ConsoleProgram {
	
/** number of guesses allowed to player */
	private static final int NUM_GUESSES = 10;
	
/** initializes the hangman canvas */
	public void init() {
		this.canvas = new HangmanCanvas();
		add(this.canvas);
	}
	
/** sets up hangman and plays it once */
    public void run() {
    	setFont("TimesNewRoman-20");
		setupHangman();
		playHangman();
	}
   
/** creates a random number generator and hangman lexicon;
 * chooses the secret word; draws the hangman and word (as dashes)
 * onto the screen */
    private void setupHangman() {
    	this.rgen = new RandomGenerator();
    	this.hlex = new HangmanLexicon();
    	this.word = this.hlex.getWord(rgen.nextInt(0, this.hlex.getWordCount()));
    	for (int i = 0; i < this.word.length(); i++) {
    		this.hint = this.hint + "-";
    	}
    	this.blanksRemaining = this.word.length();
    	this.canvas.reset(this.getWidth(), this.getHeight(), this.hint);
    	this.canvas.displayWord(this.hint);
    	println("Welcome to Hangman!");
    }
   
/** runs the game loop once and prints the result of the game */
    private void playHangman() {
    	while (!this.gameOver) {
    		guessLoop();
    	}
    	printResult();
    }

/** receives a single guess from the user and operates on it; 
 * if the user gives something other then a letter, asks them for another;
 * if the user has already guessed that letter, asks them for another;
 * if the guess is incorrect, tells the user and updates the canvas
 * if the guess is correct, tells the user and updates the canvas */
    private void guessLoop() {
    	printHint();
		printGuesses();
		char guess;
		while(true) {
			guess = getGuess();
			if (guess != '0') {
				break;
			} else {
				println("Invalid guess, try again.");
			}
		}
		if (checkForDuplicate(guess)) {
			println("You've already guesssed that letter, please try again.");
		} else if (!checkNewLetter(guess)) {
			println("There are no " + guess + "s in the word.");
			this.guessesRemaining -= 1;
			this.canvas.noteIncorrectGuess(guess, this.hint);
			if (this.guessesRemaining <= 0) {
				this.gameOver = true;
			}
		} else {
			println("That guess is correct.");
			if (this.blanksRemaining <= 0) {
				this.gameOver = true;
				this.victory = true;
			}
			this.canvas.displayWord(this.hint);
		}
		this.guessHistory = this.guessHistory + guess;
    }
    
 /** prints what the user has guessed so far */
    private void printHint() {
    	println("The word now looks like this: " + this.hint);
    }

/** prints the number of guesses remaining */
    private void printGuesses() {
    	println("You have " + this.guessesRemaining + " guesses left.");
    }

/** receives a String guess from the user, removes white space, and checks the 
 * first character of the string 
 *  @return a capitalized version of the first character if it is a valid guess; character '0' if guess is invalid */
    private char getGuess() {
    	String line = readLine("Your guess: ");
    	line = removeWhiteSpace(line);
    	if (line == "" || !Character.isAlphabetic(line.charAt(0)) || line.length() > 1) {
    		return '0';
    	}
    	char charGuess = Character.toUpperCase(line.charAt(0));
    	return charGuess;
    }
    
/** checks to see if the character has been guessed before 
 * @return true if character has been guessed already; false otherwise */
    private boolean checkForDuplicate(char guess) {
    	for (int i = 0; i < this.guessHistory.length(); i++) {
    		if (guess == this.guessHistory.charAt(i)) {
    			return true; // duplicate found
    		}
    	}
    	return false;
    }

/** checks a letter to see if it is in the secret word 
 * @return true if character is in secret word; false if not */
    private boolean checkNewLetter(char guess) {
    	boolean charFound = false;
    	for (int i = 0; i < this.word.length(); i++) {
    		if (guess == this.word.charAt(i)) { // replaces all appropriate blanks in hint with correct guessed letter
    			this.hint = stringReplaceChar(i, guess, this.hint); 
    			this.blanksRemaining -= 1;
    			charFound = true;
    		}
    	}
    	return charFound;
    }

/** removes white space from a string
 * @return a copy of the string with whitespace removed */
    private String removeWhiteSpace(String line) {
    	String temp = "";
    	for (int i = 0; i < line.length(); i++) {
    		if (!Character.isWhitespace(line.charAt(i))) {
    			temp = temp + line.charAt(i);
    		}
    	}
    	return temp;
    }
    
/** replaces character at int index in String str with char c
 * @return new string with character at index replaced */
    private String stringReplaceChar(int index, char c, String str) {
    	return str.substring(0, index) + c + str.substring(index + 1);
    }
 
/** prints a message of defeat or victory to the user */
    private void printResult() {
    	if (this.victory) {
    		println("You guessed the word: " + this.word);
    		println("You win!");
    	} else {
    		println("You're completely hung.");
    		println("The word was: " + this.word);
    		println("You lose :(");
    	}
    }
    
 /** private instance variables */
    RandomGenerator rgen = null;
    HangmanLexicon hlex = null;
    boolean gameOver = false;
    boolean victory = false;
    String word = "";
    String hint = "";
    String guessHistory = "";
    int guessesRemaining = NUM_GUESSES;
    int blanksRemaining = 0;
    private HangmanCanvas canvas;
}
