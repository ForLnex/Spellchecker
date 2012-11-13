package errorMatchers;

public class VowMatcher implements MatcherInterface{
	public StringBuilder	str;
	public int				index, vowels;
	public char				match;

	public VowMatcher(String data){
		char temp;

		this.str = new StringBuilder(data);
		this.index = -1;

		for (int i = 0; i < str.length(); ++i){
			temp = str.charAt(i);
			if (isVowel(temp))
				++vowels;
		}
	}

	public VowMatcher(StringBuilder data){
		char temp;

		this.str = new StringBuilder(data);
		this.index = -1;

		for (int i = 0; i < str.length(); ++i){
			temp = str.charAt(i);
			if (isVowel(temp))
				++vowels;
		}
	}

	public String replaceAll(){
		char temp;
		for (int i = 0; i < str.length(); ++i){
			temp = str.charAt(i);
			if (isVowel(temp))
				str.setCharAt(i, '_');
		}
		return str.toString();
	}

	public Boolean find(){
		char temp;
		for (int i = index + 1; i < str.length(); ++i){
			temp = str.charAt(i);
			if (isVowel(temp)){
				match = temp;
				index = i;
				return true;
			}
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

	public boolean notAdjacent(char temp){
		boolean notAdjacent = true;

		if (index + 1 < str.length())
			notAdjacent = str.charAt(index + 1) != temp && str.charAt(index + 1) != match;

		if (index != 0)
			notAdjacent = notAdjacent && str.charAt(index - 1) != temp && str.charAt(index - 1) != match;

		return notAdjacent;
	}

	public void replace(char replacement){
		this.str.setCharAt(index, replacement);
	}

	private boolean isVowel(char temp){
		return (temp == 'a' || temp == 'e' || temp == 'i' || temp == 'o' || temp == 'u');
	}
}
