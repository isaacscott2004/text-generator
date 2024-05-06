package comprehensive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * This class creates a text parser for parsing text from txt files
 * into arrayLists of words
 * 
 * @author Isaac Irwin and Brigham Inkley
 * @version April 17, 2024
 */
public class TxtParserGold {
	/**
	 * this method takes in a filename it parses the text and takes out all punctuation.
	 * if punctuation occurs at the beginning of the word word is "thrown out" if punctuation
	 * occurs in middle of word word = letters before punctuation.
	 * @param filename the name of the file to be parsed
	 * @return an array list of words containing all the words from the file
	 */
	public static ArrayList<String> readFile(String filename) {
	    ArrayList<String> allWords = new ArrayList<>();
	    File file = new File(filename);
	    
	    try {
	        BufferedReader reader = new BufferedReader(new FileReader(file));
	        String line;
	        do {
	            line = reader.readLine();
	            if (line != null) {
	                String[] words = line.split("\\s+"); //regular expression matches one or more(+) whitespaces
	                                                     // used to split lines into words
	                for (String word : words) {
	                	if (!word.isEmpty() && !symbolInZero(word)) {
	                		word = removeLettersAfterPuncuation(word);
	                		allWords.add(word.toLowerCase());
	                	}
	                    
	                }
	            }
	        } while (line != null);
	        reader.close();
	    } catch (IOException e) {
	        return new ArrayList<String>();
	    }
	    
	    return allWords;
	}		
		

	/**
	 * This word checks if there is a symbol/punctuation in zero index of string (except for '_')
	 * @param word word to be checked
	 * @return true if there is a symbol in zero index, false otherwise.
	 */
	private static boolean symbolInZero(String word) {
		char firstChar = word.charAt(0);
		return !(Character.isLetter(firstChar) || Character.isDigit(firstChar) || firstChar == '_');
	}
	
	
	/**
	 * this method removes letters after punctuation(excluding _) and returns the letters before punctuation
	 * @param word thats being changed(if contains punctuation
	 * @return new word(if word contained punctuation) or same word if it didn't contain punctuation.
	 */
	private static String removeLettersAfterPuncuation(String word) {
		int index = -1;
		for(int i = 0; i < word.length(); i++) {
			if(!(Character.isLetterOrDigit(word.charAt(i))|| word.charAt(i) == '_')) {
				index = i;
				break;
			}
		}
		if(index != -1 ) {
			return word.substring(0, index);
		}
		return word;
		
	}

}
