package comprehensive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * This class generates text. If there are three arguments
 * the text generated is the k most probable words after the seed word
 * if there are 4 arguments and the last argument is "all" the text is generated randomly 
 * using the probabilities of each possible next word. If the last argument is "one" then 
 * the text is generated with the most probable next word(is deterministic).
 * 
 * @author Isaac Irwin and Brigham Inkley
 * @version April 21, 2024
 */
public class TextGenerator {
	
public static void main (String[] args) {
		
		ArrayList<String> parsedText = TxtParserGold.readFile(args[0]);
		Model modelObject = new Model (parsedText);
		HashMap<String, ArrayList<Map.Entry<String, Double>>> model = modelObject.getMarkovChain();
		Predictions predictor = new Predictions();
		
		if (args.length == 3) {
			ArrayList<Map.Entry<String, Double>> subList = model.get(args[1]);
			if(!modelObject.isSorted(args[1])) { // checks if list has been sorted
			Collections.sort(subList, new ValueSorter());
			modelObject.setSorted(args[1]);
			}
			int k = Integer.parseInt(args[2]);
			if(k >= subList.size()) { 
				for(int i = 0; i < subList.size() - 1; i++) {
					System.out.print(subList.get(i).getKey() + " ");
				}
				System.out.println(subList.get(subList.size() - 1).getKey());
			} else {
				for(int j = 0; j <  k - 1;  j++) {
					System.out.print(subList.get(j).getKey() + " ");
				}
				System.out.print(subList.get(k - 1).getKey());
			}
		} else if(args.length == 4) {			
			if (args[3].equals("all")) {
				int k = Integer.parseInt(args[2]);
				String seedWord = args[1];
				System.out.print(args[1] + " ");
				String currentWord = args[1];
				for (int i = 0; i < k - 2; i ++) {
					ArrayList<Map.Entry<String, Double>> currentSubList = model.get(currentWord);
					if(currentSubList == null) {
						currentWord = seedWord; // if currentWord has no next word current word will go back to the seed word.
					} else {
						if(!modelObject.isSorted(currentWord)) { // checks if list has been sorted
							Collections.sort(currentSubList, new ValueSorter());
							modelObject.setSorted(currentWord);
						}
					currentWord = predictor.chooseRandom(currentSubList);
				
					}
					System.out.print(currentWord + " ");
				}
				ArrayList<Map.Entry<String, Double>> currentSubList = model.get(currentWord);
				if(currentSubList == null) {
					currentWord = seedWord; // if currentWord has no next word current word will go back to the seed word.
				} else {
					if(!modelObject.isSorted(currentWord)) { // checks if list has been sorted
						Collections.sort(currentSubList, new ValueSorter());
						modelObject.setSorted(currentWord);
					}
				currentWord = predictor.chooseRandom(currentSubList);
				}
				System.out.print(currentWord);				
			} else if (args[3].equals("one")) {
				int k = Integer.parseInt(args[2]);
				String seedWord = args[1];
				System.out.print(args[1] + " ");
				String currentWord = args[1];
				for (int i = 0; i < k - 2; i++) {
					ArrayList<Map.Entry<String, Double>> currentSubList = model.get(currentWord);
					if(currentSubList == null) {
						currentWord = seedWord; // if currentWord has no next word current word will go back to the seed word.
					} else {
						if(!modelObject.isSorted(currentWord)) { // checks if list has been sorted
							Collections.sort(currentSubList, new ValueSorter());
							modelObject.setSorted(currentWord);
						}	
					currentWord = currentSubList.get(0).getKey();
					}
					System.out.print(currentWord + " ");
					
				}
				ArrayList<Map.Entry<String, Double>> currentSubList = model.get(currentWord);
				if(currentSubList == null) {
					currentWord = seedWord; // if currentWord has no next word current word will go back to the seed word.
				} else {
					if(!modelObject.isSorted(currentWord)) { // checks if list has been sorted
						Collections.sort(currentSubList, new ValueSorter());
						modelObject.setSorted(currentWord);
					}
				currentWord = currentSubList.get(0).getKey();
				}
				System.out.print(currentWord);
				
			}
		}
	}


}
