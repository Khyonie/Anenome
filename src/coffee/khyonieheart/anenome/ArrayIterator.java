/*
 * Anenome ~ Shared code for my projects
 * Copyright (C) 2024 Hailey-Jane "Khyonie" Garrett (www.khyonieheart.coffee)
 */

package coffee.khyonieheart.anenome;

import java.util.Iterator;
import java.util.Objects;

/**
 * Specialized iterator for iterating over arrays.
 */
public class ArrayIterator<T> implements Iterator<T>
{
	private int index;
	private T[] data;

	/**
	 * Constructs a new ArrayIterator with the given data, starting at the given index.
	 *
	 * @param data Data to be iterated over
	 * @param index Starting index
	 *
	 * @throws IllegalArgumentException If index is negative
	 * @throws ArrayIndexOutOfBoundsException If index lies outside the bounds of the given array
	 */
	public ArrayIterator(
		@NotNull T[] data,
		@Positive int index
	) {
		this.data = Objects.requireNonNull(data);
		this.index = RuntimeConditions.requirePositive(index);

		if (index > data.length - 1)
		{
			throw new ArrayIndexOutOfBoundsException("Index " + index + " is out of bounds for array length " + data.length);
		}
	}

	/**
	 * Constructs a new ArrayIterator with the given data, starting at index 0.
	 *
	 * @param data Data to be iterated over
	 */
	public ArrayIterator(
		@NotNull T[] data
	) {
		this(data, 0);
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasNext() 
	{
		return index < data.length;
	}

	/**
	 * Returns true if an iteration in reverse-order has more elements.
	 *
	 * @return True if the reverse iteration has more elements
	 */
	public boolean hasPrevious()
	{
		return index > 0;
	}

	/** {@inheritDoc} */
	@Nullable
	@Override
	public T next() 
	{
		return data[index++];
	}

	/**
	 * Returns the previous element in the iteration.
	 *
	 * @return The previous element in the iteration
	 */
	@Nullable
	public T previous()
	{
		return data[--index];
	}

	/**
	 * Increments current index.
	 *
	 * @return New index
	 * @throws ArrayIndexOutOfBoundsException If incrementing the index would cause the index to be the same size or greater than the bounds of the array
	 */
	public int nextIndex()
		throws ArrayIndexOutOfBoundsException
	{
		if ((this.index + 1) >= this.data.length - 1)
		{
			throw new ArrayIndexOutOfBoundsException("Cannot increment array iterator index to be greater than the bounds of the array");
		}

		return ++index;
	}

	/**
	 * Decrements current index.
	 *
	 * @return New index
	 * @throws ArrayIndexOutOfBoundsException If decrementing the index would cause the index to be negative
	 */
	public int previousIndex()
		throws ArrayIndexOutOfBoundsException
	{
		if (this.index == 0)
		{
			throw new ArrayIndexOutOfBoundsException("Cannot decrement array iterator index to be negative");
		}

		return --index;
	}
}
