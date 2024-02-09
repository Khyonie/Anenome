/*
 * Anenome ~ Shared code for my projects
 * Copyright (C) 2024 Hailey-Jane "Khyonie" Garrett (www.khyonieheart.coffee)
 */

package coffee.khyonieheart.anenome;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Validation tool that allows chaining calls, to reduce clutter.
 */
public class ObjectValidator<T>
{
	private T object;

	private ObjectValidator(
		T object
	) {
		this.object = object;
	}

	/**
	 * Returns the original object given to this validator.
	 *
	 * @return Unmodified object given. May be null if a null reference was given to this validator.
	 */
	@Nullable
	public T complete()
	{
		return object;
	}

	/**
	 * Tests if the object is not null. More formally, this method will throw an exception if `object == null` and calling any methods on it will
	 * throw a {@link NullPointerException}.
	 *
	 * @return This object validator
	 */
	@NotNull
	public ObjectValidator<T> isNotNull()
	{
		if (this.object == null)
		{
			throw new NullPointerException("Object can not be null");
		}

		return this;
	}

	/**
	 * Tests if this object is a null reference. More formally, this method will throw an exception if `object != null` and calling any methods on it will 
	 * not cause an exception.
	 *
	 * @return This object validator
	 */
	@NotNull
	public ObjectValidator<T> isNull()
	{
		if (this.object != null)
		{
			throw new IllegalArgumentException("Object must be a null reference");
		}

		return this;
	}

	/**
	 * Tests the object against the given predicate.
	 *
	 * @param predicate Predicate to test with
	 *
	 * @return This object validator
	 * @throws IllegalArgumentException If predicate returns false
	 * @throws NullPointerException If predicate is null
	 */
	@NotNull
	public ObjectValidator<T> testPredicate(
		@NotNull Predicate<T> predicate
	) {
		if (!Objects.requireNonNull(predicate).test(this.object))
		{
			throw new IllegalArgumentException("Validation predicate returned false");
		}

		return this;
	}

	/**
	 * Constructs a new object validator containing the given object.
	 *
	 * @param <T> Type of object
	 *
	 * @param object Object to validate
	 *
	 * @return A new object validator for the given object
	 */
	public static <T> ObjectValidator<T> validate(
		T object
	) {
		return new ObjectValidator<>(object);
	}
}
