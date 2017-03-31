package ku.util;

public class CountSyllables {
	
	/**
	 * Count syllables in word
	 * @param word - String of word that want to count syllables
	 * @return syllables of word
	 */
	public int countSyllables(String word) {
		int syllables = 0;
		char c = ' ';
		State state = State.START;
		for (int k = 0 ; k < word.length() ; k++) {
			c = word.charAt(k);
			switch( state ) {
			case START:
				notic("Start: ",c);
				if ( isGarbage(c) || isHyphen(c) ) state = State.NONWORD;
				else if ( isVowel(c) || c == 'y' ) {
					syllables++;
					state = State.VOWEL;
				}
				else if ( Character.isLetter(c) || isIgnore(c) ) state = State.CONSONANT;
				break;
			case CONSONANT:
				notic("Consonant: ",c);
				if (k == word.length()-2 ) {
					if ( isVowel(c) ) {
						syllables++;
						state = State.END;
					}
					else state = State.FINAL;
				}
				else if ( isVowel(c) || c == 'y' ) {
					syllables++;
					state = State.VOWEL;
				}
				else if ( Character.isLetter(c) ) ;// stay consonant
				else if ( isHyphen(c) ) state = State.HYPHEN;
				else if ( isGarbage(c) ) state = State.NONWORD;
				break;
			case VOWEL:
				notic("Vowel: ",c);
				if (k == word.length()-1 ) state = State.END;
				else if ( isVowel(c) ) state = State.MULTIVOWEL;
				else if ( Character.isLetter(c) || isIgnore(c) ) state = State.CONSONANT;
				else if ( isHyphen(c) ) state = State.HYPHEN;
				else if ( isGarbage(c) ) state = State.NONWORD;
				break;
			case MULTIVOWEL:
				notic("Multivowel: ",c);
				if (k == word.length()-1 ) state = State.END;
				else if ( isVowel(c) ) ;// stay multivowel
				else if ( Character.isLetter(c) || isIgnore(c) ) state = State.CONSONANT;
				else if ( isHyphen(c) ) state = State.HYPHEN;
				else if ( isGarbage(c) ) state = State.NONWORD;
//				state = State.CONSONANT;
				break;
			case HYPHEN:
				notic("Hyphen: ",c);
				if ( isVowel(c) || c == 'y' ) {
					syllables++;
					state = State.VOWEL;
				}
				else if ( Character.isLetter(c) || isIgnore(c) ) state = State.CONSONANT;
				else if ( isHyphen(c) ) state = State.NONWORD;
				else if ( isGarbage(c) ) state = State.NONWORD;
				break;
			case NONWORD:
				notic("Nonword: ",c);
				return 0;
			case FINAL:
				notic("Final: ",c);
				if ( isGarbage(c) ) state = State.NONWORD;
				else if ( isVowel(c) && c != 'e' ) syllables++;
				else if ( syllables == 0 && c == 'e' ) syllables++;
				else if ( c == 'y' ) syllables++;
				else if ( Character.isLetter(c) || isIgnore(c) ) state = State.END;
				break;
			case END:
				notic("End: ",c);
				return syllables;
			default:
				return syllables;
			}
		}
		return syllables;
	}

	private void notic(String noti, char c) {
		System.out.println(noti+c);
	}
	
	/**
	 * Check if char is ignore
	 * @param c - char that want to check
	 * @return true if c is ignore
	 */
	private boolean isIgnore(char c) {
		if (c == '\'' || c == ' ') return true;
		return false;
	}
	
	/**
	 * Check if char is garbage.
	 * @param c - char that want to check
	 * @return true if c is garbage
	 */
	private boolean isGarbage(char c) {
		if ( !Character.isLetter(c) && !isVowel(c) && !isHyphen(c) && !isIgnore(c) ) return true;
		return false;
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

	enum State {
		START,
		FINAL,
		VOWEL,
		MULTIVOWEL,
		HYPHEN,
		END,
		CONSONANT,
		NONWORD;
	}
}
