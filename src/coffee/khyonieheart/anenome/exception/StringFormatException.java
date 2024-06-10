package coffee.khyonieheart.anenome.exception;

import java.util.Objects;

import coffee.khyonieheart.anenome.NotNull;
import coffee.khyonieheart.anenome.Strings;

/**
 * Thrown when an invalid {@link Strings} format is encountered.
 */
public class StringFormatException extends RuntimeException
{
	/**
	 * Constructs this exception with a message.
	 *
	 * @param message
	 */
	public StringFormatException(
		@NotNull String message
	) {
		super(Objects.requireNonNull(message));
	}

	/**
	 * Constructs this exception with a message and a cause.
	 *
	 * @param message
	 * @param ex
	 */
	public StringFormatException(
		@NotNull String message,
		@NotNull Exception ex
	) {
		super(message, ex);
	}
}
