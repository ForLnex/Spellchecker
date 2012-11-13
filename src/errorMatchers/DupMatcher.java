package errorMatchers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DupMatcher implements MatcherInterface{
	private final Pattern	DUPPATTERN	= Pattern.compile("(\\w)\\1+");
	private Matcher			matcher;
	private String				data;

	public DupMatcher(String data){

		matcher = DUPPATTERN.matcher(data);
		this.data = data;
	}

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
