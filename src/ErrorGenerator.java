import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import errorMatchers.VowMatcher;

public class ErrorGenerator{
	private final File	wordList;
	private final int		numEntries;
	private Random			generator;

	public ErrorGenerator(File wordList, int numEntries){
		this.wordList = wordList;
		this.numEntries = numEntries;
		generator = new Random();
	}

	public String[] errorGen(){
		String[] temp;
		do
			temp = EG(new ObfWord(getWord()));
		while (temp[0].equals(temp[1]));
		return temp;
	}

	private String[] EG(ObfWord word){
		String[] temp = new String[2];
		temp[0] = word.buildWord();
		temp[1] = errorGen(word);
		return temp;
	}

	// 0-vow, 1-vow rep, 2-rep, 3-rep case, 4-vow rep case
	// 5-vow case, 6-case

	// Reordered by commands:
	// VOW : 0-vow, 1-vow rep, 4-vow rep case, 5-vow case
	// REP : 1-vow rep, 2-rep, 3-rep case, 4-vow rep case
	// CASE: 3-rep case, 4-vow rep case, 5-vow case, 6-case
	private String errorGen(ObfWord errWord){
		int selector;
		selector = generator.nextInt(7);
		switch (selector){
		case 0:
		case 1:
		case 4:
		case 5:
			errWord.vowelError();
			if (selector == 0) // Vowel only case
				return errWord.buildWord();
		case 2:
		case 3:
			if (selector != 5) // Filters out the
										// Vowel-Capitalization case
				errWord.repeatError();
			if (selector < 3) // Either Vowel-Repeat, or Repeat
									// only case
				return errWord.buildWord();
		case 6:
			errWord.caseError();
			return errWord.buildWord();
		}
		return null;
	}

	private String getWord(){
		int currentIndex, wordIndex;
		double rand;
		String word;

		Scanner in = null;
		try{
			in = new Scanner(wordList);
		}
		catch (FileNotFoundException e){
			System.out.println("Error: Could not find dictionary at " + wordList.getPath());
			System.exit(0);
		}

		currentIndex = 0;
		rand = generator.nextInt(numEntries) + 1;
		wordIndex = (int) rand;
		while (++currentIndex < wordIndex)
			in.nextLine();
		word = in.nextLine();
		in.close();
		return word;
	}
}

class ObfWord{
	String			word;
	StringBuilder	str;
	Random			generator;

	ObfWord(String word){
		this.word = word;
		this.str = new StringBuilder(word);
		generator = new Random();
	}

	void caseError(){
		char ch;

		for (int i = 0; i < str.length(); ++i)
			if (generator.nextInt(2) == 0){
				ch = str.charAt(i);
				if (Character.isUpperCase(ch))
					str.setCharAt(i, Character.toLowerCase(ch));
				if (Character.isLowerCase(ch))
					str.setCharAt(i, Character.toUpperCase(ch));
			}
	}

	void repeatError(){
		char ch;
		int rand;

		for (int i = 0; i < str.length(); ++i)
			if (generator.nextInt(4) == 0 && Character.isLetter(this.str.charAt(i))){
				ch = this.str.charAt(i);
				rand = generator.nextInt(5);
				str.ensureCapacity(str.capacity() + rand + 1);
				str.insert(i, new char[] { ch, ch, ch, ch, ch }, 0, rand);
				i += rand + 2;
			}
	}

	void vowelError(){
		VowMatcher nextVowel = new VowMatcher(str);
		while (nextVowel.find() && nextVowel.notAdjacent())
			switch (generator.nextInt(5)){
			case 0:
				if (nextVowel.notAdjacent('a'))
					str.setCharAt(nextVowel.index, 'a');
				break;
			case 1:
				if (nextVowel.notAdjacent('e'))
					str.setCharAt(nextVowel.index, 'e');
				break;
			case 2:
				if (nextVowel.notAdjacent('i'))
					str.setCharAt(nextVowel.index, 'i');
				break;
			case 3:
				if (nextVowel.notAdjacent('o'))
					str.setCharAt(nextVowel.index, 'o');
				break;
			case 4:
				if (nextVowel.notAdjacent('u'))
					str.setCharAt(nextVowel.index, 'u');
				break;
			}
	}

	String buildWord(){
		return str.toString();
	}
}