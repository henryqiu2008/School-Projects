final class FloatVal extends Val
{
	float val;

	FloatVal(float f)
	{
		val = f;
	}

	public String toString()
	{
		return val+"";
	}

	Val cloneVal()
	{
		return new FloatVal(val);
	}

	float floatVal()
	{
		return val;
	}

	boolean isNumber()
	{
		return true;
	}

	boolean isZero()
	{
		return val == 0.0f;
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