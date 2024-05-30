package coffee.khyonieheart.anenome.exception;

import java.util.Objects;

import coffee.khyonieheart.anenome.NotNull;

public class StringFormatException extends RuntimeException
{
	public StringFormatException(
		@NotNull String message
	) {
		super(Objects.requireNonNull(message));
	}

	public StringFormatException(
		@NotNull String message,
		@NotNull Exception ex
	) {
		super(message, ex);
	}
}
