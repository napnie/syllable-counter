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
