package regex_investigation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regex_investigation {

	public static void main(String[] args) {
		
		int PARSE_URL_PATH = 5;
	    String URL_REGEX = "^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?";
	    Pattern URL_PATTERN = Pattern.compile(URL_REGEX);	   
		
		String PATH_REGEX_GOOD = "^(/[-\\w:@&?=+,.!/~*'%$_;\\(\\)]*)?$";
	    String PATH_REGEX_BAD = "^(/[-\\w:@&?=+,.!*'%$_;\\(\\)]*)?$";

	    Pattern good = Pattern.compile(PATH_REGEX_GOOD);
	    Pattern bad = Pattern.compile(PATH_REGEX_BAD);
	    
		try {
			File file = new File("URLS_FROM_PASSING_TEST.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line;
			while ((line = bufferedReader.readLine()) != null) {
//				System.out.println("______");
//				System.out.println(line);
				Matcher urlMatcher = URL_PATTERN.matcher(line);
				if (urlMatcher.matches()) {
					
					Matcher good_match = good.matcher(urlMatcher.group(PARSE_URL_PATH));
					Matcher bad_match = bad.matcher(urlMatcher.group(PARSE_URL_PATH));
					if (good_match.matches() != bad_match.matches()) {
						System.out.println("*************************");
						System.out.println("URL: " + line);
						System.out.println("PATH: " +  urlMatcher.group(PARSE_URL_PATH));
						System.out.println("good: " + String.valueOf(good_match.matches()));
						System.out.println("bad: " + String.valueOf(bad_match.matches()));
					}
				}
			}
			fileReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}