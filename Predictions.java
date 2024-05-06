package comprehensive;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
/**
 * This class creates a predictor for predicting random words based on how
 * probable they are at occurring.
 * 
 * @author Isaac Irwin and Brigham Inkley
 * @version April 17, 2024
 */
public class Predictions {
	private Random random;
	/**
	 * This is the constructor 
	 * for this class
	 */
	public Predictions() {
		this.random = new Random();
	}
	/**
	 * this chooses a random word from sublist random words based on how
	 * probable it is at occurring(0.0 - 1).
	 * @param subList the list of words to choose from
	 * @return a randomly selected word
	 */
	public String chooseRandom(ArrayList<Map.Entry<String, Double>> subList) {
		Double randomDouble = random.nextDouble();
		Double counter = 0.0; 
		String chosenWord = "";
		for (int i = 0; i < subList.size(); i ++) {
			Double currentProb = subList.get(i).getValue();
			counter = counter + currentProb;
			if (randomDouble <= counter) { 
				chosenWord = subList.get(i).getKey();
				break;
			}
		}
		return chosenWord;
	}
	

}
