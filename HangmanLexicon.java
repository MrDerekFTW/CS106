/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file reads in a text file and converts it to a lexicon
 * for use with the Hangman game. 
 */

import java.util.*;
import java.io.*;
import acm.util.*;

public class HangmanLexicon {
	
/** constructor */
	public HangmanLexicon() {
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.wordList = this.readLines(rd);
	}

/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return this.wordList.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return this.wordList.get(index);
	};
	
/** reads the lines from a BufferedReader and returns them as an ArrayList of Strings
 * @return ArrayList<String> with all the lines from the BufferedReader */
	private ArrayList<String> readLines(BufferedReader rd) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			while(true) {
				String line = rd.readLine();
				if (line == null) break;
				list.add(line);
			}
		} catch(IOException ex) {
			throw new ErrorException(ex);
		}
		return list;
	}
	
/** private instance variables */
	ArrayList<String> wordList;
}
