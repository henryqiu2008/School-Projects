import java.util.*;

class Null extends Exp
{	
	static final Null nullVal = new Null();
	
	Val Eval(HashMap<String,Val> state)
	{
		return null; // tentatively returns null in Project 3
	}
}