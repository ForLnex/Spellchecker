package dataStructures;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

@SuppressWarnings("serial")
public class HLDictionary extends HashMap<HLKey, LLStringList>{
	public int			numEntries;
	public final File	DICTIONARYFILE;

	public HLDictionary(File fileIn) throws FileNotFoundException{
		this.DICTIONARYFILE = fileIn;

		Scanner dictionaryFile = new Scanner(DICTIONARYFILE);
		String line = dictionaryFile.nextLine();
		this.numEntries = 0;

		while (dictionaryFile.hasNextLine()){
			line = dictionaryFile.nextLine();
			this.put(line);
			++this.numEntries;
		}
		dictionaryFile.close();
	}

	public void put(String value){
		HLKey key = new HLKey(value);
		LLStringList temp = this.get(key);

		if (temp != null)
			temp.insert(value);
		else
			super.put(key, new LLStringList(key, value));
	}

	public boolean containsValue(String value){
		HLKey key = new HLKey(value);

		return containsValue(key, value);
	}

	public boolean containsValue(HLKey key, String value){
		return (containsKey(key) && get(key).contains(value));
	}

	public boolean containsCasedValue(HLKey key, String value){
		LLStringList temp = this.get(key);

		if (temp != null)
			return (temp.casedWord(value) != null);
		return false;
	}
}
