import dataStructures.HLDictionary;
import dataStructures.HLKey;

public class Spellchecker{
	private final HLDictionary	dictionary;

	public Spellchecker(HLDictionary dictIn){
		this.dictionary = dictIn;
	}

	public String spellCheck(String wordIn){
		HLKey key = new HLKey(wordIn);

		if (dictionary.containsValue(key, wordIn))
			return "NO SUGGESTION";
		if (dictionary.containsCasedValue(key, wordIn))
			return dictionary.get(key).casedWord(wordIn);
		if (dictionary.get(key) != null)
			return dictionary.get(key).closestWord(wordIn);

		return "NO SUGGESTION";
	}
}