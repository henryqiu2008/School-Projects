import java.util.*;

class And extends BoolExp
{
	// Inherits "ExpList expList" from BoolExp

	And(ExpList e)
	{
		expList = e;
	}

	Val Eval(HashMap<String,Val> state)
	{
		return andEval(expList, state);
	}

	Val andEval(ExpList expList, HashMap<String,Val> state)
	{
		if ( expList == null )
			return new BoolVal(true);
		
		Val firstExpVal = expList.firstExp().Eval(state);
		Val tailExpListVal = andEval(expList.tailExpList(), state);
		if ( firstExpVal == null | tailExpListVal == null )
			return null;
		if ( firstExpVal.getClass() != BoolVal.class )
		{
			System.out.println( "Error: & operator cannot be applied to " + firstExpVal.toString() );
			return null;
		}

		((BoolVal)firstExpVal).val = ((BoolVal)firstExpVal).val & ((BoolVal)tailExpListVal).val;
		return firstExpVal;
	}
}