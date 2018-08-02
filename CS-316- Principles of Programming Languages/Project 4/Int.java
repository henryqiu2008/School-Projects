import java.util.*;

class Int extends Exp
{
	int intElem;
	
	Int(int i)
	{
		intElem = i;
	}
	
	Val Eval(HashMap<String,Val> state)
	{
		return new IntVal(intElem);
	}
}