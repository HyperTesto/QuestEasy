package me.hypertesto.questeasy.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author hypertesto
 * Helper class that capitalize every word as Locale specific behaviour (foreign special chars also)
 * as of: http://stackoverflow.com/questions/1892765/how-to-capitalize-the-first-character-of-each-word-in-a-string
 *
 */
public class WordsCapitalizer {

	/**
	 * Wrapper for capitalizeEveryWord(source,null,null);
	 *
	 * @param source
	 * @return
	 */
	public static String capitalizeEveryWord(String source) {
		return capitalizeEveryWord(source, null, null);
	}

	/**
	 * Wrapper for capitalizeEveryWord(source,null,locale);
	 *
	 * @param source
	 * @param locale
	 * @return
	 */
	public static String capitalizeEveryWord(String source, Locale locale) {
		return capitalizeEveryWord(source, null, locale);
	}

	/**
	 * Perform capitalize given a list of delimiters and a Locale.
	 * First word will always be capitalized.
	 *
	 * @param source
	 * @param delimiters
	 * @param locale
	 * @return
	 */
	public static String capitalizeEveryWord(String source, List<Delimiter> delimiters, Locale locale) {
		char[] chars;

		if (delimiters == null || delimiters.size() == 0)
			delimiters = getDefaultDelimiters();

		// If Locale specified, i18n toLowerCase is executed, to handle specific behaviors (eg. Turkish dotted and dotless 'i')
		if (locale != null)
			chars = source.toLowerCase(locale).toCharArray();
		else
			chars = source.toLowerCase().toCharArray();

		// First character ALWAYS capitalized, if it is a Letter.
		if (chars.length > 0 && Character.isLetter(chars[0]) && !isSurrogate(chars[0])) {
			chars[0] = Character.toUpperCase(chars[0]);
		}

		for (int i = 0; i < chars.length; i++) {
			if (!isSurrogate(chars[i]) && !Character.isLetter(chars[i])) {
				// Current char is not a Letter; gonna check if it is a delimitrer.
				for (Delimiter delimiter : delimiters) {
					if (delimiter.getDelimiter() == chars[i]) {
						// Delimiter found, applying rules...
						if (delimiter.capitalizeBefore() && i > 0
								&& Character.isLetter(chars[i - 1]) && !isSurrogate(chars[i - 1])) {   // previous character is a Letter and I have to capitalize it
							chars[i - 1] = Character.toUpperCase(chars[i - 1]);
						}
						if (delimiter.capitalizeAfter() && i < chars.length - 1
								&& Character.isLetter(chars[i + 1]) && !isSurrogate(chars[i + 1])) {   // next character is a Letter and I have to capitalize it
							chars[i + 1] = Character.toUpperCase(chars[i + 1]);
						}
						break;
					}
				}
			}
		}
		return String.valueOf(chars);
	}


	/**
	 * Check if the current character is part of an UTF-16 Surrogate Pair.
	 * Note: not validating the pair, just used to bypass (any found part of) it.
	 *
	 * @param chr
	 * @return
	 */
	private static boolean isSurrogate(char chr) {
		return (Character.isHighSurrogate(chr) || Character.isLowSurrogate(chr));
	}

	/**
	 * If no delimiter specified, "Capitalize after space" rule is set by default.
	 *
	 * @return
	 */
	private static List<Delimiter> getDefaultDelimiters() {
		List<Delimiter> delimiters = new ArrayList<Delimiter>();
		delimiters.add(new Delimiter(Behavior.CAPITALIZE_AFTER_MARKER, ' '));
		return delimiters;
	}

	/**
	 * A simple class that handle capitalizing settings
	 */
	public static class Delimiter {
		private Behavior behavior;
		private char delimiter;

		public Delimiter(Behavior behavior, char delimiter) {
			super();
			this.behavior = behavior;
			this.delimiter = delimiter;
		}

		public boolean capitalizeBefore() {
			return (behavior.equals(Behavior.CAPITALIZE_BEFORE_MARKER)
					|| behavior.equals(Behavior.CAPITALIZE_BEFORE_AND_AFTER_MARKER));
		}

		public boolean capitalizeAfter() {
			return (behavior.equals(Behavior.CAPITALIZE_AFTER_MARKER)
					|| behavior.equals(Behavior.CAPITALIZE_BEFORE_AND_AFTER_MARKER));
		}

		public char getDelimiter() {
			return delimiter;
		}
	}

	public static enum Behavior {
		CAPITALIZE_AFTER_MARKER(0),
		CAPITALIZE_BEFORE_MARKER(1),
		CAPITALIZE_BEFORE_AND_AFTER_MARKER(2);

		private int value;

		private Behavior(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}