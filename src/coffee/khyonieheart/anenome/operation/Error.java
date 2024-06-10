package coffee.khyonieheart.anenome.operation;

public class Error<T, E> implements Result<T, E>
{
	private E error;

	protected Error(
		E error
	) {
		this.error = error;
	}

	@Override
	public T unwrap() 
	{
		throw new IllegalStateException("Attempted to unwrap a value from an Error");
	}

	@Override
	public E unwrapError() 
	{
		return error;
	}

	@Override
	public boolean isOk() 
	{
		return false;
	}
}
