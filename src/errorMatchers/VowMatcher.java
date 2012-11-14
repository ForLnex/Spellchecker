package errorMatchers;

public class VowMatcher implements MatcherInterface{
	public StringBuilder	str;
	public int				index, vowels;
	public char				match;

	public VowMatcher(String data){
		this.str = new StringBuilder(data);
		this.index = -1;

		for (int i = 0; i < str.length(); ++i)
			if (isVowel(str.charAt(i)))
				++vowels;
	}

	public VowMatcher(StringBuilder data){
		this.str = new StringBuilder(data);
		this.index = -1;

		for (int i = 0; i < str.length(); ++i)
			if (isVowel(str.charAt(i)))
				++vowels;
	}

	public String replaceAll(){
		for (int i = 0; i < str.length(); ++i)
			if (isVowel(str.charAt(i)))
				str.setCharAt(i, '_');
		return str.toString();
	}

	public Boolean find(){
		for (int i = index + 1; i < str.length(); ++i)
			if (isVowel(str.charAt(i))){
				match = str.charAt(i);
				index = i;
				return true;
			}
		return false;
	}

	public boolean notAdjacent(){
		boolean notAdjacent = true;

		if (index + 1 < str.length())
			notAdjacent = !isVowel(str.charAt(index + 1));

		if (index != 0)
			notAdjacent = notAdjacent && !isVowel(str.charAt(index - 1));

		return notAdjacent;
	}

	/**
	 * Returns true if the last found vowel in the VowMatcher
	 * object's data field is proceeded or followed by an
	 * instance of the passed in char temp OR another
	 * instance of the last found vowel.
	 * 
	 * @param temp
	 *           The character to test for adjacency to the
	 *           last found vowel
	 * @return true if temp is not adjacent to the last found
	 *         vowel AND the last found vowel is not repeated
	 */
	public boolean notAdjacent(char temp){
		boolean notAdjacent = true;

		if (index + 1 < str.length())
			notAdjacent = str.charAt(index + 1) != temp && str.charAt(index + 1) != match;

		if (index != 0)
			notAdjacent = notAdjacent && str.charAt(index - 1) != temp && str.charAt(index - 1) != match;

		return notAdjacent;
	}

	/**
	 * @param temp
	 *           the character being tested for vowel-hood
	 * @return true if temp is a vowel
	 */
	private boolean isVowel(char temp){
		return (temp == 'a' || temp == 'e' || temp == 'i' || temp == 'o' || temp == 'u');
	}
}
