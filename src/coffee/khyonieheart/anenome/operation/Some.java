package coffee.khyonieheart.anenome.operation;

public class Some<T> implements Option<T>
{
	private T value;

	protected Some(
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
	public boolean isSome() 
	{
		return true;
	}
}
