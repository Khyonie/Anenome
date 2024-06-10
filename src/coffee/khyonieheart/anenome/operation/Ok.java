package coffee.khyonieheart.anenome.operation;

public class Ok<T, E> implements Result<T, E>
{
	private T value;

	protected Ok(
		T value
	) {
		this.value = value;
	}

	@Override
	public T unwrap() 
	{
		return value;
	}

	@Override
	public E unwrapError() 
	{
		throw new IllegalStateException("Attempted to unwrap an Error from an Ok value");
	}

	@Override
	public boolean isOk() 
	{
		return true;
	}
}
