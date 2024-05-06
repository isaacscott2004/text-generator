package comprehensive;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class creates a model for generating random words and texts
 * 
 * @author Isaac Irwin and Brigham Inkley
 * @version April 21, 2024
 */
public class Model {
	private ArrayList<String> listOfWords;
	private HashMap<String, Integer> wordFrequency;
	private HashMap<String, Boolean> sortedMap;
	private HashMap<String, ArrayList<Map.Entry<String, Double>>> markovChain;

	/**
	 * This is the constructor for this class
	 * 
	 * @param listOfWords words to be put into model
	 */
	public Model(ArrayList<String> listOfWords) {
		this.listOfWords = listOfWords;
		this.wordFrequency = getWordFrequencies();
		this.sortedMap = new HashMap<>();
		this.markovChain = makeMarkovChain();

	}

	/**
	 * this method gets the frequency each word appears in list of words(the
	 * frequency of the last word is - 1) used to get the probability of bigram of
	 * word pair.
	 * 
	 * @return a hashmap where the key is the word and the value is the count of
	 *         each word
	 */
	private HashMap<String, Integer> getWordFrequencies() {
		HashMap<String, Integer> counts = new HashMap<>();

		for (String word : this.listOfWords) {
			if (!(counts.containsKey(word))) {
				counts.put(word, 1);
			} else {
				counts.put(word, counts.get(word) + 1);
			}
		}
		String lastWord = listOfWords.get(listOfWords.size() - 1);
		counts.put(lastWord, counts.get(lastWord) - 1); // for most words the frequency is appears in text is equal to
														// the total number of words that come after it except for the
														// last word
														// the frequency for this last word will always be one greater
														// that the
														// total number of words that come after it so, I subtract that
														// value off
		return counts;
	}

	/**
	 * This method gets bigram(two word sequences) frequencies. It loops through a
	 * list of words via this.listofWords. It gets the list of words at i and list
	 * of words at i + 1 and puts them into an arrayList of size two. This arrayList
	 * is the key and the value is the number of times it appears in the list of
	 * words.
	 * 
	 * @return A HashMap of bigrams as the key and the count(number of times it
	 *         appears in bigrams) as the value
	 */
	private HashMap<ArrayList<String>, Double> getBigramFrequencies() {
		HashMap<ArrayList<String>, Double> bigramCount = new HashMap<>();
		for (int i = 0; i < this.listOfWords.size() - 1; i++) {
			ArrayList<String> bigram = new ArrayList<>();
			bigram.add(listOfWords.get(i));
			bigram.add(listOfWords.get(i + 1));
			if (!bigramCount.containsKey(bigram)) {
				bigramCount.put(bigram, 1.0);
			} else {
				bigramCount.put(bigram, bigramCount.get(bigram) + 1);
			}

		}
		return bigramCount;
	}

	/**
	 * This method creates a usable model of a markov Chain. It loops through a list
	 * of words(this.bigramList) and adds the word at i as the key to the hashMap.
	 * The Value is an arrayList of Map.entries where key is the word at i + 1 in
	 * the list of words and the value is the probability of that word occurring
	 * after the word in the key of the hashmap.
	 * 
	 * @return a HashMap of type <String, ArrayList<Map.Entry<String, Double>>>
	 */
	private HashMap<String, ArrayList<Map.Entry<String, Double>>> makeMarkovChain() {
		HashMap<String, ArrayList<Map.Entry<String, Double>>> markovChain = new HashMap<>();
		HashMap<ArrayList<String>, Double> bigramCounts = getBigramFrequencies();

		for (int i = 0; i < this.listOfWords.size() - 1; i++) {

			String currentWord = this.listOfWords.get(i);
			String nextWord = this.listOfWords.get(i + 1);

			if (!(markovChain.containsKey(currentWord))) {
				markovChain.put(currentWord, new ArrayList<>()); // add new value to hashmap
			}

			ArrayList<Map.Entry<String, Double>> nextWordList = markovChain.get(currentWord); // get arrayList key of

			// currentWord
			boolean wordExists = false;
			for (Map.Entry<String, Double> nextWordEntry : nextWordList) {
				if (nextWordEntry.getKey().equals(nextWord)) { // if word is in arrayList stop don't do anything
					wordExists = true;
					break;
				}
			}
			if (!(wordExists)) {

				ArrayList<String> currentBigram = new ArrayList<>(List.of(currentWord, nextWord));
				Double countOfCurrent = bigramCounts.get(currentBigram);
				nextWordList.add(
						new AbstractMap.SimpleEntry<>(nextWord, countOfCurrent / this.wordFrequency.get(currentWord))); // add
																														// word
																														// and
																														// its
																														// probability
																														// of
																														// occurring
			}
		}

		return markovChain;

	}

	/**
	 * If subList is sorted map entry is set to true indicating whether the list is
	 * sorted or not.
	 * 
	 * @param currentWord in the markovChain
	 * 
	 */
	public void setSorted(String key) {
		sortedMap.put(key, true);
	}

	/**
	 * returns whether or not a list has been sorted
	 * 
	 * @param key currentWord in the markovChain
	 * @return true if subList is sorted false otherwise
	 */
	public boolean isSorted(String key) {
		return sortedMap.getOrDefault(key, false); // default value is false unless set true by setSorted
	}

	/**
	 * gets the markov chain created by makeMarkovChain
	 * 
	 * @return the markov chain
	 */
	public HashMap<String, ArrayList<Map.Entry<String, Double>>> getMarkovChain() {
		return this.markovChain;
	}

}
