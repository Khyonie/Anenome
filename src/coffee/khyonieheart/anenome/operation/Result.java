package coffee.khyonieheart.anenome.operation;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import coffee.khyonieheart.anenome.NotNull;
import coffee.khyonieheart.anenome.Nullable;
import coffee.khyonieheart.anenome.exception.InfallibleError;

public interface Result<T, E>
{
	@Nullable
	public T unwrap();

	@Nullable
	public E unwrapError();

	@Nullable
	public default T unwrapOr(
		@Nullable T defaultValue
	) {
		if (this.isOk())
		{
			return this.unwrap();
		}

		return defaultValue;
	}

	@Nullable
	public default T unwrapOrElse(
		@NotNull Supplier<T> defaultValue
	) {
		Objects.requireNonNull(defaultValue);

		if (isOk())
		{
			return unwrap();
		}

		return defaultValue.get();
	}

	public boolean isOk();

	public default boolean isOkAnd(
		@NotNull Predicate<T> predicate
	) {
		Objects.requireNonNull(predicate);

		if (this.isError())
		{
			return false;
		}

		return predicate.test(this.unwrap());
	}

	public default boolean isError()
	{
		return !isOk();
	}

	public default boolean isErrorAnd(
		@NotNull Predicate<E> predicate
	) {
		Objects.requireNonNull(predicate);

		if (this.isOk())
		{
			return false;
		}

		return predicate.test(this.unwrapError());
	}

	@NotNull
	public default Option<T> ok()
	{
		return switch (this) {
			case Ok<T, E> ok -> new Some<>(this.unwrap());
			case Error<T, E> error -> new None<>();
			default -> throw new InfallibleError();
		};
	}

	@NotNull
	public default Option<E> error()
	{
		return switch (this) {
			case Ok<T, E> ok -> new None<>();
			case Error<T, E> error -> new Some<>(this.unwrapError());
			default -> throw new InfallibleError();
		};
	}

	@NotNull
	public default <U> Result<U, E> map(
		@NotNull Function<T, U> mapper
	) {
		Objects.requireNonNull(mapper);

		return switch (this) {
			case Ok<T, E> ok -> new Ok<>(mapper.apply(ok.unwrap()));
			case Error<T, E> error -> new Error<>(error.unwrapError());
			default -> throw new InfallibleError();
		};
	}

	@NotNull
	public default <F> Result<T, F> mapError(
		@NotNull Function<E, F> mapper
	) {
		Objects.requireNonNull(mapper);

		return switch (this) {
			case Ok<T, E> ok -> new Ok<>(ok.unwrap());
			case Error<T, E> error -> new Error<>(mapper.apply(error.unwrapError()));
			default -> throw new InfallibleError();
		};
	}

	@NotNull
	public default <U> Result<U, E> mapOr(
		@Nullable U defaultValue,
		@NotNull Function<T, U> mapper
	) {
		Objects.requireNonNull(mapper);

		if (this.isOk())
		{
			return new Ok<>(mapper.apply(this.unwrap()));
		}

		return new Ok<>(defaultValue);
	}

	@NotNull
	public default <U> U mapOrElse(
		@NotNull Function<T, U> okMapper,
		@NotNull Function<E, U> errorMapper
	) {
		Objects.requireNonNull(okMapper);
		Objects.requireNonNull(errorMapper);

		return switch (this) {
			case Ok<T, E> ok -> okMapper.apply(ok.unwrap());
			case Error<T, E> error -> errorMapper.apply(error.unwrapError());
			default -> throw new InfallibleError();
		};
	}

	@NotNull
	public default Result<T, E> inspect(
		@NotNull Consumer<T> inspector
	) {
		Objects.requireNonNull(inspector);

		if (this.isError())
		{
			return this;
		}

		inspector.accept(this.unwrap());
		return this;
	}

	@NotNull
	public default Result<T, E> inspectError(
		@NotNull Consumer<E> inspector
	) {
		Objects.requireNonNull(inspector);

		if (this.isOk())
		{
			return this;
		}

		inspector.accept(this.unwrapError());
		return this;
	}

	@NotNull
	public static <T, E> Result<T, E> ok(
		@Nullable T value
	) {
		return new Ok<>(value);
	}

	@NotNull
	public static <T, E> Result<T, E> error(
		@Nullable E error
	) {
		return new Error<>(error);
	}
}
