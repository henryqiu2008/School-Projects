final class BoolVal extends Val
{
	boolean val;

	BoolVal(boolean b)
	{
		val = b;
	}

	public String toString()
	{
		return val+"";
	}

	Val cloneVal()
	{
		return new BoolVal(val);
	}

	float floatVal()
	{
		if ( val )
			return 1.0f;
		else
			return 0.0f;
	}

	boolean isNumber()
	{
		return false;
	}

	boolean isZero()
	{
		return false;
	}

	boolean equalVal(Val that)
	{
		if ( that.getClass() == BoolVal.class )
			return this.val == ((BoolVal) that).val;

		return false;
	}
}