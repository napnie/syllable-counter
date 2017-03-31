package ku.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Count syllables and words from online dictionary.
 * @author Nitith Chayakul
 * @version 1.0
 *
 */
public class Main {
	
	/**
	 * Run the program
	 * @param arg
	 */
	public static void main(String[] arg) {
		System.out.println( getCount() );
	}
	
	/**
	 * Get InputStream from dictionary.
	 * @return InputStream of dictionary
	 */
	private static InputStream getInput() {
		final String DICT_URL = "http://se.cpe.ku.ac.th/dictionary.txt";
		try {
			URL url = new URL(DICT_URL);
			InputStream input = url.openStream();
			return input;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage());
		} catch (IOException io) {
			throw new RuntimeException(io.getMessage());
		}
	}
	
	/**
	 * Count syllables and words from InputStream.
	 * @return text described count of syllables and words
	 */
	private static String getCount() {
		BufferedReader reader = new BufferedReader (new InputStreamReader( getInput() ) );
		int syll = 0;
		int wordCount = 0;
		SyllableCounter counter = new SimpleSyllableCounter();
		String word;
		try {
			while( (word = reader.readLine()) != null ) {
				syll += counter.countSyllables(word);
				wordCount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String output = "Reading words from http://se.cpe.ku.ac.th/dictionary.txt";
		output += "\n" + String.format("Counted %d syllables in %d words",syll,wordCount);
		return output;
	}
}
