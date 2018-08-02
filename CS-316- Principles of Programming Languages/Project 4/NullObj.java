final class NullObj extends Val
{
	public String toString()
	{
		return "null";
	}

	Val cloneVal() // You modify the body code
	{
		return new NullObj(); //cloning this value implies that it is cloning a new Null class
	}

	float floatVal()
	{
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

	boolean equalVal(Val that) // You modify the body code
	{
		if(that.getClass() == NullObj.class){ //if that.class is the same as NullObj.class it returns true since
											  //it is implied that that value is equal to null
			return true;
		}
		return false;
	}
}