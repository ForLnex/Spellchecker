package errorMatchers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DupMatcher implements MatcherInterface{
	private static final Pattern	DUPPATTERN	= Pattern.compile("(\\w)\\1+");
	private Matcher					matcher;
	private String						data;

	public DupMatcher(String data){
		this.data = data;
		this.matcher = DUPPATTERN.matcher(data);
	}

	/**
	 * Returns a String object identical to the DupMatcher
	 * object's data String with all of the instances of
	 * duplicate characters condensed down to one instance of
	 * that character (i.e. HHHello => Helo).
	 * 
	 * @return A string with no adjacent duplicate
	 *         characters.
	 */
	public String replaceAll(){
		String tempWord;

		while (matcher.find()){
			tempWord = "";
			for (int i = 0; i < matcher.start() + 1; ++i)
				tempWord += data.charAt(i);
			for (int i = matcher.end(); i < data.length(); ++i)
				tempWord += data.charAt(i);

			data = tempWord;
			matcher.reset(data);
		}
		return data;
	}
}