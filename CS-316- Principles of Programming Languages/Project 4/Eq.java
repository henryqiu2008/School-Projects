import java.util.*;

class Eq extends CompExp
{
	// Inherits "ExpList expList" from CompExp

	Eq(ExpList e)
	{
		expList = e;
	}

	Val Eval(HashMap<String,Val> state)
	{
		Val[] eqVal = eqEval(expList, state);
		if ( eqVal == null )
			return null;
		else
			return eqVal[0];
	}

	Val[] eqEval(ExpList expList, HashMap<String,Val> state)
	{
		Val[] returnVals = new Val[2]; // will set returnVals[0] = Val of (= expList), returnVals[1] = Val of expList.firstExp()
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
			returnVals[0] = new BoolVal(true);
			returnVals[1] = e1Val;
			return returnVals;
		}

		// expList has at least two exp's

		Val[] tailExpListVals = eqEval(tailExpList, state);
		if ( e1Val == null | tailExpListVals == null )
			return null;
		Val e2Val = tailExpListVals[1];
		Val tailExpListVal = tailExpListVals[0];

		returnVals[0] = new BoolVal( e1Val.equalVal(e2Val) && ((BoolVal)tailExpListVal).val );
		returnVals[1] = e1Val;
		return returnVals;
	}
}