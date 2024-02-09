/*
 * Anenome ~ Shared code for my projects
 * Copyright (C) 2024 Hailey-Jane "Khyonie" Garrett (www.khyonieheart.coffee)
 */

package coffee.khyonieheart.anenome;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.function.Function;

/**
 * Little utilities to handle arrays.
 */
public class Arrays
{
	/**
	 * Converts an array into a string, using either the provided mapper or {@link Object#toString()} and seperating elements with the given delimiter.
	 *
	 * @param <T> Type of array
	 *
	 * @param data Array to convert into a string
	 * @param delimiter String to seperate array entries with
	 * @param mapper Optional mapper function to convert elements in data to a string
	 *
	 * @return String containing all elements in the given array
	 */
	@NotNull
	public static <T> String toString(
		@NotNull T[] data,
		@NotNull String delimiter,
		@Nullable Function<T, String> mapper
	) {
		Objects.requireNonNull(data);
		Objects.requireNonNull(delimiter);

		StringBuilder builder = new StringBuilder();
		ArrayIterator<T> iter = new ArrayIterator<>(data);
		if (mapper != null)
		{
			while (iter.hasNext())
			{
				T next = iter.next();
				builder.append(next == null ? "null" : mapper.apply(iter.next()));

				if (iter.hasNext())
				{
					builder.append(delimiter);
				}
			}

			return builder.toString();
		}

		while (iter.hasNext())
		{
			T next = iter.next();
			builder.append(next == null ? "null" : next.toString());

			if (iter.hasNext())
			{
				builder.append(delimiter);
			}
		}

		return builder.toString();
	}

	/**
	 * Converts an array into a string, seperating elements with the given delimiter.
	 *
	 * @param <T> Type of array
	 *
	 * @param data Array to convert into a string
	 * @param delimiter String to seperate array entries with
	 *
	 * @return String containing all elements in the given array
	 */
	public static <T> String toString(
		@NotNull T[] data,
		@NotNull String delimiter
	) {
		return toString(data, delimiter, null);
	}

	/**
	 * Maps an array to a different type.
	 *
	 * @param <T> Initial type
	 * @param <R> Mapped type
	 *
	 * @param data Array to be mapped
	 * @param type Type of data to map to
	 * @param mapper Mapping function that takes elements of T and outputs R
	 *
	 * @return An array of R
	 * @implNote Null elements will remain null and will not be passed through the mapper.
	 */
	@SuppressWarnings("unchecked")
	public static <T, R> R[] map(
		@NotNull T[] data,
		@NotNull Class<? extends R> type,
		@NotNull Function<T, R> mapper
	) {
		Objects.requireNonNull(data);
		Objects.requireNonNull(type);
		Objects.requireNonNull(mapper);

		R[] mapped = (R[]) Array.newInstance(type, data.length);

		for (int i = 0; i < data.length; i++)
		{
			if (data[i] == null)
			{
				mapped[i] = null;
				continue;
			}

			mapped[i] = mapper.apply(data[i]);
		}

		return mapped;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] copyOf(
		@NotNull T[] data,
		@Positive int length,
		@Nullable T defaultValue
	) {
		Objects.requireNonNull(data);
		RuntimeConditions.requirePositive(length);

		T[] copy = (T[]) Array.newInstance(data.getClass().getComponentType(), length);

		if (length >= data.length)
		{
			int index = 0;
			for (; index < data.length; index++)
			{
				copy[index] = data[index];
			}

			for (; index < length; index++)
			{
				copy[index] = defaultValue;
			}
		}

		return copy;
	}
}
