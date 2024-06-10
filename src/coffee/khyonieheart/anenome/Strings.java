package coffee.khyonieheart.anenome;

import java.io.PrintStream;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import coffee.khyonieheart.anenome.exception.StringFormatException;

/**
 * String utilities.
 */
public class Strings
{
	private static Pattern pattern = Pattern.compile("\\{(.*?)\\}");
	private static Pattern formatPattern = Pattern.compile("(\\d+)|(.*)([<>^])(\\d+)");

	/**
	 * Calculates the Levenshtein distance (sometimes called the "edit distance") between two strings.
	 *
	 * @param stringA String A
	 * @param stringB String B
	 *
	 * @return The Levenshtein distance between A and B
	 */
	public static int levenshtein(
		@NotNull String stringA,
		@NotNull String stringB
	) {
		Objects.requireNonNull(stringA);
		Objects.requireNonNull(stringB);

		int[][] distances = new int[stringB.length() + 1][stringA.length() + 1];

		// Initialize empty sections
		for (int i = 0; i < stringA.length(); i++)
		{
			distances[0][i] = i;
		}

		for (int i = 1; i < stringB.length(); i++)
		{
			distances[i][0] = i; 
		}

		// Compute
		int cost = 0;
		for (int b = 1; b < stringB.length() + 1; b++)
		{
			for (int a = 1; a < stringA.length() + 1; a++)
			{
				cost = 0;
				if (stringA.charAt(a - 1) != stringB.charAt(b - 1))
				{
					cost = 1;
				}

				distances[b][a] = Arrays.minArray(
					distances[b - 1][a] + 1,
					distances[b][a - 1] + 1,
					distances[b - 1][a - 1] + cost
				);
			}
		}

		return distances[stringB.length()][stringA.length()];
	}

	/**
	 * Formats a string using the given format.
	 *
	 * @param format Format string 
	 * @param parameters Parameters
	 *
	 * @return Format string with its parameters replaced.
	 */
	public static String format(
		@NotNull String format,
		@NotNull Object... parameters
	) {
		Objects.requireNonNull(format);
		Objects.requireNonNull(parameters);

		if (parameters.length == 0)
		{
			return format;
		}

		Matcher matcher = pattern.matcher(format);

		return matcher.replaceAll(new FormatReplacer(parameters));
	}

	/**
	 * Prints a formatted string to the given {@link PrintStream}.
	 *
	 * @param format Format string
	 * @param out PrintStream to be used
	 * @param parameters Parameters
	 */
	public static void pformat(
		@NotNull String format,
		@NotNull PrintStream out,
		@NotNull Object... parameters
	) {
		Objects.requireNonNull(out);

		out.print(format(format, parameters));
	}

	/**
	 * Prints a formatted string to System.out.
	 *
	 * @param format Format string
	 * @param parameters Parameters
	 */
	public static void pformat(
		@NotNull String format,
		@NotNull Object... parameters
	) {
		pformat(format, System.out, parameters);
	}

	/**
	 * Prints a formatted string to the given {@link PrintStream}, and terminates the line.
	 *
	 * @param format Format string
	 * @param out PrintStream to be used
	 * @param parameters Parameters
	 */
	public static void pformatln(
		@NotNull String format,
		@NotNull PrintStream out,
		@NotNull Object... parameters
	) {
		Objects.requireNonNull(out);

		out.println(format(format, parameters));
	}

	/**
	 * Prints a formatted string to System.out, and terminates the line.
	 *
	 * @param format Format string
	 * @param parameters Parameters
	 */
	public static void pformatln(
		@NotNull String format,
		@NotNull Object... parameters
	) {
		pformatln(format, System.out, parameters);
	}

	//-------------------------------------------------------------------------------- 
	private static class FormatReplacer implements Function<MatchResult, String>
	{
		private final ArrayIterator<Object> formatIterator;

		public FormatReplacer(
			Object[] parameters
		) {
			this.formatIterator = new ArrayIterator<>(parameters);
		}

		@Override
		public String apply(
			MatchResult match
		) {
			Object obj = formatIterator.next();
			String format = match.group(1);

			if (format.isBlank())
			{
				return obj == null ? "null" : obj.toString();
			}

			// Positional parameter
			try {
				int index = Integer.parseInt(format);

				obj = formatIterator.getBacking()[index].toString();

				return obj == null ? "null" : obj.toString();
			} catch (NumberFormatException e) {}

			// Formatting
			if (format.startsWith(":"))
			{
				format = format.substring(1);

				Matcher matcher = formatPattern.matcher(format);

				if (!matcher.matches())
				{
					throw new StringFormatException("Unknown format \"" + format + "\"");
				}

				if (matcher.group(1) == null)
				{
					String padChar = matcher.group(2);

					if (padChar.isEmpty())
					{
						padChar = " ";
					}

					char alignment = matcher.group(3).charAt(0);
					int size = Integer.parseInt(matcher.group(4));
					String string = obj == null ? "null" : obj.toString();

					StringBuilder builder = new StringBuilder(padChar.repeat(size));
					switch (alignment) // {:[fill](^<>)x}
					{
						case '<' -> builder.replace(0, string.length(), string);
						case '>' -> {
							if (string.length() >= builder.length())
							{
								return string;
							} 

							builder.replace(builder.length() - string.length(), builder.length(), string);
						}
						case '^' -> {
							if (string.length() >= builder.length())
							{
								return string;
							} 

							int roundedSize = (int) Math.ceil((double) string.length() / 2); // Rounded up, so the string is slightly biased towards the left
							int remainingSize = string.length() - roundedSize;
							
							builder.replace(builder.length() / 2 - roundedSize, builder.length() / 2 + remainingSize, string);
						}
					}

					return builder.toString();
				}

				// {:x} - Left-aligned, fill at least x characters, padded with spaces
				int size = Integer.parseInt(matcher.group(1));
				obj = formatIterator.next();

				String string = obj == null ? "null" : obj.toString();
				
				if (string.length() >= size)
				{
					return string;
				}

				while (string.length() < size)
				{
					string += ' ';
				}

				return string;
			}

			throw new StringFormatException("Unknown format \"" + format + "\"");
		}
	}
}
