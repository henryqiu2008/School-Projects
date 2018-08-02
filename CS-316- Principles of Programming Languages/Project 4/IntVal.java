final class IntVal extends Val
{
	int val;

	IntVal(int i)
	{
		val = i;
	}

	public String toString()
	{
		return val+"";
	}

	Val cloneVal()
	{
		return new IntVal(val);
	}

	float floatVal()
	{
		return (float)val;
	}

	boolean isNumber()
	{
		return true;
	}

	boolean isZero()
	{
		return val == 0;
	}

	boolean equalVal(Val that)
	{
		Class thatClass = that.getClass();
		if ( thatClass == IntVal.class )
			return this.val == ((IntVal) that).val;
		if ( thatClass == FloatVal.class )
			return this.val == ((FloatVal) that).val;

		return false;
	}
}