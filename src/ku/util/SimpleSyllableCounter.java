package ku.util;

/**
 * Count syllables of word using state machine approach with a simple enum for state.
 * @author Nitith Chayakul
 *
 */
public class SimpleSyllableCounter extends SyllableCounter {
	
	/** @see SyllableCounter#countSyllables(String) */
	public int countSyllables(String word) {
		int syllables = 0;
		char c = ' ';
		State state = State.START;
		for (int k = 0 ; k < word.length() ; k++) {
			c = word.charAt(k);
			c = Character.toLowerCase(c);
			switch( state ) {
			case START:
				if ( isLetter(c) && !isVowel(c) ) state = State.CONSONANT;
				else if ( isVowel(c) || c == 'y' ) {
					syllables++;
					state = State.SINGLE_VOWEL;
				}
				else if ( !isLetter(c) ) state = State.NONWORD;
				else if ( c == ' ' ) ; // remain start
				break;
			case CONSONANT:
				if ( c == 'e' && k == word.length()-1 && syllables != 0 ) ;
				else if ( isVowel(c) || c == 'y' ) {
					syllables++;
					state = State.SINGLE_VOWEL;
				}
				else if ( isLetter(c) && !isVowel(c) || isIgnore(c) ) ; //remain consonant
				else if ( isHyphen(c) ) state = State.HYPHEN;
				else if ( !isLetter(c) ) state = State.NONWORD;
				break;
			case SINGLE_VOWEL:
				if ( isLetter(c) && !isVowel(c) || isIgnore(c) ) state = State.CONSONANT;
				else if ( isVowel(c) ) state = State.MULTIVOWEL;
				else if ( isHyphen(c) ) state = State.HYPHEN;
				else if ( !isLetter(c) ) state = State.NONWORD;
				break;
			case MULTIVOWEL:
				if ( isLetter(c) && !isVowel(c) || isIgnore(c) ) state = State.CONSONANT;
				else if ( isVowel(c) ) ; //remain multivowel
				else if ( isHyphen(c) ) state = State.HYPHEN;
				else if ( !isLetter(c) ) state = State.NONWORD;
				break;
			case HYPHEN:
				if ( c == 'y' && k == word.length()-1 ) syllables++;
				else if ( isVowel(c) || c == 'y' ) {
					syllables++;
					state = State.SINGLE_VOWEL;
				}
				else if ( isLetter(c) && !isVowel(c) || isIgnore(c) ) state = State.CONSONANT;
				else if ( c == 'e' && k == word.length()-1 && syllables != 0) ;
				else if ( isHyphen(c) ) state = State.NONWORD;
				else if ( !isLetter(c) ) state = State.NONWORD;
				break;
			case NONWORD:
				syllables = 0;
				break;
			default :
				break;
			}
		}
		return syllables;
	}
	
	/**
	 * Check if char is ignore
	 * @param c - char that want to check
	 * @return true if c is ignore
	 */
	private boolean isIgnore(char c) {
		if (c == '\'') return true;
		return false;
	}
	
	/**
	 * Check if char is letter
	 * @param c - char that want to check
	 * @return true if c is letter
	 */
	private boolean isLetter(char c) {
		return Character.isLetter(c) ;
	}

	/**
	 * check if char is hyphen (-)
	 * @param c - char that want to check
	 * @return true if c is hyphen
	 */
	private boolean isHyphen(char c) {
		if (c == '-') return true;
		return false;
	}

	/**
	 * Check if char is vowel.
	 * @param c - char that want to check
	 * @return true if c is vowel
	 */
	private boolean isVowel(char c) {
		char[] vowel = {'a', 'A', 'e', 'E', 'i', 'I', 'o', 'O', 'u', 'U'};
		for (char check : vowel) {
			if (c == check) return true;
		}
		return false;
	}
	
	/**
	 * Various state of SimpleSyllableCounter object.
	 */
	enum State {
		START,
		CONSONANT,
		SINGLE_VOWEL,
		MULTIVOWEL,
		HYPHEN,
		NONWORD;
	}
	
}
