import java.util.*;

class Le extends CompExp
{
	// Inherits "ExpList expList" from CompExp

	Le(ExpList e)
	{
		expList = e;
	}

	Val Eval(HashMap<String,Val> state)
	{
		Val[] leVal = leEval(expList, state);
		if ( leVal == null )
			return null;
		else
			return leVal[0];
	}

	Val[] leEval(ExpList expList, HashMap<String,Val> state)
	{
		Val[] returnVals = new Val[2]; // will set returnVals[0] = Val of (<= expList), returnVals[1] = Val of expList.firstExp()
		if ( expList == null )
		{
			returnVals[0] = new BoolVal(true);
			return returnVals;
		}

		Exp exp1 = expList.firstExp();
		Val e1Val = exp1.Eval(state);
		ExpList tailExpList = expList.tailExpList();
		if ( tailExpList == null ) // expList has only one exp
		{
			if ( e1Val == null )
				return null;
			if ( ! e1Val.isNumber() )
			{
				System.out.println( "Error: <= operator cannot be applied to " + e1Val.toString() );
				return null;
			}
			returnVals[0] = new BoolVal(true);
			returnVals[1] = e1Val;
			return returnVals;
		}

		// expList has at least two exp's

		Val[] tailExpListVals = leEval(tailExpList, state);
		if ( e1Val == null | tailExpListVals == null )
			return null;
		if ( ! e1Val.isNumber() )
		{
			System.out.println( "Error: <= operator cannot be applied to " + e1Val.toString() );
			return null;
		}
		Val e2Val = tailExpListVals[1];
		Val tailExpListVal = tailExpListVals[0];
		if ( e1Val.getClass() == IntVal.class && e2Val.getClass() == IntVal.class )
			returnVals[0] = new BoolVal( ((IntVal)e1Val).val <= ((IntVal)e2Val).val && ((BoolVal)tailExpListVal).val );
		else
			returnVals[0] = new BoolVal( e1Val.floatVal() <= e2Val.floatVal() && ((BoolVal)tailExpListVal).val );
		returnVals[1] = e1Val;
		return returnVals;	
	}
}