package coffee.khyonieheart.anenome.tuple;

import coffee.khyonieheart.anenome.NotNull;
import coffee.khyonieheart.anenome.Nullable;

public class DoubleTuple<A, B>
{
	private A a;
	private B b;

	public DoubleTuple(
		@Nullable A a,
		@Nullable B b
	) {
		this.a = a; 
		this.b = b;
	}

	@Nullable
	public A a()
	{
		return this.a;
	}

	@NotNull
	public DoubleTuple<A, B> setA(
		A a
	) {
		this.a = a;
		
		return this;
	}

	@Nullable
	public B b()
	{
		return this.b;
	}

	@NotNull
	public DoubleTuple<A, B> setB(
		B b
	) {
		this.b = b;

		return this;
	}
}
