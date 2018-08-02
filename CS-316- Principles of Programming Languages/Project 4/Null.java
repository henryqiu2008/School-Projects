import java.util.*;

class Null extends Exp
{	
	static final Null nullVal = new Null();
	
	Val Eval(HashMap<String,Val> state) // You modify the body code
	{
		return new NullObj(); //Eval function returns a new NullObj as when Null class is reached
							  //it is implied that the value is already null, so null just has to be
							  //returned with NullObj
	}
}