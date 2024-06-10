package coffee.khyonieheart.anenome.operation;

public class None<T> implements Option<T>
{
	@Override
	public T unwrap() 
	{
		throw new IllegalStateException("Attempted to unwrap a None value");
	}

	@Override
	public boolean isSome() 
	{
		return false;
	}
}
