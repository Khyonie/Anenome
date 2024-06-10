/*
 * Anenome ~ Shared code for my projects
 * Copyright (C) 2024 Hailey-Jane "Khyonie" Garrett (www.khyonieheart.coffee)
 */

package coffee.khyonieheart.anenome;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

/**
 * Little utilities to handle arrays.
 */
public class Arrays
{
	public static int minArray(
		int... values
	) {
		int min = Integer.MAX_VALUE;

		for (int i : values)
		{
			min = Math.min(min, i);
		}

		return min;
	}

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
				builder.append(next == null ? "null" : mapper.apply(next));

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

	/**
	 * Casts the input array to the given type. If the original type is a primitive (More formally, for arrays where {@code data.getClass().getComponentType().isPrimitive()} is {@code true}),
	 * this method will first create a copy of the original array as an {@code Object[]} and then perform the actual cast.
	 *
	 * @param <T> Input array type
	 * @param <R> Target array type
	 *
	 * @param data Input array
	 * @param type Target type
	 *
	 * @return The input array, casted to the target type
	 */
	public static <T, R> R[] cast(
		@NotNull T[] data,
		@NotNull Class<? extends R[]> type
	) {
		Objects.requireNonNull(data);
		Objects.requireNonNull(type);

		if (data.getClass().isPrimitive())
		{
			return type.cast(copyPrimitiveArrayToObjectArray(data));
		}

		return type.cast(data);
	}

	public static <T> ArrayList<T> toArrayList(
		@NotNull T[] data
	) {
		Objects.requireNonNull(data);

		if (data.length == 0)
		{
			return new ArrayList<>();
		}

		ArrayList<T> list = new ArrayList<>(data.length);

		for (int i = 0; i < data.length; i++)
		{
			list.add(i, data[i]);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(
		@NotNull Class<T> type,
		@NotNull Collection<T> data
	) {
		T[] array = (T[]) Array.newInstance(type, data.size());

		int index = 0;
		Iterator<T> iter = data.iterator();
		while (iter.hasNext())
		{
			array[index++] = iter.next();
		}

		return array;
	}

	public static <T> Object[] copyPrimitiveArrayToObjectArray(
		@NotNull T[] data
	) {
		Objects.requireNonNull(data);

		if (!data.getClass().getComponentType().isPrimitive())
		{
			throw new IllegalArgumentException();
		}

		Object[] newData = new Object[data.length];
		for (int i = 0; i < data.length; i++)
		{
			newData[i] = data[i];
		}

		return newData;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] copyOf(
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
