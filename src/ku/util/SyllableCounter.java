package ku.util;

/**
 * Syllable Counter super class.
 * @author Nitith Chayakul
 * @version 1.0
 *
 */
public abstract class SyllableCounter {
	
	/**
	 * Count syllables in word
	 * @param word - String of word that want to count syllables
	 * @return syllables of word
	 */
	public abstract int countSyllables(String word) ;
	
	/**
	 * Check if char is ignore
	 * @param c - char that want to check
	 * @return true if c is ignore
	 */
	protected boolean isIgnore(char c) {
		if (c == '\'') return true;
		return false;
	}
	
	/**
	 * Check if char is letter
	 * @param c - char that want to check
	 * @return true if c is letter
	 */
	protected boolean isLetter(char c) {
		return Character.isLetter(c) ;
	}

	/**
	 * check if char is hyphen (-)
	 * @param c - char that want to check
	 * @return true if c is hyphen
	 */
	protected boolean isHyphen(char c) {
		if (c == '-') return true;
		return false;
	}

	/**
	 * Check if char is vowel.
	 * @param c - char that want to check
	 * @return true if c is vowel
	 */
	protected boolean isVowel(char c) {
		char[] vowel = {'a', 'A', 'e', 'E', 'i', 'I', 'o', 'O', 'u', 'U'};
		for (char check : vowel) {
			if (c == check) return true;
		}
		return false;
	}
}
