import java.util.*;

class Or extends BoolExp
{
	// Inherits "ExpList expList" from BoolExp

	Or(ExpList e)
	{
		expList = e;
	}

	Val Eval(HashMap<String,Val> state)
	{
		return orEval(expList, state);
	}

	Val orEval(ExpList expList, HashMap<String,Val> state)
	{
		if ( expList == null )
			return new BoolVal(false);
		
		Val firstExpVal = expList.firstExp().Eval(state);
		Val tailExpListVal = orEval(expList.tailExpList(), state);
		if ( firstExpVal == null | tailExpListVal == null )
			return null;
		if ( firstExpVal.getClass() != BoolVal.class )
		{
			System.out.println( "Error: | operator cannot be applied to " + firstExpVal.toString() );
			return null;
		}

		((BoolVal)firstExpVal).val = ((BoolVal)firstExpVal).val | ((BoolVal)tailExpListVal).val;
		return firstExpVal;
	}
}