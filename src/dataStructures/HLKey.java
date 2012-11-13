package dataStructures;

import errorMatchers.DupMatcher;
import errorMatchers.VowMatcher;

public class HLKey{
	protected String	keyName;

	public HLKey(){
		keyName = null;
	}

	public HLKey(String wordIn){
		String word;
		VowMatcher vowels;
		DupMatcher dupes;

		word = wordIn.toLowerCase();

		dupes = new DupMatcher(word);
		word = dupes.replaceAll();

		vowels = new VowMatcher(word);
		word = vowels.replaceAll();

		keyName = word;
	}

	public boolean equals(Object keyIn){
		return this.keyName.equals( ((HLKey) keyIn).keyName);
	}

	public int hashCode(){
		return keyName.hashCode();
	}

	public String toString(){
		return this.keyName;
	}
}