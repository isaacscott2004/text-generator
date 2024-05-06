package comprehensive;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;


/**
* This class implements comparator, it is used to sort
* sublists in the markovChain model
* 
* @author Isaac Irwin and Brigham Inkley
* @version April 21, 2024
*/
public class ValueSorter implements Comparator<Map.Entry<String, Double>> {
	/**
	 * this is the compare method it compares the values of each map entry(high is
	 * first) and if there is a tie it compares the keys lexigraphically(natural
	 * ordering)
	 */
	@Override
	public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
		int comparer = o2.getValue().compareTo(o1.getValue());
		if (comparer == 0) {
			comparer = o1.getKey().compareTo(o2.getKey());
		}
		return comparer;
	}

}

