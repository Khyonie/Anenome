package coffee.khyonieheart.anenome.operation;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import coffee.khyonieheart.anenome.NotNull;
import coffee.khyonieheart.anenome.Nullable;

public interface Option<T>
{
	@Nullable
	public T unwrap();

	@Nullable
	public default T unwrapOr(
		@Nullable T defaultValue
	) {
		if (this.isSome())
		{
			return this.unwrap();
		}

		return defaultValue;
	}

	@Nullable
	public default T unwrapOrElse(
		@NotNull Supplier<T> supplier
	) {
		Objects.requireNonNull(supplier);

		if (this.isSome())
		{
			return this.unwrap();
		}

		return supplier.get();
	}

	public boolean isSome();

	public default boolean isSomeAnd(
		@NotNull Predicate<T> predicate
	) {
		Objects.requireNonNull(predicate);

		if (this.isNone())
		{
			return false;
		}

		return predicate.test(this.unwrap());
	}

	public default boolean isNone()
	{
		return !this.isSome();
	}

	@NotNull
	public default <U> Option<U> map(
		@NotNull Function<T, U> mapper
	) {
		Objects.requireNonNull(mapper);

		if (this.isSome())
		{
			return new Some<>(mapper.apply(this.unwrap()));
		}

		return new None<>();
	}

	@Nullable
	public default <U> U mapOr(
		@Nullable U defaultValue,
		@NotNull Function<T, U> mapper
	) {
		Objects.requireNonNull(mapper);

		if (this.isSome())
		{
			return mapper.apply(this.unwrap());
		}

		return defaultValue;
	}

	@Nullable
	public default <U> U mapOrElse(
		@NotNull Function<T, U> someMapper,
		@NotNull Supplier<U> noneSupplier
	) {
		if (this.isSome())
		{
			return someMapper.apply(this.unwrap());
		}

		return noneSupplier.get();
	} 

	@NotNull
	public default <E> Result<T, E> okOr(
		@Nullable E error
	) {
		if (this.isSome())
		{
			return Result.ok(this.unwrap());
		}

		return Result.error(error);
	}

	@NotNull
	public default <E> Result<T, E> okOrElse(
		@NotNull Supplier<E> errorSupplier
	) {
		Objects.requireNonNull(errorSupplier);

		if (this.isSome())
		{
			return Result.ok(this.unwrap());
		}

		return Result.error(errorSupplier.get());
	}

	@NotNull
	public static <T> Option<T> some(
		@Nullable T value
	) {
		return new Some<>(value);
	}

	@NotNull
	public static <T> Option<T> none()
	{
		return new None<>();
	}
}
